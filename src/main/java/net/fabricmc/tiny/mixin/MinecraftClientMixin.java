package net.fabricmc.tiny.mixin;

import net.fabricmc.tiny.imixin.IMinecraftClientMixin;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements IMinecraftClientMixin {

    @Shadow private static int currentFps;

    @Override
    public int getCurrentFps()
    {
        return currentFps;
    }
}
