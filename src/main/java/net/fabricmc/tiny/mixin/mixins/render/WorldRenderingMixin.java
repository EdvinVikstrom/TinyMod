package net.fabricmc.tiny.mixin.mixins.render;

import net.fabricmc.tiny.Config;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRenderingMixin {

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/BackgroundRenderer;applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V"),
            method = "render")
    private void renderFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog)
    {
        if (!camera.getSubmergedFluidState().isEmpty())
            BackgroundRenderer.applyFog(camera, fogType, viewDistance, thickFog);
        else
        {
            if (Config.RENDER_FOG.get() == 1)
                viewDistance*=Config.RENDER_FOG$FAR_VALUE.get();
            else if (Config.RENDER_FOG.get() == 2)
                viewDistance*=Config.RENDER_FOG$NEAR_VALUE.get();
            if (Config.RENDER_FOG.get() != 3)
                BackgroundRenderer.applyFog(camera, fogType, viewDistance, thickFog);
        }
    }

    @Inject(at = @At("HEAD"), method = "renderSky", cancellable = true)
    private void renderSky(MatrixStack matrices, float tickDelta, CallbackInfo info)
    {
        if (!Config.RENDER_SKY.get())
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderStars()V", cancellable = true)
    private void renderStars(CallbackInfo info)
    {
        if (!Config.RENDER_STARS.get())
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    private void renderWeather(LightmapTextureManager manager, float f, double d, double e, double g, CallbackInfo info)
    {
        if (!Config.RENDER_WEATHER.get())
            info.cancel();
    }
}
