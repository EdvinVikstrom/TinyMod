package net.fabricmc.tiny.event.events;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.TickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.lwjgl.opengl.GL20;

public class OpenGLLogger implements TickEvent.Event {

    public static final OpenGLLogger INSTANCE = new OpenGLLogger();

    @Override
    public void TickEvent_onClientTick(MinecraftClient client)
    {
        int error = GL20.glGetError();
        if (error != 0 && client.player != null && Config.OPENGL_INFO.get())
            client.player.sendMessage(new LiteralText(Formatting.AQUA + "[OpenGL]: " + Formatting.RESET + "error[" + error + "]"), false);
    }
}
