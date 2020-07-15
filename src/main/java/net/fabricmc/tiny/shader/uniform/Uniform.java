package net.fabricmc.tiny.shader.uniform;

import net.fabricmc.tiny.shader.ShaderProgram;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public final class Uniform {
    private final String name;
    private int location;

    public Uniform(String name, int location)
    {
        this.name = name;
        this.location = location;
    }

    public Uniform(String name)
    {
        this.name = name;
        location = -2;
    }

    public String name()
    {
        return name;
    }

    public int location()
    {
        return location;
    }

    public boolean loaded()
    {
        return location >= -1;
    }

    public void load(ShaderProgram program)
    {
        location = glGetUniformLocation(program.programID(), name);
    }
}
