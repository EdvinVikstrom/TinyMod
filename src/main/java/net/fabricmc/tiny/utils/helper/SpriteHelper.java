package net.fabricmc.tiny.utils.helper;

import net.fabricmc.tiny.mixin.imixin.ISpriteAtlasTexture;
import net.fabricmc.tiny.mixin.imixin.ISpriteAtlasTexture$Data;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.opengl.GL20.*;

public class SpriteHelper {

    private static final Map<Identifier, Sprite> SPRITES = new ConcurrentHashMap<>();

    public static void putSprite(Sprite sprite)
    {
        SPRITES.put(sprite.getId(), sprite);
    }

    public static Sprite getSprite(Identifier identifier)
    {
        return SPRITES.get(identifier);
    }

    // TODO: don't use java.awt
    public static void writeAtlas(String filepath, SpriteAtlasTexture atlasTexture)
    {
        ISpriteAtlasTexture$Data atlasData = (ISpriteAtlasTexture$Data) ((ISpriteAtlasTexture) atlasTexture).getData();
        ByteBuffer texBuffer = BufferUtils.createByteBuffer(atlasData.getWidth() * atlasData.getHeight() * 4);
        glBindTexture(GL_TEXTURE_2D, atlasTexture.getGlId());
        glGetTexImage(GL_TEXTURE_2D, 0, GL_RGBA, GL_UNSIGNED_BYTE, texBuffer);

        BufferedImage image = new BufferedImage(atlasData.getWidth(), atlasData.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
                image.setRGB(x, y, texBuffer.getInt());
        }
        try {
            ImageIO.write(image, "PNG", new File(filepath));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
