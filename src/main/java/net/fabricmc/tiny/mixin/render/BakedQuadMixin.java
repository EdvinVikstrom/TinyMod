package net.fabricmc.tiny.mixin.render;

import net.fabricmc.tiny.imixin.IBakedQuad;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BakedQuad.class)
public class BakedQuadMixin implements IBakedQuad {

    @Final @Shadow protected Sprite sprite;

    @Override
    public Sprite getSprite()
    {
        return sprite;
    }
}
