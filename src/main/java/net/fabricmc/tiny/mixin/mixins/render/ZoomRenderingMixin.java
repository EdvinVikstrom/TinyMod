package net.fabricmc.tiny.mixin.mixins.render;

import net.fabricmc.tiny.Config;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class ZoomRenderingMixin {

    @Inject(at = @At("HEAD"), method = "getFov", cancellable = true)
    private void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> info)
    {
        if (Config.ZOOMING.get())
            info.setReturnValue(Config.ZOOM_FACTOR.get());
    }

    @Inject(at = @At("HEAD"), method = "renderHand", cancellable = true)
    private void renderHand(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo info)
    {
        if (Config.ZOOMING.get())
            info.cancel();
    }

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/GameRenderer;renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V"),
            method = "render")
    private void render(float tickDelta, long startTime, boolean tick, CallbackInfo info)
    {
        //loadShader(new Identifier(Constants.MODID, "shaders/post/bloom.json"));
    }

    @Shadow
    private void loadShader(Identifier identifier)
    {

    }
}
