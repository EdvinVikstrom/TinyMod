package net.fabricmc.tiny.screen.util;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.utils.property.*;
import net.fabricmc.tiny.utils.property.properties.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.CyclingOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.*;

public class OptionMenuBuilder {

    private final Map<String, AbstractProperty<?>> properties;

    public OptionMenuBuilder(Map<String, AbstractProperty<?>> properties)
    {
        this.properties = properties;
    }

    public OptionMenuBuilder()
    {
        this(new LinkedHashMap<>());
    }

    public Map<String, AbstractProperty<?>> getProperties()
    {
        return properties;
    }

    public OptionMenuBuilder put(String key, AbstractProperty<?> property)
    {
        properties.put(key, property);
        return this;
    }

    public Build build(Screen parent, Text text)
    {
        Build build = new Build(MinecraftClient.getInstance(), parent, text);
        Set<String> keys = properties.keySet();
        MinecraftClient client = MinecraftClient.getInstance();
        for (String key : keys)
        {
            String textKey = "options." + key;
            AbstractProperty<?> property = properties.get(key);
            TranslatableText propertyText = new TranslatableText(textKey);
            if (property instanceof ListProperty)
                build.putOption(new OptionButtonWidget(textKey, propertyText, null, button -> client.openScreen(new OptionMenuBuilder(((ListProperty) property).asMap()).build(build, propertyText))));
            else if (property instanceof BooleanProperty)
                build.putOption(new BooleanOption(textKey,
                        gameOptions -> ((BooleanProperty) property).get(),
                        (gameOptions, value) -> ((BooleanProperty) property).set(value)));
            else if (property instanceof FloatProperty)
                build.putOption(new DoubleOption(textKey,
                        ((FloatProperty) property).getMin(),
                        ((FloatProperty) property).getMax(),
                        (((FloatProperty) property).getMax() <= 10.0D ? 0.01F : (((FloatProperty) property).getMax() >= 100.0D ? 1.0F : 0.1F)),
                        gameOptions -> ((FloatProperty) property).get(),
                        (gameOptions, value) -> ((FloatProperty) property).set(value),
                        (gameOptions, doubleOption) -> new TranslatableText(textKey, String.format("%.1f", ((FloatProperty) property).get()))));
            else if (property instanceof IntProperty)
                build.putOption(new DoubleOption(textKey,
                        ((IntProperty) property).getMin(),
                        ((IntProperty) property).getMax(),
                        1.0F,
                        gameOptions -> (double) (int) ((IntProperty) property).get(),
                        (gameOptions, value) -> ((IntProperty) property).set((int) (double) value),
                        (gameOptions, doubleOption) -> new TranslatableText(textKey, ((IntProperty) property).get())));
            else if (property instanceof EnumProperty)
                build.putOption(new CyclingOption(textKey,
                        (gameOptions, integer) -> ((EnumProperty) property).cycle(),
                        (gameOptions, cyclingOption) -> new TranslatableText(textKey,
                                new TranslatableText(textKey + "." + ((EnumProperty) property).getCurrent())
                        .getString())));
        }
        return build;
    }

    public static class Build extends Screen {

        private final Screen parent;
        private ButtonListWidget buttonListWidget;

        private final List<Option> options;

        public Build(MinecraftClient client, Screen parent, Text title)
        {
            super(title);
            this.parent = parent;
            this.client = client;
            options = new ArrayList<>();
        }

        private void exit()
        {
            Config.write();
            client.openScreen(parent);
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
        {
            renderBackground(matrices);
            drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 16777215);
            buttonListWidget.render(matrices, mouseX, mouseY, delta);
            super.render(matrices, mouseX, mouseY, delta);
        }

        @Override
        protected void init()
        {
            buttonListWidget = new ButtonListWidget(client, this.width, this.height, 32, this.height - 32, 25);
            buttonListWidget.addAll(options.toArray(new Option[0]));
            addChild(buttonListWidget);
            addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) -> exit()));
        }

        @Override
        public void tick()
        {
            super.tick();
        }

        @Override
        public boolean shouldCloseOnEsc()
        {
            return true;
        }

        @Override
        public void onClose()
        {
            exit();
        }

        public void putOption(Option... option)
        {
            options.addAll(Arrays.asList(option));
        }
    }

}
