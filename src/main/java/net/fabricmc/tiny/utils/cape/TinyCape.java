package net.fabricmc.tiny.utils.cape;

import net.fabricmc.tiny.Constants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.PlayerSkinTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import java.io.File;

public class TinyCape extends AbstractCape { // TODO

    private static final String DATABASE_URL = "";

    public TinyCape(String username)
    {
        super(username);
    }

    @Override
    public boolean downloadCape()
    {
        String url = DATABASE_URL + username;
        File cacheDir = new File(".cache");
        if (!cacheDir.exists())
            cacheDir.mkdir();
        File cacheFile = new File("");
        Identifier fallback = new Identifier(Constants.MODID + "textures/empty.png");
        PlayerSkinTexture texture = new PlayerSkinTexture(cacheFile, DATABASE_URL + username, fallback, false, null);
        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        return true;
    }

    @Override
    public boolean hasCape()
    {
        return false;
    }

    @Override
    public Identifier getCapeTexture()
    {
        return null;
    }
}
