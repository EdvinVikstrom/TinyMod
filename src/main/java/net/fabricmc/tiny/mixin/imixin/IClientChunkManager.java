package net.fabricmc.tiny.mixin.imixin;

import net.minecraft.client.world.ClientChunkManager;

public interface IClientChunkManager {

    ClientChunkManager.ClientChunkMap getChunkMap();

}
