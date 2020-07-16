package net.fabricmc.tiny.mixin.render;

import net.fabricmc.tiny.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {

    @Inject(at = @At("HEAD"), method = "renderFireOverlay", cancellable = true)
    private static void renderFireOverlay(MinecraftClient client, MatrixStack matrixStack, CallbackInfo info)
    {
        if (!Config.CREATIVE_FIRE_OVERLAY.get() && client.player != null && client.player.abilities.creativeMode)
            info.cancel();
    }

}
