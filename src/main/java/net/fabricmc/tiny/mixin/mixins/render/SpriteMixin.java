package net.fabricmc.tiny.mixin.mixins.render;

import net.fabricmc.tiny.utils.helper.SpriteHelper;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sprite.class)
public class SpriteMixin {

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(SpriteAtlasTexture spriteAtlasTexture, Sprite.Info info, int maxLevel, int atlasWidth, int atlasHeight, int x, int y, NativeImage nativeImage, CallbackInfo callbackInfo)
    {
        SpriteHelper.putSprite((Sprite) (Object) this);
    }

}
