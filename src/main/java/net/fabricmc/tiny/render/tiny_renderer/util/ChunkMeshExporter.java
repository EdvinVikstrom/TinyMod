package net.fabricmc.tiny.render.tiny_renderer.util;

import net.fabricmc.tiny.render.tiny_renderer.world.TinyBuiltChunk;
import net.fabricmc.tiny.render.tiny_renderer.world.TinyChunkBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

public final class ChunkMeshExporter {

    private final MinecraftClient client;
    private final ChunkPos from, to;

    private final ClientWorld world;
    private final TinyChunkBuilder chunkBuilder;

    private final List<TinyBuiltChunk> builtChunks = new ArrayList<>();

    public ChunkMeshExporter(MinecraftClient client, ChunkPos from, ChunkPos to)
    {
        this.client = client;
        this.from = from;
        this.to = to;
        world = client.world;
        chunkBuilder = new TinyChunkBuilder(client, world);
    }

    public boolean buildChunks()
    {
        if (world == null)
            return false;
        for (int x = from.x; x < to.x; x++)
        {
            for (int z = from.z; z < to.z; z++)
            {
                WorldChunk chunk = world.getChunk(x, z);
                if (chunk == null)
                    continue;
                TinyBuiltChunk builtChunk = chunkBuilder.buildChunk(new ChunkPos(x, z));
                builtChunks.add(builtChunk);
            }
        }
        return true;
    }

    public void exportChunks(String filepath)
    {
        MeshExporter exporter = new MeshExporter();
        for (TinyBuiltChunk builtChunk : builtChunks)
            exporter.append(builtChunk.getMesh());
        exporter.exportMesh(filepath, MeshExporter.Format.OBJ);
    }

}
