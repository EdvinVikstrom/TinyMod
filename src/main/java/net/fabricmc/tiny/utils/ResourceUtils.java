package net.fabricmc.tiny.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {

    public static byte[] loadResource(MinecraftClient client, Identifier identifier)
    {
        try {
            InputStream inputStream = client.getResourceManager().getResource(identifier).getInputStream();
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            return data;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
