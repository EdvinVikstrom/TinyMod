package net.fabricmc.tiny.render.api;

import java.nio.FloatBuffer;

public class Vertex {

    public final float[] position;
    public final float[] normal;
    public final float[] texCoord;
    public final float[] color;

    public Vertex(float[] position, float[] normal, float[] texCoord, float[] color)
    {
        this.position = position;
        this.normal = normal;
        this.texCoord = texCoord;
        this.color = color;
        if (position.length != 3 || normal.length != 3 || texCoord.length != 2 || color.length != 4)
            throw new IndexOutOfBoundsException(position.length + "/3, " + normal.length + "/3, " + texCoord.length + "/2, " + color.length + "/4");
    }

    public FloatBuffer put(FloatBuffer buffer)
    {
        buffer.put(position[0]);
        buffer.put(position[1]);
        buffer.put(position[2]);
        buffer.put(normal[0]);
        buffer.put(normal[1]);
        buffer.put(normal[2]);
        buffer.put(texCoord[0]);
        buffer.put(texCoord[1]);
        //buffer.put(color[0]);
        //buffer.put(color[1]);
        //buffer.put(color[2]);
        //buffer.put(color[3]);
        return buffer;
    }

    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        else if (!(o instanceof Vertex))
            return false;
        Vertex v = (Vertex) o;
        return v.position[0] == position[0] &&
                v.position[1] == position[1] &&
                v.position[2] == position[2] &&
                v.normal[0] == normal[0] &&
                v.normal[1] == normal[1] &&
                v.normal[2] == normal[2] &&
                v.texCoord[0] == texCoord[0] &&
                v.texCoord[1] == texCoord[1] &&
                v.color[0] == color[0] &&
                v.color[1] == color[1] &&
                v.color[2] == color[2] &&
                v.color[3] == color[3];
    }

    public static final int SIZE = 12;
    public static final int BYTES = SIZE * Float.BYTES;

}
