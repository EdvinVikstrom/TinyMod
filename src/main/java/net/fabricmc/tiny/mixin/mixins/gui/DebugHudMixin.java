package net.fabricmc.tiny.mixin.mixins.gui;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.render.GraphRenderer;
import net.fabricmc.tiny.render.HudRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public class DebugHudMixin {

    private static final int GRAPH_SCALE = 128;
    private static final GraphRenderer GRAPH_RENDERER = new GraphRenderer();

    @Inject(at = @At("TAIL"), method = "render")
    private void render(MatrixStack matrixStack, CallbackInfo info)
    {
        if (Config.DEBUG_GRAPH.get())
        {
            int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
            int x = 2;
            int y = scaledHeight - GRAPH_SCALE - 2;
            DrawableHelper.fill(matrixStack, x, y, x + GRAPH_SCALE, y + GRAPH_SCALE, HudRenderer.getDebugColor());
            GRAPH_RENDERER.render(matrixStack, x + (GRAPH_SCALE / 2), y + (GRAPH_SCALE / 2), (GRAPH_SCALE / 2) - 8);
        }
    }

    @Inject(at = @At("RETURN"), method = "getLeftText")
    private void getLeftText(CallbackInfoReturnable<List<String>> info)
    {
    }

    @Inject(at = @At("RETURN"), method = "getRightText")
    private void getRightText(CallbackInfoReturnable<List<String>> info)
    {
    }

    @ModifyConstant(constant = @Constant(intValue = -1873784752), method = "renderLeftText")
    private int getLeftBackgroundColor(int color)
    {
        return HudRenderer.getDebugColor();
    }

    @ModifyConstant(constant = @Constant(intValue = 14737632), method = "renderLeftText")
    private int getLeftTextColor(int color)
    {
        return HudRenderer.getDebugTextColor();
    }

    @ModifyConstant(constant = @Constant(intValue = -1873784752), method = "renderRightText")
    private int getRightBackgroundColor(int color)
    {
        return HudRenderer.getDebugColor();
    }

    @ModifyConstant(constant = @Constant(intValue = 14737632), method = "renderRightText")
    private int getRightTextColor(int color)
    {
        return HudRenderer.getDebugTextColor();
    }

    private static void addColors(List<String> list)
    {
    }

}
