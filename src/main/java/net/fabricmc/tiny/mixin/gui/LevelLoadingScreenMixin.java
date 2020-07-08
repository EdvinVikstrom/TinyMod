package net.fabricmc.tiny.mixin.gui;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.TinyMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Mixin(LevelLoadingScreen.class)
public class LevelLoadingScreenMixin {

    private static final ConcurrentMap<ChunkPos, Integer[]> chunkMaps = new ConcurrentHashMap<>();

    /*
        inspired/stolen from a image on Discord(The Fabric Project) by @GiantNuker.
        it looked cool so i made a similar thing C:
     */
    @Inject(at = @At("HEAD"), method = "drawChunkMap", cancellable = true)
    private static void drawChunkMap(MatrixStack matrixStack, WorldGenerationProgressTracker worldGenerationProgressTracker, int a, int b, int c, int d, CallbackInfo info)
    {
        if (Config.CHUNK_MAP_PREVIEW.get())
        {
            c = 1;
            int centerX = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
            int centerY = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2;
            int scale = worldGenerationProgressTracker.getSize() / 2;
            int start = (worldGenerationProgressTracker.getSize() / 2) - (scale / 2);
            int end = (worldGenerationProgressTracker.getSize() / 2) + (scale / 2);
            int cx = -(scale / 2);

            double cursorX = MinecraftClient.getInstance().mouse.getX();
            double cursorY = MinecraftClient.getInstance().mouse.getY();

            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            for (int x = start; x < end; x++)
            {
                int cz = -(scale / 2);
                for (int z = start; z < end; z++)
                {
                    ChunkPos chunkPos = new ChunkPos(x, z);
                    ChunkStatus chunkStatus = worldGenerationProgressTracker.getChunkStatus(x, z);
                    int chunkX = cx * 16;
                    int chunkZ = cz * 16;
                    if (chunkStatus != null && chunkStatus.getIndex() >= ChunkStatus.SURFACE.getIndex())
                    {
                        if (!chunkMaps.containsKey(chunkPos))
                        {
                            Integer[] colors = renderChunk(chunkPos);
                            if (colors != null)
                                chunkMaps.put(chunkPos, colors);
                        }
                        Integer[] colors = chunkMaps.get(chunkPos);
                        if (colors != null)
                        {
                            for (int i = 0; i < colors.length; i++)
                            {
                                int ix = (i / 16) * c;
                                int iy = (i % 16) * c;
                                int posX = centerX + ix + chunkX;
                                int posY = centerY + iy + chunkZ;
                                DrawableHelper.fill(matrixStack, posX, posY, posX + c, posY + c, colors[i] | 0xFF000000);
                            }
                        }
                        if (cursorX >= chunkX && cursorX <= chunkX + (16 * c) && cursorY >= chunkZ && cursorY <= chunkZ + (16 * c))
                        {
                            String text = chunkStatus.getId();
                            textRenderer.draw(matrixStack, text, (float) cursorX, (float) cursorY, 16777215);
                        }
                    }
                    cz+=c;
                }
                cx+=c;
            }
            String text = "";
            int textWidth = textRenderer.getWidth(text);
            int textY = centerY + ((scale / 2) * 16 * c) + 6;
            textRenderer.draw(matrixStack, text, centerX - (textWidth / 2.0F), textY, 16777215);
            info.cancel();
        }
    }

    private static boolean warnPrinted = false;
    private static Integer[] renderChunk(ChunkPos chunkPos)
    {
        if (MinecraftClient.getInstance().getServer() == null)
        {
            if (!warnPrinted)
                TinyMod.LOGGER.warn("Server nullptr");
            warnPrinted = true;
            return null;
        }
        ServerWorld world = MinecraftClient.getInstance().getServer().getOverworld();
        if (world == null)
            return null;
        Integer[] colors = new Integer[16 * 16];
        int index = 0;
        WorldChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                int y = 255;
                BlockState blockState = Blocks.AIR.getDefaultState();
                while (blockState.isAir() && y > 0)
                {
                    blockState = chunk.getBlockState(new BlockPos(x, y, z));
                    y--;
                }
                colors[index] = blockState.getMaterial().getColor().color;
                index++;
            }
        }
        return colors;
    }

}
