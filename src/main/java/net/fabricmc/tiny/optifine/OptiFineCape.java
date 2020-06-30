package net.fabricmc.tiny.optifine;

import net.fabricmc.tiny.Constants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.PlayerSkinTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.regex.Pattern;

public class OptiFineCape {

    private static final String URL = "http://s.optifine.net/capes/";
    private static final Logger logger = LogManager.getLogger("OptiFine Capes");
    private static final String CACHE_DIR = "./capeof/";

    private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");

    private final String username;
    private Identifier capeTexture = null;

    public OptiFineCape(String username)
    {
        this.username = username;
    }

    public OptiFineCape pull(String hash)
    {
        if (username != null && !username.isEmpty() && !username.contains("\u0000") && PATTERN_USERNAME.matcher(username).matches())
        {
            String url = URL + username + ".png";
            logger.debug("downloading cape from " + url);
            capeTexture = new Identifier(Constants.MODID, "capeof/" + username.toLowerCase() + ".png");
            TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();

            File cacheFile = new File(CACHE_DIR + hash);
            Identifier fallback = new Identifier(Constants.MODID, "empty.png");
            PlayerSkinTexture texture = new PlayerSkinTexture(cacheFile, url, fallback, false, () -> logger.debug("using cape `" + capeTexture.toString() + "`"));
            textureManager.registerTexture(capeTexture, texture);
            cacheFile.delete();
            return this;
        }
        logger.warn("username not valid");
        return null;
    }

    public boolean hasCape()
    {
        return capeTexture != null;
    }

    public Identifier getCapeTexture()
    {
        return capeTexture;
    }

}
