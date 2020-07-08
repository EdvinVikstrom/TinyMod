package net.fabricmc.tiny.mixin.gui;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.render.GraphRenderer;
import net.fabricmc.tiny.render.HudRenderer;
import net.fabricmc.tiny.utils.NumUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public class DebugHudMixin {

    private static final Formatting DIGIT_COLOR = Formatting.YELLOW;
    private static final Formatting X_COLOR = Formatting.RED;
    private static final Formatting Y_COLOR = Formatting.GREEN;
    private static final Formatting Z_COLOR = Formatting.BLUE;

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
        /*
        if (!Config.getBoolean("debugPieColors").get())
            return;
        List<String> list = info.getReturnValue();
        addColors(list);

         */
    }

    @Inject(at = @At("RETURN"), method = "getRightText")
    private void getRightText(CallbackInfoReturnable<List<String>> info)
    {
        /*
        if (!Config.getBoolean("debugPieColors").get())
            return;
        List<String> list = info.getReturnValue();
        addColors(list);


         */
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
        for (int i = 0; i < list.size(); i++)
        {
            String string = list.get(i);
            StringBuilder builder = new StringBuilder();
            int currentColor = Formatting.RESET.getColorIndex();
            for (int k = 0; k < string.length(); k++)
            {
                char c = string.charAt(k);
                char last = k > 0 ? string.charAt(k - 1) : '\0';
                char next = k < string.length() - 1 ? string.charAt(k + 1) : '\0';
                if ((NumUtils.isDigit(c) || (NumUtils.isDigit(last) && c == '.') || (NumUtils.isDigit(next) && (c == '-' || c == '+'))) && currentColor != DIGIT_COLOR.getColorIndex())
                {
                    builder.append(DIGIT_COLOR.toString());
                    currentColor = DIGIT_COLOR.getColorIndex();
                }else if (c == 'X' && currentColor != X_COLOR.getColorIndex())
                {
                    builder.append(X_COLOR.toString());
                    currentColor = X_COLOR.getColorIndex();
                }else if (c == 'Y' && currentColor != Y_COLOR.getColorIndex())
                {
                    builder.append(Y_COLOR.toString());
                    currentColor = Y_COLOR.getColorIndex();
                }else if (c == 'Z' && currentColor != Z_COLOR.getColorIndex())
                {
                    builder.append(Z_COLOR.toString());
                    currentColor = Z_COLOR.getColorIndex();
                }else if (currentColor != Formatting.RESET.getColorIndex())
                {
                    builder.append(Formatting.RESET.toString());
                    currentColor = Formatting.RESET.getColorIndex();
                }
                builder.append(c);
            }
            string = builder.toString() + Formatting.RESET;
            list.set(i, string);
        }
    }

}
