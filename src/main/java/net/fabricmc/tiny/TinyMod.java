package net.fabricmc.tiny;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.tiny.event.EntityEvent;
import net.fabricmc.tiny.event.InitEvent;
import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.fabricmc.tiny.event.events.*;
import net.fabricmc.tiny.render.HudRenderer;
import net.fabricmc.tiny.render.tiny_renderer.TinyRenderer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TinyMod implements ClientModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("TinyMod");

    @Override
    public void onInitializeClient()
    {
        LOGGER.info("HI!");

        Config.load();
        registerEvents();
        initRenderer();
        InitEvent.INSTANCE.onInit(MinecraftClient.getInstance());
        TickEvent.INSTANCE.register();
        RenderEvent.INSTANCE.register();
    }

    private void registerEvents()
    {
        InitEvent.INSTANCE.registerEvent(ZoomEvent.INSTANCE);
        InitEvent.INSTANCE.registerEvent(KeysEvent.INSTANCE);
        TickEvent.INSTANCE.registerEvent(ZoomEvent.INSTANCE);
        TickEvent.INSTANCE.registerEvent(KeysEvent.INSTANCE);
        TickEvent.INSTANCE.registerEvent(OpenGLLogger.INSTANCE);
        InitEvent.INSTANCE.registerEvent(LinearTexEvent.INSTANCE);
        RenderEvent.INSTANCE.registerEvent(TinyRenderer.INSTANCE);
        RenderEvent.INSTANCE.registerEvent(HudRenderer.INSTANCE);
        EntityEvent.INSTANCE.registerEvent(EntityMovementHandler.INSTANCE);
    }

    private void initRenderer()
    {
        /*
        if (RendererAccess.INSTANCE.hasRenderer())
            LOGGER.warn("Could't register Renderer API");
        else
        {
            LOGGER.info("Registering Tiny Renderer!");
            RendererAccess.INSTANCE.registerRenderer(TinyRenderer.INSTANCE);
        }
         */
    }

}
