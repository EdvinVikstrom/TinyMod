package net.fabricmc.tiny.mixin;

import net.fabricmc.tiny.screen.DetailOptionsScreen;
import net.fabricmc.tiny.screen.OtherOptionsScreen;
import net.fabricmc.tiny.screen.PerformanceOptionsScreen;
import net.fabricmc.tiny.screen.shader.ShaderOptionsScreen;
import net.fabricmc.tiny.screen.util.OptionButtonWidget;
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
            new OptionButtonWidget("details", new TranslatableText("options.details"), null, button -> MinecraftClient.getInstance().openScreen(new DetailOptionsScreen(MinecraftClient.getInstance().currentScreen))),
            new OptionButtonWidget("performance", new TranslatableText("options.performance"), null, button -> MinecraftClient.getInstance().openScreen(new PerformanceOptionsScreen(MinecraftClient.getInstance().currentScreen))),
            new OptionButtonWidget("misc", new TranslatableText("key.categories.misc"), null, button -> MinecraftClient.getInstance().openScreen(new OtherOptionsScreen(MinecraftClient.getInstance().currentScreen)))
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
