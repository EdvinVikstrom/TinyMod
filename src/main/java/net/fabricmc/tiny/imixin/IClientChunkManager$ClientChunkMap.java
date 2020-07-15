package net.fabricmc.tiny.imixin;

import net.minecraft.world.chunk.WorldChunk;

import java.util.concurrent.atomic.AtomicReferenceArray;

public interface IClientChunkManager$ClientChunkMap {

    AtomicReferenceArray<WorldChunk> getChunks();

}
