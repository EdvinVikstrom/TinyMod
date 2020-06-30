package net.fabricmc.tiny.shaders;

import net.fabricmc.tiny.shaders.types.LocalShaderPack;
import net.fabricmc.tiny.shaders.types.NoneShaderPack;
import net.fabricmc.tiny.utils.FileUtils;
import net.minecraft.client.gl.ShaderEffect;

import java.io.File;
import java.util.LinkedHashMap;

public class ShaderManager {

    public static final ShaderManager INSTANCE = new ShaderManager(new File("./shaders"));

    private final File shaderDirectory;
    private final LinkedHashMap<String, AbstractShaderPack> shaderPacks;

    private AbstractShaderPack shaderPack;
    private ShaderEffect shaderEffect;

    public ShaderManager(File shaderDirectory)
    {
        this.shaderDirectory = shaderDirectory;
        shaderPacks = new LinkedHashMap<>();
    }

    public void updateEntries()
    {
        shaderPacks.clear();
        shaderPacks.put("none", new NoneShaderPack());
        if (!shaderDirectory.exists())
            shaderDirectory.mkdir();
        File[] files = shaderDirectory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory() && FileUtils.dirContainsFile(file, "shader.json"))
                {
                    LocalShaderPack shaderPack = new LocalShaderPack(file);
                    shaderPacks.put(shaderPack.getShaderId(), shaderPack);
                }
            }
        }
        for (AbstractShaderPack shaderPack : shaderPacks.values())
            shaderPack.loadShaderData();
    }

    public LinkedHashMap<String, AbstractShaderPack> getShaderPacks()
    {
        return shaderPacks;
    }

    public void useShaderPack(AbstractShaderPack shaderPack)
    {

    }

    public AbstractShaderPack getShaderPack()
    {
        return shaderPack;
    }

    public ShaderEffect getShaderEffect()
    {
        return shaderEffect;
    }
}
