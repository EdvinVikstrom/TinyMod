package net.fabricmc.tiny.render.world;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class TinyBuiltChunk {

    private final ChunkPos pos;
    private final BlockPos.Mutable origin;

    private final VertexBuffer vertexBuffer;

    public TinyBuiltChunk(ChunkPos pos)
    {
        this.pos = pos;
        vertexBuffer = new VertexBuffer(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
        origin = new BlockPos.Mutable(pos.x * 16, 0, pos.z * 16);
    }

    public ChunkPos getPos()
    {
        return pos;
    }

    public BlockPos getOrigin()
    {
        return origin;
    }

    public VertexBuffer getVertexBuffer()
    {
        return vertexBuffer;
    }

    public void destroy()
    {
        vertexBuffer.close();
    }
}
