package net.fabricmc.tiny.shader.shader_pack;

import net.fabricmc.tiny.shader.IShaderPack;
import net.fabricmc.tiny.shader.ShaderProgram;
import net.minecraft.util.Identifier;

import java.io.File;

public class TarballShaderPack implements IShaderPack {

    private final File file;

    public TarballShaderPack(File file)
    {
        this.file = file;
    }

    @Override
    public boolean loadPack()
    {
        return false;
    }

    @Override
    public Identifier getIdentifier()
    {
        return null;
    }

    @Override
    public ShaderProgram getProgram(ShaderProgram.Type type)
    {
        return null;
    }
}
