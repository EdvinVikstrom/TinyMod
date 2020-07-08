package net.fabricmc.tiny.render;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.RenderEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class BlockCollisionRenderer implements RenderEvent.Event {

    public static final BlockCollisionRenderer INSTANCE = new BlockCollisionRenderer();

    private static final int AREA_X = 8, AREA_Y = 4, AREA_Z = 8;

    @Override
    public void RenderEvent_onWorldRender(RenderEvent.WorldRendererContext context)
    {
        if (Config.SHOW_COLLISION.get() && MinecraftClient.getInstance().player != null)
        {
            VertexConsumer vertices = context.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.getLines());
            if (vertices == null)
                return;
            for (int x = 0; x < AREA_X; x++)
            {
                for (int z = 0; z < AREA_Z; z++)
                {
                    for (int y = 0; y < AREA_Y; y++)
                    {
                        BlockPos blockPos = MinecraftClient.getInstance().player.getBlockPos().add(x - (AREA_X * 0.5), y - 1, z - (AREA_Z * 0.5));
                        BlockState blockState = context.getWorld().getBlockState(blockPos);
                        if (blockState.isAir())
                            continue;
                        Vec3d camera = MinecraftClient.getInstance().player.getCameraPosVec(context.getTickDelta());
                        VoxelShape shape = blockState.getCollisionShape(context.getWorld(), blockPos);
                        double offset = 0.001D;
                        shape.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) ->
                                WorldRenderer.drawBox(
                                        context.getMatrices(),
                                        vertices,
                                        (double) blockPos.getX() + minX - offset - camera.getX(),
                                        (double) blockPos.getY() + minY - offset - camera.getY(),
                                        (double) blockPos.getZ() + minZ - offset - camera.getZ(),

                                        (double) blockPos.getX() + maxX + offset - camera.getX(),
                                        (double) blockPos.getY() + maxY + offset - camera.getY(),
                                        (double) blockPos.getZ() + maxZ + offset - camera.getZ(),

                                        1.0F, 0.2F, 0.2F, 1.0F));
                    }
                }
            }
        }
    }
}
