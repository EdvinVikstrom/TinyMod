package net.fabricmc.tiny.imixin;

import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Set;

public interface ISpriteAtlasTexture$Data {

    Set<Identifier> getSpriteIds();
    int getWidth();
    int getHeight();
    int getMaxLevel();
    List<Sprite> getSprites();

}
