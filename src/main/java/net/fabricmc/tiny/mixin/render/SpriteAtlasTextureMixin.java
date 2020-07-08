package net.fabricmc.tiny.mixin.render;

import net.fabricmc.tiny.Config;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpriteAtlasTexture.class)
public class SpriteAtlasTextureMixin {

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/texture/SpriteAtlasTexture;bindTexture()V",
            shift = At.Shift.AFTER
    ), method = "tickAnimatedSprites", cancellable = true)
    private void tickAnimatedSprites(CallbackInfo info)
    {
        if (!Config.TEXTURE_ANIMATION.get())
            info.cancel();
    }

}
