package net.fabricmc.tiny.content;

import net.fabricmc.tiny.Constants;
import net.fabricmc.tiny.event.InitEvent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TinyBlocks implements InitEvent.Event {

    public static final TinyBlocks INSTANCE = new TinyBlocks();

    public static final GrassBlock FULL_GRASS_BLOCK = new GrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK));

    @Override
    public void InitEvent_onInit(MinecraftClient client)
    {
        Registry.register(Registry.BLOCK, new Identifier(Constants.MODID, "full_grass_block"), FULL_GRASS_BLOCK);
    }
}
