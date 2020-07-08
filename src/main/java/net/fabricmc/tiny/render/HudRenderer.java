package net.fabricmc.tiny.render;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.fabricmc.tiny.utils.ColorHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class HudRenderer implements RenderEvent.Event {

    public static final HudRenderer INSTANCE = new HudRenderer();

    private static final List<String> BUFFER = new ArrayList<>();

    @Override
    public void RenderEvent_onHudRender(MatrixStack matrixStack, float v)
    {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        if (!MinecraftClient.getInstance().options.debugEnabled)
        {
            boolean showFPS = Config.SHOW_FPS.get();
            boolean showTPS = Config.SHOW_TPS.get();
            if (!showFPS && !showTPS)
                return;
            if (showFPS) BUFFER.add(RenderEvent.getCurrentFPS() + " fps");
            if (showTPS) BUFFER.add(TickEvent.getCurrentTPS() + " tps");
            if (BUFFER.size() > 0)
            {
                StringBuilder builder = new StringBuilder(BUFFER.size() + ((BUFFER.size() - 1) * 2));
                for (int i = 0; i < BUFFER.size(); i++)
                {
                    builder.append(BUFFER.get(i));
                    if (i < BUFFER.size() - 1)
                        builder.append(", ");
                }
                String text = builder.toString();
                int width = textRenderer.getWidth(text);
                DrawableHelper.fill(matrixStack, 1, 1, 3 + width, 1 + textRenderer.fontHeight, getDebugColor());
                textRenderer.draw(matrixStack, text, 2.0F, 2.0F, getDebugTextColor());
            }
            BUFFER.clear();
        }
    }

    public static int getDebugColor()
    {
        return ColorHelper.getRGBA(0.31F,0.31F,0.31F, Config.DEBUG_OPACITY.get().floatValue());
    }

    public static int getDebugTextColor()
    {
        return ColorHelper.getRGBA(0.87F,0.87F,0.87F, Config.DEBUG_TEXT_OPACITY.get().floatValue());
    }

}
