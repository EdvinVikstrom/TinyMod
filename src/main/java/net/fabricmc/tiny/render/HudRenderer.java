package net.fabricmc.tiny.render;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.fabricmc.tiny.event.events.EntityMovementHandler;
import net.fabricmc.tiny.utils.helper.ColorHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class HudRenderer implements RenderEvent.Event {

    public static final HudRenderer INSTANCE = new HudRenderer();

    @Override
    public void RenderEvent_onHudRender(MatrixStack matrixStack, float v)
    {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        if (!MinecraftClient.getInstance().options.debugEnabled)
        {
            List<String> lines = new ArrayList<>();
            getText(lines);
            for (int i = 0; i < lines.size(); i++)
            {
                int width = textRenderer.getWidth(lines.get(i));
                int y = 2 + textRenderer.fontHeight * i;
                DrawableHelper.fill(matrixStack, 1, y - 1, 2 + width + 1, y + textRenderer.fontHeight - 1, getDebugColor());
                textRenderer.draw(matrixStack, lines.get(i), 2.0F, (float) y, getDebugTextColor());
            }
        }
    }

    private static void getText(List<String> lines)
    {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (Config.SHOW_FPS.get()) lines.add(RenderEvent.getCurrentFPS() + " fps");
        if (Config.SHOW_TPS.get())
        {
            if (Config.SHOW_FPS.get())
                lines.set(0, lines.get(0) + ", " + TickEvent.getCurrentTPS() + " tps");
            else
                lines.add(TickEvent.getCurrentTPS() + " tps");
        }
        if (Config.SHOW_COORDS.get() && player != null)
            lines.add(String.format("XYZ: %.1f / %.1f / %.1f", player.getX(), player.getY(), player.getZ()));
        if (Config.SHOW_VELOCITY.get() && player != null)
        {
            Entity entity = player.isRiding() ? player.getVehicle() : player;
            lines.add(String.format("Velocity: %.1f", EntityMovementHandler.INSTANCE.getEntityVelocity(entity)));
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
