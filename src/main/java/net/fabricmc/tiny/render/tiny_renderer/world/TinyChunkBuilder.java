package net.fabricmc.tiny.render.tiny_renderer.world;

import net.fabricmc.tiny.render.api.utils.MeshBuilder;
import net.fabricmc.tiny.render.tiny_renderer.TinyMesh;
import net.fabricmc.tiny.render.tiny_renderer.TinyRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public class TinyChunkBuilder {

    private final ClientWorld world;

    private final TinyBlockBuilder blockBuilder;

    public TinyChunkBuilder(MinecraftClient client, ClientWorld world)
    {
        this.world = world;
        blockBuilder = new TinyBlockBuilder(world, client.getBakedModelManager(), client.getBlockColors());
    }

    public TinyBuiltChunk buildChunk(ChunkPos chunkPos)
    {
        WorldChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
        MeshBuilder builder = new MeshBuilder(4);

        int chunkSizeX = 16, chunkSizeY = 256, chunkSizeZ = 16;
        MatrixStack matrixStack = new MatrixStack();
        for (int y = 0; y < chunkSizeY; y++)
        {
            for (int x = 0; x < chunkSizeX; x++)
            {
                for (int z = 0; z < chunkSizeZ; z++)
                {

                    BlockPos pos = new BlockPos(x, y, z);
                    BlockPos global = pos.add(chunkPos.x * 16, 0, chunkPos.z * 16);
                    global.add(pos);
                    BlockState state = chunk.getBlockState(pos);
                    if (state.isAir())
                        continue;

                    matrixStack.push();
                    matrixStack.translate(pos.getX(), pos.getY(), pos.getZ());
                    blockBuilder.buildBlock(matrixStack, chunk, global, pos, state, builder);
                    matrixStack.pop();
                }
            }
        }
        TinyMesh mesh = builder.build(new Identifier("chunk/" + chunkPos.x + "_" + chunkPos.z));
        TinyRenderer.INSTANCE.meshManager().registerMesh(chunkPos.toLong(), mesh);
        return new TinyBuiltChunk(chunkPos, mesh);
    }

}
