package net.fabricmc.tiny.shaders;

import java.util.concurrent.ConcurrentMap;

public abstract class AbstractShaderPack {

    private final ConcurrentMap<net.fabricmc.tiny.shaders.ShaderProgram.Type, ShaderProgram> programs;

    public AbstractShaderPack(ConcurrentMap<ShaderProgram.Type, ShaderProgram> programs)
    {
        this.programs = programs;
    }

    public abstract void loadShaderData();
    public abstract String getShaderId();
    public abstract String getShaderAuthor();
    public abstract String getShaderName();
    public abstract String getShaderDesc();
    public abstract int getShaderVersion();
    public abstract ShaderProgram loadShaderProgram(ShaderProgram.Type program);
    public abstract void onLoaded();

    public void putShaderProgram(ShaderProgram.Type type, ShaderProgram program)
    {
        programs.put(type, program);
    }

    public ShaderProgram getShaderProgram(ShaderProgram.Type type)
    {
        return programs.get(type);
    }

    public ConcurrentMap<ShaderProgram.Type, ShaderProgram> getPrograms()
    {
        return programs;
    }
}
