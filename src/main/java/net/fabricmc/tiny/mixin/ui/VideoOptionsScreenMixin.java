package net.fabricmc.tiny.mixin.ui;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.screen.shader.ShaderOptionsScreen;
import net.fabricmc.tiny.screen.util.OptionButtonWidget;
import net.fabricmc.tiny.screen.util.OptionMenuBuilder;
import net.fabricmc.tiny.utils.property.Categories;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenMixin extends GameOptionsScreen {

    @Final @Shadow private static Option[] OPTIONS;

    private static final Option[] EXTRA_OPTIONS = new Option[]{
            new OptionButtonWidget("shaders", new TranslatableText("options.shaders"), null, button -> MinecraftClient.getInstance().openScreen(new ShaderOptionsScreen(MinecraftClient.getInstance().currentScreen))),
            new OptionButtonWidget("graphics", new TranslatableText("options.graphics"), null,
                    button -> MinecraftClient.getInstance().openScreen(
                    new OptionMenuBuilder(Config.getProperties(Categories.GRAPHICS)).build(MinecraftClient.getInstance().currentScreen, new TranslatableText("options.graphics")))),
            new OptionButtonWidget("performance", new TranslatableText("options.performance"), null,
                    button -> MinecraftClient.getInstance().openScreen(
                            new OptionMenuBuilder(Config.getProperties(Categories.PERFORMANCE)).build(MinecraftClient.getInstance().currentScreen, new TranslatableText("options.performance")))),
            new OptionButtonWidget("details", new TranslatableText("options.details"), null,
                    button -> MinecraftClient.getInstance().openScreen(
                            new OptionMenuBuilder(Config.getProperties(Categories.DETAILS)).build(MinecraftClient.getInstance().currentScreen, new TranslatableText("options.details")))),
            new OptionButtonWidget("other", new TranslatableText("options.other"), null,
                    button -> MinecraftClient.getInstance().openScreen(
                            new OptionMenuBuilder(Config.getProperties(Categories.OTHER)).build(MinecraftClient.getInstance().currentScreen, new TranslatableText("options.other")))),
    };

    public VideoOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title)
    {
        super(parent, gameOptions, title);
    }

    static {
        List<Option> options = new ArrayList<>();
        for (Option option : OPTIONS)
        {
            if (option != Option.PARTICLES &&
                    option != Option.ENTITY_SHADOWS &&
                    option != Option.ENTITY_DISTANCE_SCALING &&
                    option != Option.MIPMAP_LEVELS &&
                    option != Option.CLOUDS)
                options.add(option);
        }
        options.addAll(Arrays.asList(EXTRA_OPTIONS));
        OPTIONS = new Option[options.size()];
        for (int i = 0; i < options.size(); i++)
            OPTIONS[i] = options.get(i);
    }

}
