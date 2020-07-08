package net.fabricmc.tiny.mixin.other;

import net.fabricmc.tiny.Config;
import net.minecraft.client.Keyboard;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.util.ScreenshotUtils;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;
import java.util.function.Consumer;

@Mixin(Keyboard.class)
public class TakeScreenshotMixin {

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/util/ScreenshotUtils;saveScreenshot(Ljava/io/File;IILnet/minecraft/client/gl/Framebuffer;Ljava/util/function/Consumer;)V"),
            method = "onKey")
    private void takeScreenshot(File gameDirectory, int framebufferWidth, int framebufferHeight, Framebuffer framebuffer, Consumer<Text> messageReceiver)
    {
        double aspectRatio = (double) framebufferWidth / (double) framebufferHeight;
        int resolution = Config.SCREENSHOT_RESOLUTION.get();
        if (resolution == 0)
            ScreenshotUtils.saveScreenshot(gameDirectory, framebufferWidth, framebufferHeight, framebuffer, messageReceiver);
        else
        {
            int height = resolution * 2000;
            int width = (int) (aspectRatio * height);
            ScreenshotUtils.saveScreenshot(gameDirectory, width, height, framebuffer, messageReceiver);
        }
    }

}
