package net.fabricmc.tiny.imixin;

import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.world.chunk.WorldChunk;

import java.util.concurrent.atomic.AtomicReferenceArray;

public interface IClientChunkManager {

    ClientChunkManager.ClientChunkMap getChunkMap();

}
