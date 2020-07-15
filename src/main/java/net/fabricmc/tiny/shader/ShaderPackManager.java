package net.fabricmc.tiny.shader;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.shader.exceptions.FailedToLoadShaderPackException;
import net.fabricmc.tiny.shader.shader_pack.DefaultShaderPack;
import net.fabricmc.tiny.shader.shader_pack.DirectoryShaderPack;
import net.fabricmc.tiny.shader.shader_pack.TarballShaderPack;

import java.io.File;
import java.util.*;

public final class ShaderPackManager {

    public static final ShaderPackManager INSTANCE = new ShaderPackManager(new File("shaderpacks"));

    private final File directory;
    private final List<IShaderPack> shaderPacks;

    private IShaderPack defaultPack;

    public ShaderPackManager(File directory)
    {
        this.directory = directory;
        shaderPacks = new ArrayList<>();
        reloadDirectory();
    }

    public void loadDefaultPack()
    {
        defaultPack = new DefaultShaderPack();
        if (!defaultPack.loadPack())
            throw new FailedToLoadShaderPackException("default pack");
    }

    public void reloadDirectory()
    {
        File[] files = directory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                    pushShaderPack(new DirectoryShaderPack(file));
                else if (file.getAbsolutePath().endsWith(".tar"))
                    pushShaderPack(new TarballShaderPack(file));
            }
        }
    }

    public void pushShaderPack(IShaderPack pack)
    {
        shaderPacks.add(pack);
    }

    public void pullShaderPack(IShaderPack pack)
    {
        shaderPacks.remove(pack);
    }

    public IShaderPack getActivePack()
    {
        if (Config.ACTIVE_SHADER_PACK.get().intValue() == -1)
            return defaultPack;
        return shaderPacks.get(Config.ACTIVE_SHADER_PACK.get().intValue());
    }

    public void loadShaderPack(IShaderPack pack)
    {
        int index = shaderPacks.indexOf(pack);
        if (index >= 0)
        {
            if (!pack.loadPack())
                throw new FailedToLoadShaderPackException(pack.getIdentifier().toString());
            Config.ACTIVE_SHADER_PACK.set((double) index);
        }
    }

    public List<IShaderPack> getShaderPacks()
    {
        return shaderPacks;
    }

    public void init()
    {
        loadDefaultPack();
    }
}
