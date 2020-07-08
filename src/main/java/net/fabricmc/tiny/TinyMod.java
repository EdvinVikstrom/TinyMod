package net.fabricmc.tiny;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.tiny.commands.CommandManager;
import net.fabricmc.tiny.content.TinyBlocks;
import net.fabricmc.tiny.event.InitEvent;
import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.fabricmc.tiny.event.events.*;
import net.fabricmc.tiny.render.BlockCollisionRenderer;
import net.fabricmc.tiny.render.HudRenderer;
import net.fabricmc.tiny.render.tiny_renderer.TinyRenderer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TinyMod implements ClientModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("TinyMod");

    private static final TinyRenderer TINY_RENDERER = new TinyRenderer();

    @Override
    public void onInitializeClient()
    {
        LOGGER.info("HI!");
        Config.load();
        registerEvents();
        initRenderer();
        CommandManager.INSTANCE.registerCommands();
        InitEvent.INSTANCE.onInit(MinecraftClient.getInstance());
        TickEvent.INSTANCE.register();
        RenderEvent.INSTANCE.register();
    }

    private void registerEvents()
    {
        InitEvent.INSTANCE.registerEvent(TinyBlocks.INSTANCE);
        InitEvent.INSTANCE.registerEvent(ZoomEvent.INSTANCE);
        TickEvent.INSTANCE.registerEvent(ZoomEvent.INSTANCE);
        TickEvent.INSTANCE.registerEvent(OpenGLLogger.INSTANCE);
        InitEvent.INSTANCE.registerEvent(LinearTexEvent.INSTANCE);
        RenderEvent.INSTANCE.registerEvent(BlockCollisionRenderer.INSTANCE);
        RenderEvent.INSTANCE.registerEvent(HudRenderer.INSTANCE);
    }

    private void initRenderer()
    {
        if (RendererAccess.INSTANCE.hasRenderer())
            LOGGER.warn("Could't register Renderer API");
        else
        {
            LOGGER.info("Registering Tiny Renderer!");
            RendererAccess.INSTANCE.registerRenderer(TINY_RENDERER);
        }
    }

}
