package net.fabricmc.tiny.event.events;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.tiny.event.InitEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.fabricmc.tiny.screen.MeshExporterScreen;
import net.fabricmc.tiny.screen.config.ConfigMenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeysEvent implements InitEvent.Event, TickEvent.Event {

    public static final KeysEvent INSTANCE = new KeysEvent();

    private static final KeyBinding CONFIG_KEY_BINDING = new KeyBinding("key.tiny_mod.config", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_HOME, "key.categories.misc");
    private static final KeyBinding EXPORT_MESH_KEY_BINDING = new KeyBinding("key.tiny_mod.export_mesh", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_END, "key.categories.misc");

    @Override
    public void InitEvent_onInit(MinecraftClient client)
    {
        KeyBindingHelper.registerKeyBinding(CONFIG_KEY_BINDING);
        KeyBindingHelper.registerKeyBinding(EXPORT_MESH_KEY_BINDING);
    }

    @Override
    public void TickEvent_onClientTick(MinecraftClient client)
    {
        if (CONFIG_KEY_BINDING.wasPressed())
            client.openScreen(new ConfigMenuScreen(client.currentScreen));
        if (EXPORT_MESH_KEY_BINDING.wasPressed())
            client.openScreen(new MeshExporterScreen(client.currentScreen));
    }
}
