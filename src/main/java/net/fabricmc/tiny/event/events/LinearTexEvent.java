package net.fabricmc.tiny.event.events;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.InitEvent;
import net.fabricmc.tiny.utils.TextureFilterHelper;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL11;

public class LinearTexEvent implements InitEvent.Event {

    public static final LinearTexEvent INSTANCE = new LinearTexEvent();

    @Override
    public void InitEvent_onInit(MinecraftClient client)
    {
        update();
    }

    public void update()
    {
        boolean linear = Config.LINEAR_TEXTURES.get();
        TextureFilterHelper.setAllSprites(linear ? GL11.GL_LINEAR : GL11.GL_NEAREST);
    }

}
