package net.fabricmc.tiny.screen;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.utils.OptionFactory;
import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.Category;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OtherOptionsScreen extends Screen {

    private static final Option[] OPTIONS;

    private final Screen parent;
    private ButtonListWidget buttonListWidget;

    public OtherOptionsScreen(Screen parent)
    {
        super(new TranslatableText("options.other"));
        this.parent = parent;
    }

    private void exit()
    {
        Config.write();
        client.openScreen(parent);
    }

    @Override
    protected void init()
    {
        buttonListWidget = new ButtonListWidget(client, this.width, this.height, 32, this.height - 32, 25);
        buttonListWidget.addAll(OPTIONS);
        addChild(buttonListWidget);
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) -> exit()));
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
    public void onClose()
    {
        exit();
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return true;
    }

    static {
        List<Option> optionList = new ArrayList<>();
        Set<String> keys = Config.getProperties().keySet();
        for (String key : keys)
        {
            AbstractProperty<?> property = Config.getProperty(key);
            if (property.getCategory() != Category.OTHER)
                continue;
            Option option = OptionFactory.create("options." + key, property);
            if (option != null)
                optionList.add(option);
        }
        OPTIONS = optionList.toArray(new Option[0]);
    }
}
