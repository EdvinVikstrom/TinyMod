package net.fabricmc.tiny.render.tiny_renderer.world;

import net.fabricmc.tiny.render.tiny_renderer.util.MeshExporter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

public final class ChunkMeshBuilder {

    private final List<ChunkPos> chunks;

    private final ClientWorld world;
    private final TinyChunkBuilder chunkBuilder;

    private final List<TinyBuiltChunk> builtChunks = new ArrayList<>();

    public ChunkMeshBuilder(MinecraftClient client, ClientWorld world, List<ChunkPos> chunks)
    {
        this.chunks = chunks;
        this.world = world;
        chunkBuilder = new TinyChunkBuilder(client, world);
    }

    public boolean renderChunks()
    {
        if (world == null)
            return false;
        for (ChunkPos chunkPos : chunks)
        {
            WorldChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
            if (chunk == null)
                continue;
            TinyBuiltChunk builtChunk = chunkBuilder.buildChunk(chunkPos);
            builtChunks.add(builtChunk);
        }
        return true;
    }

    public void exportChunks(String filepath, MeshExporter meshExporter)
    {
        for (TinyBuiltChunk builtChunk : builtChunks)
            meshExporter.append(builtChunk.getMesh());
        meshExporter.exportMeshes(filepath);
    }

}
