package net.fabricmc.tiny;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.tiny.commands.CommandManager;
import net.fabricmc.tiny.event.InitEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.fabricmc.tiny.event.events.ZoomEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.Option;
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
    }

    private void registerEvents()
    {
        InitEvent.INSTANCE.registerEvent(ZoomEvent.INSTANCE);
        TickEvent.INSTANCE.registerEvent(ZoomEvent.INSTANCE);
    }

}
