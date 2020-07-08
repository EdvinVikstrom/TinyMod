package net.fabricmc.tiny.render.world;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TinyWorldRenderer {

    private final MinecraftClient client;
    private final VertexFormat vertexFormat;
    private final BufferBuilderStorage bufferBuilders;
    private ClientWorld world;
    private TinyChunkBuilder chunkBuilder = null;

    private final List<ChunkPos> chunksToRender = new ArrayList<>();
    private final ConcurrentMap<ChunkPos, TinyBuiltChunk> chunks = new ConcurrentHashMap<>();

    private boolean needsUpdate = true;
    private ChunkPos rebuildChunk = null;

    private ChunkPos lastChunkPos;

    public TinyWorldRenderer(MinecraftClient client, VertexFormat vertexFormat, BufferBuilderStorage bufferBuilders, ClientWorld world)
    {
        this.client = client;
        this.vertexFormat = vertexFormat;
        this.bufferBuilders = bufferBuilders;
        this.world = world;
    }

    private int getRenderDistance()
    {
        return client.options.viewDistance;
    }

    private void checkPosition(Camera camera)
    {
        ChunkPos chunkPos = new ChunkPos(camera.getBlockPos());
        if (!chunkPos.equals(lastChunkPos))
            needsUpdate = true;
        lastChunkPos = chunkPos;
        if (needsUpdate)
        {
            chunksToRender.clear();
            for (int i = -(getRenderDistance() / 2); i < getRenderDistance() / 2; i++)
            {
                for (int j = -(getRenderDistance() / 2); j < getRenderDistance() / 2; j++)
                    chunksToRender.add(new ChunkPos(chunkPos.x + i, chunkPos.z + j));
            }
        }
    }

    public void setupTerrain(Camera camera, Frustum frustum, boolean hasForcedFrustum, int frame, boolean spectator)
    {
        checkPosition(camera);
        if (needsUpdate)
        {
            for (ChunkPos pos : chunks.keySet())
            {
                if (!chunksToRender.contains(pos))
                {
                    chunks.get(pos).destroy();
                    chunks.remove(pos);
                }
            }
            for (ChunkPos pos : chunksToRender)
            {
                if (!chunks.containsKey(pos))
                {
                    TinyBuiltChunk builtChunk = chunkBuilder.buildChunk(pos, bufferBuilders.getBlockBufferBuilders());
                    chunks.put(pos, builtChunk);
                }
            }
            needsUpdate = false;
        }
    }

    public void setWorld(ClientWorld world)
    {
        this.world = world;
        chunkBuilder = new TinyChunkBuilder(MinecraftClient.getInstance(), world);
    }

    public void renderLayer(RenderLayer renderLayer, MatrixStack matrixStack, double x, double y, double z)
    {
        renderLayer.startDrawing();
        if (renderLayer != RenderLayer.getTranslucent())
        {
            for (ChunkPos pos : chunksToRender)
            {
                BlockPos blockPos = pos.getCenterBlockPos();
                TinyBuiltChunk builtChunk = chunks.get(pos);
                if (builtChunk == null)
                    continue;
                VertexBuffer vertexBuffer = builtChunk.getVertexBuffer();
                matrixStack.push();
                matrixStack.translate((double) blockPos.getX() - x, (double) blockPos.getY() - y, (double) blockPos.getZ() - z);
                vertexBuffer.bind();
                vertexFormat.startDrawing(0L);
                vertexBuffer.draw(matrixStack.peek().getModel(), 7);
                matrixStack.pop();
            }
        }
        VertexBuffer.unbind();
        RenderSystem.clearCurrentColor();
        vertexFormat.endDrawing();
        renderLayer.endDrawing();
    }

}
