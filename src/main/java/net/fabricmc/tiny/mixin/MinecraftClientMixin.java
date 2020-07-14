package net.fabricmc.tiny.mixin;

import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.imixin.IMinecraftClientMixin;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements IMinecraftClientMixin {

    @Shadow private static int currentFps;

    @Inject(at = @At(value = "INVOKE",
            shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/client/render/GameRenderer;render(FJZ)V"),
            method = "render")
    private void render(boolean tick, CallbackInfo info)
    {
        RenderEvent.INSTANCE.onRender((MinecraftClient) (Object) this);
    }

    @Override
    public int getCurrentFps()
    {
        return currentFps;
    }
}
