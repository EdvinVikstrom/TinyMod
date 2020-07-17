package net.fabricmc.tiny.utils.helper;

import java.nio.ByteBuffer;

public class ColorHelper {

    public static int getRGBA(float red, float green, float blue, float alpha)
    {
        int r = (int) (red * 255.0F);
        int g = (int) (green * 255.0F);
        int b = (int) (blue * 255.0F);
        int a = (int) (alpha * 255.0F);
        return ByteBuffer.wrap(new byte[]{
                (byte) a, (byte) b, (byte) g, (byte) r
        }).getInt();
    }

}
