package net.fabricmc.tiny.event.events;

import net.fabricmc.tiny.event.InitEvent;
import net.minecraft.client.MinecraftClient;

public class LinearTexEvent implements InitEvent.Event {

    public static final LinearTexEvent INSTANCE = new LinearTexEvent();

    @Override
    public void InitEvent_onInit(MinecraftClient client)
    {
        update();
    }

    public void update()
    {
        //boolean linear = Config.LINEAR_TEXTURES.get();
        //TextureFilterHelper.setAllSprites(linear ? GL11.GL_LINEAR : GL11.GL_NEAREST);
    }

}
