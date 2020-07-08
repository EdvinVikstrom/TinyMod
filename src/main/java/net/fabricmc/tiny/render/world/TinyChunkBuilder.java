package net.fabricmc.tiny.render.world;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public class TinyChunkBuilder {

    private final MinecraftClient client;
    private final ClientWorld world;

    private final TinyBlockBuilder blockBuilder;

    public TinyChunkBuilder(MinecraftClient client, ClientWorld world)
    {
        this.client = client;
        this.world = world;
        blockBuilder = new TinyBlockBuilder(world, client.getBakedModelManager(), client.getBlockColors());
    }

    public TinyBuiltChunk buildChunk(ChunkPos chunkPos, BlockBufferBuilderStorage buffers)
    {
        WorldChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
        TinyBuiltChunk builtChunk = new TinyBuiltChunk(chunkPos);

        MatrixStack matrices = new MatrixStack();
        VertexBuffer vertexBuffer = builtChunk.getVertexBuffer();
        BufferBuilder builder = new BufferBuilder(16384);

        builder.begin(7, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);

        int chunkSizeX = 16, chunkSizeY = 256, chunkSizeZ = 16;
        for (int y = 0; y < chunkSizeY; y++)
        {
            for (int x = 0; x < chunkSizeX; x++)
            {
                for (int z = 0; z < chunkSizeZ; z++)
                {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = chunk.getBlockState(pos);
                    if (state.isAir())
                        continue;
                    matrices.push();
                    matrices.translate(x, y, z);
                    blockBuilder.buildBlock(chunk, pos, state, matrices, builder);
                    matrices.pop();
                }
            }
        }
        builder.end();
        vertexBuffer.upload(builder);
        return builtChunk;
    }

}
