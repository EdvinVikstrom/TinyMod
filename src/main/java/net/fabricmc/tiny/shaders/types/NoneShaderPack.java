package net.fabricmc.tiny.shaders.types;

import net.fabricmc.tiny.shaders.AbstractShaderPack;
import net.fabricmc.tiny.shaders.ShaderProgram;

import java.util.concurrent.ConcurrentHashMap;

public class NoneShaderPack extends AbstractShaderPack {

    public NoneShaderPack()
    {
        super(new ConcurrentHashMap<>());
    }

    @Override
    public void loadShaderData()
    {

    }

    @Override
    public String getShaderId()
    {
        return "none";
    }

    @Override
    public String getShaderAuthor()
    {
        return "";
    }

    @Override
    public String getShaderName()
    {
        return "none";
    }

    @Override
    public String getShaderDesc()
    {
        return "no shader pack";
    }

    @Override
    public int getShaderVersion()
    {
        return -1;
    }

    @Override
    public ShaderProgram loadShaderProgram(ShaderProgram.Type program)
    {
        return null;
    }

    @Override
    public void onLoaded()
    {

    }
}
