package net.fabricmc.tiny.mixin;

import net.fabricmc.tiny.Config;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    /*
    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/BackgroundRenderer;applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V"),
            method = "render")
    private void render$applyFog(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo info)
    {
        
    }
     */

    @Shadow private ClientWorld world;

    @Inject(at = @At("HEAD"), method = "renderStars(Lnet/minecraft/client/render/BufferBuilder;)V", cancellable = true)
    private void renderStars(BufferBuilder buffer, CallbackInfo info)
    {
        if (!Config.getBoolean("renderStars").get())
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    private void renderWeather(LightmapTextureManager manager, float f, double d, double e, double g, CallbackInfo info)
    {
        if (!Config.getBoolean("renderWeather").get())
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderSky", cancellable = true)
    private void renderSky(MatrixStack matrices, float tickDelta, CallbackInfo info)
    {
        if (!Config.getBoolean("renderSky").get())
            info.cancel();
    }
}
