package net.fabricmc.tiny.mixin.mixins.world;

import net.fabricmc.tiny.mixin.imixin.IClientChunkManager;
import net.minecraft.client.world.ClientChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientChunkManager.class)
public class ClientChunkManagerMixin implements IClientChunkManager {

    @Shadow private volatile ClientChunkManager.ClientChunkMap chunks;

    @Override
    public ClientChunkManager.ClientChunkMap getChunkMap()
    {
        return chunks;
    }
}
