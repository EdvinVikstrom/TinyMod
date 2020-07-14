package net.fabricmc.tiny.render.tiny_renderer_wip.world;

import net.fabricmc.tiny.render.tiny_renderer_wip.TinyMesh;
import net.minecraft.util.math.ChunkPos;

public class TinyBuiltChunk {

    private final ChunkPos pos;
    private final TinyMesh mesh;

    public TinyBuiltChunk(ChunkPos pos, TinyMesh mesh)
    {
        this.pos = pos;
        this.mesh = mesh;
    }

    public ChunkPos getPos()
    {
        return pos;
    }

    public TinyMesh getMesh()
    {
        return mesh;
    }
}
