package net.fabricmc.tiny;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.tiny.commands.CommandManager;
import net.fabricmc.tiny.event.InitEvent;
import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.fabricmc.tiny.event.events.*;
import net.fabricmc.tiny.render.BlockCollisionRenderer;
import net.fabricmc.tiny.render.HudRenderer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TinyMod implements ClientModInitializer {

    private static final Logger logger = LogManager.getLogger("TinyMod");

    @Override
    public void onInitializeClient()
    {
        logger.info("HI!");
        Config.load();
        registerEvents();
        CommandManager.INSTANCE.registerCommands();
        InitEvent.INSTANCE.onInit(MinecraftClient.getInstance());
        TickEvent.INSTANCE.register();
        RenderEvent.INSTANCE.register();
    }

    private void registerEvents()
    {
        InitEvent.INSTANCE.registerEvent(ZoomEvent.INSTANCE);
        TickEvent.INSTANCE.registerEvent(ZoomEvent.INSTANCE);
        TickEvent.INSTANCE.registerEvent(OpenGLLogger.INSTANCE);
        InitEvent.INSTANCE.registerEvent(LinearTexEvent.INSTANCE);
        RenderEvent.INSTANCE.registerEvent(BlockCollisionRenderer.INSTANCE);
        RenderEvent.INSTANCE.registerEvent(HudRenderer.INSTANCE);
    }

}
