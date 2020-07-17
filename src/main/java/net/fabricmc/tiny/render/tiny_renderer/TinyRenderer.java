package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.ClientEvent;
import net.fabricmc.tiny.event.ExecuteEvent;
import net.fabricmc.tiny.event.RenderEvent;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;

public class TinyRenderer implements RenderEvent.Event {

    public static final TinyRenderer INSTANCE = new TinyRenderer();
    public static final Logger LOGGER = LogManager.getLogger("TinyRenderer");

    private final TinyMaterialManager materialManager;
    private final TinyMeshManager meshManager;

    public TinyRenderer()
    {
        materialManager = new TinyMaterialManager();
        meshManager = new TinyMeshManager();
    }

    public TinyMaterialManager materialManager()
    {
        return materialManager;
    }

    public TinyMeshManager meshManager()
    {
        return meshManager;
    }

    public void loadRenderer()
    {
        LOGGER.info("Loading renderer!");

        reloadRenderer();
    }

    public void reloadRenderer()
    {
        if (!GL.getCapabilities().OpenGL40)
        {
            Config.GL40_SUPPORTED.set(false);
            LOGGER.warn("OpenGL 4 not supported; using vanilla renderer");
            return;
        }
        Config.GL40_SUPPORTED.set(true);
    }

    @Override
    public void RenderEvent_onInit(MinecraftClient client)
    {
        ExecuteEvent.INSTANCE.execute(() -> {
            ClientEvent.INSTANCE.registerEvent(materialManager);
        });
        materialManager.init(client);
        loadRenderer();
    }
}
