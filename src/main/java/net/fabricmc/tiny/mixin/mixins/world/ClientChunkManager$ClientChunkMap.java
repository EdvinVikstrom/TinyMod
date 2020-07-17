package net.fabricmc.tiny.mixin.mixins.world;

import net.fabricmc.tiny.mixin.imixin.IClientChunkManager$ClientChunkMap;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.concurrent.atomic.AtomicReferenceArray;

@Mixin(ClientChunkManager.ClientChunkMap.class)
public class ClientChunkManager$ClientChunkMap implements IClientChunkManager$ClientChunkMap {

    @Shadow @Final private AtomicReferenceArray<WorldChunk> chunks;

    @Override
    public AtomicReferenceArray<WorldChunk> getChunks()
    {
        return chunks;
    }
}
