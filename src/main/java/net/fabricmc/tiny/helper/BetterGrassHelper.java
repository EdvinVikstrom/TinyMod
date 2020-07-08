package net.fabricmc.tiny.helper;

import net.minecraft.block.GrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public class BetterGrassHelper {

    public static boolean shouldRenderFullBlock(BlockRenderView world, BlockPos blockPos)
    {
        boolean north = world.getBlockState(blockPos.north().down()).getBlock() instanceof GrassBlock;
        boolean south = world.getBlockState(blockPos.south().down()).getBlock() instanceof GrassBlock;
        boolean west = world.getBlockState(blockPos.west().down()).getBlock() instanceof GrassBlock;
        boolean east = world.getBlockState(blockPos.east().down()).getBlock() instanceof GrassBlock;
        return north || south || west || east;
    }

}
