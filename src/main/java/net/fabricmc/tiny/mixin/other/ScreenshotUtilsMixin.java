package net.fabricmc.tiny.mixin.other;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenshotUtils.class)
public class ScreenshotUtilsMixin {

    // TODO:
    @Inject(at = @At("HEAD"), method = "takeScreenshot", cancellable = true)
    private static void takeScreenshot(int width, int height, Framebuffer framebuffer, CallbackInfoReturnable<NativeImage> info)
    {
        width = framebuffer.textureWidth;
        height = framebuffer.textureHeight;
        NativeImage nativeImage = new NativeImage(width, height, false);
        RenderSystem.bindTexture(framebuffer.colorAttachment);
        nativeImage.loadFromTextureImage(0, true);
        nativeImage.mirrorVertically();
        info.setReturnValue(nativeImage);
    }

}
