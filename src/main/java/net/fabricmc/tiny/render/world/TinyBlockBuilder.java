package net.fabricmc.tiny.render.world;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.WorldChunk;

import java.util.List;
import java.util.Random;

public class TinyBlockBuilder {

    private final ClientWorld world;
    private final BakedModelManager modelManager;
    private final BlockColors blockColors;
    private final Random random = new Random();

    public TinyBlockBuilder(ClientWorld world, BakedModelManager modelManager, BlockColors blockColors)
    {
        this.world = world;
        this.modelManager = modelManager;
        this.blockColors = blockColors;
    }

    public void buildBlock(WorldChunk chunk, BlockPos pos, BlockState state, MatrixStack matrices, VertexConsumer vertices)
    {
        BlockState north = world.getBlockState(pos.north());
        BlockState south = world.getBlockState(pos.south());
        BlockState west = world.getBlockState(pos.west());
        BlockState east = world.getBlockState(pos.east());
        BlockState bottom = world.getBlockState(pos.down());
        BlockState top = world.getBlockState(pos.up());
        ModelIdentifier modelIdentifier = BlockModels.getModelId(state);
        BakedModel blockModel = modelManager.getModel(modelIdentifier);
        int light = WorldRenderer.getLightmapCoordinates(world, state, pos);
        buildBlockModel(blockModel, pos, state, matrices, vertices,
                north.isAir(),
                south.isAir(),
                west.isAir(),
                east.isAir(),
                bottom.isAir(),
                top.isAir(),
                light);
    }

    private void buildBlockModel(BakedModel blockModel, BlockPos pos, BlockState state, MatrixStack matrices, VertexConsumer vertices, boolean northFace, boolean southFace, boolean westFace, boolean eastFace, boolean bottomFace, boolean topFace, int light)
    {
        for (Direction direction : Direction.values())
        {
            if (direction == Direction.NORTH && !northFace) continue;
            else if (direction == Direction.SOUTH && !southFace) continue;
            else if (direction == Direction.WEST && !westFace) continue;
            else if (direction == Direction.EAST && !eastFace) continue;
            else if (direction == Direction.DOWN && !bottomFace) continue;
            else if (direction == Direction.UP && !topFace) continue;

            List<BakedQuad> quads = blockModel.getQuads(state, direction, random);
            renderQuads(quads, pos, state, light, matrices, vertices);
        }
    }

    private void renderQuads(List<BakedQuad> quads, BlockPos pos, BlockState state, int light, MatrixStack matrices, VertexConsumer vertices)
    {
        for (BakedQuad quad : quads)
        {
            float r = 1.0F;
            float g = 1.0F;
            float b = 1.0F;
            if (quad.hasColor())
            {
                int color = blockColors.getColor(state, world, pos);
                r = ((color >> 16) & 0xFF) / 255.0F;
                g = ((color >> 8) & 0xFF) / 255.0F;
                b = (color & 0xFF) / 255.0F;
            }

            float brightness = world.getBrightness(quad.getFace(), quad.hasShade());
            vertices.quad(matrices.peek(), quad, new float[]{
                    brightness, brightness, brightness, brightness
            }, r, g, b, new int[]{
                    light, light, light, light
            }, OverlayTexture.DEFAULT_UV, true);
        }
    }

}
