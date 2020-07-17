package net.fabricmc.tiny.mixin.mixins.render;

import net.fabricmc.tiny.mixin.imixin.ISpriteAtlasTexture$Data;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Set;

@Mixin(SpriteAtlasTexture.Data.class)
public class SpriteAtlasTexture$DataMixin implements ISpriteAtlasTexture$Data {

    @Final @Shadow Set<Identifier> spriteIds;
    @Final @Shadow int width;
    @Final @Shadow int height;
    @Final @Shadow int maxLevel;
    @Final @Shadow List<Sprite> sprites;

    @Override
    public Set<Identifier> getSpriteIds()
    {
        return spriteIds;
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    @Override
    public int getMaxLevel()
    {
        return maxLevel;
    }

    @Override
    public List<Sprite> getSprites()
    {
        return sprites;
    }
}
