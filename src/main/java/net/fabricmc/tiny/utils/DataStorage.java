package net.fabricmc.tiny.utils;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DataStorage {

    public static final ConcurrentMap<Identifier, Sprite> SPRITES = new ConcurrentHashMap<>();
    public static final ConcurrentMap<Identifier, SpriteAtlasTexture> SPRITE_ATLASES = new ConcurrentHashMap<>();

}
