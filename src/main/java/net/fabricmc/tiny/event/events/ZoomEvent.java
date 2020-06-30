package net.fabricmc.tiny.event.events;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.InitEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZoomEvent implements InitEvent.Event, TickEvent.Event {

    public static final ZoomEvent INSTANCE = new ZoomEvent();

    private KeyBinding keyBinding;

    private boolean cinemaMode;

    @Override
    public void InitEvent_onInit(MinecraftClient client)
    {
        keyBinding = new KeyBinding("key.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.categories.misc");
        KeyBindingHelper.registerKeyBinding(keyBinding);
    }

    @Override
    public void TickEvent_onClientTick(MinecraftClient client)
    {
        if (keyBinding.isPressed())
            startZoom(client);
        else
            stopZoom(client);
    }

    private void startZoom(MinecraftClient client)
    {
        if (!Config.getBoolean("zooming").get())
            cinemaMode = client.options.smoothCameraEnabled;
        client.options.smoothCameraEnabled = true;
        Config.getBoolean("zooming").set(true);
    }

    private void stopZoom(MinecraftClient client)
    {
        client.options.smoothCameraEnabled = cinemaMode;
        Config.getBoolean("zooming").set(false);
    }
}
