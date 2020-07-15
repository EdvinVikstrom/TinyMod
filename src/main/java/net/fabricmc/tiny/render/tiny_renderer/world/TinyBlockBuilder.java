package net.fabricmc.tiny.render.tiny_renderer.world;

import net.fabricmc.tiny.render.api.MeshQuad;
import net.fabricmc.tiny.render.api.utils.MeshBuilder;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
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

    public void buildBlock(MatrixStack matrixStack, WorldChunk chunk, BlockPos globalPos, BlockPos localPos, BlockState state, MeshBuilder builder)
    {
        BlockState north = chunk.getBlockState(localPos.north());
        BlockState south = chunk.getBlockState(localPos.south());
        BlockState west = chunk.getBlockState(localPos.west());
        BlockState east = chunk.getBlockState(localPos.east());
        BlockState bottom = chunk.getBlockState(localPos.down());
        BlockState top = chunk.getBlockState(localPos.up());
        ModelIdentifier modelIdentifier = BlockModels.getModelId(state);
        BakedModel blockModel = modelManager.getModel(modelIdentifier);
        int light = WorldRenderer.getLightmapCoordinates(world, state, globalPos);
        buildBlockModel(matrixStack, blockModel, localPos, state, builder,
                north.isAir() || north.getRenderType() == BlockRenderType.MODEL || state.getRenderType() == BlockRenderType.MODEL,
                south.isAir() || south.getRenderType() == BlockRenderType.MODEL || state.getRenderType() == BlockRenderType.MODEL,
                west.isAir() || west.getRenderType() == BlockRenderType.MODEL || state.getRenderType() == BlockRenderType.MODEL,
                east.isAir() || east.getRenderType() == BlockRenderType.MODEL || state.getRenderType() == BlockRenderType.MODEL,
                bottom.isAir() || bottom.getRenderType() == BlockRenderType.MODEL || state.getRenderType() == BlockRenderType.MODEL,
                top.isAir() || top.getRenderType() == BlockRenderType.MODEL || state.getRenderType() == BlockRenderType.MODEL,
                light);
    }

    private void buildBlockModel(MatrixStack matrixStack, BakedModel blockModel, BlockPos pos, BlockState state, MeshBuilder builder, boolean northFace, boolean southFace, boolean westFace, boolean eastFace, boolean bottomFace, boolean topFace, int light)
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
            renderQuads(matrixStack.peek(), quads, pos, state, light, builder);
        }
    }

    private void renderQuads(MatrixStack.Entry matrixEntry, List<BakedQuad> quads, BlockPos pos, BlockState state, int light, MeshBuilder builder)
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
            builder.quad(new MeshQuad(matrixEntry, quad, r, g, b, 1.0F));
        }
    }

}
