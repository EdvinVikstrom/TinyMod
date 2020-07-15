package net.fabricmc.tiny.utils;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class BufferUtilities {

    public static FloatBuffer expandFloatBuffer(FloatBuffer buffer, int capacity)
    {
        float[] array = new float[buffer.capacity()];
        buffer.position(0);
        buffer.get(array);
        FloatBuffer expanded = BufferUtils.createFloatBuffer(array.length + capacity);
        expanded.put(array);
        return expanded;
    }

}
