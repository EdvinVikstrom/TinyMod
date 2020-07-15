package net.fabricmc.tiny.mixin.world;

import net.fabricmc.tiny.imixin.IClientChunkManager;
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
