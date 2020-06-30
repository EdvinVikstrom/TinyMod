package net.fabricmc.tiny.shaders.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.tiny.shaders.AbstractShaderPack;
import net.fabricmc.tiny.shaders.ShaderProgram;
import net.fabricmc.tiny.utils.FileUtils;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class LocalShaderPack extends AbstractShaderPack {

    private final File file;

    private String shaderAuthor;
    private String shaderName;
    private String shaderDesc;
    private int shaderVersion;

    public LocalShaderPack(File file)
    {
        super(new ConcurrentHashMap<>());
        this.file = file;
    }

    @Override
    public void loadShaderData()
    {
        File dataFile = new File(file, "shader.json");
        byte[] data = FileUtils.read(dataFile.getAbsolutePath());
        if (data != null)
        {
            String rawJson = new String(data);
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(rawJson);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            shaderAuthor = jsonObject.get("author").getAsString();
            shaderName = jsonObject.get("name").getAsString();
            shaderDesc = jsonObject.get("description").getAsString();
            shaderVersion = jsonObject.get("version").getAsInt();
        }
    }

    @Override
    public String getShaderId()
    {
        return file.getAbsolutePath().replace("_", "\\_").replace(" ", "_");
    }

    @Override
    public String getShaderAuthor()
    {
        return shaderAuthor;
    }

    @Override
    public String getShaderName()
    {
        return shaderName;
    }

    @Override
    public String getShaderDesc()
    {
        return shaderDesc;
    }

    @Override
    public int getShaderVersion()
    {
        return shaderVersion;
    }

    @Override
    public ShaderProgram loadShaderProgram(ShaderProgram.Type program)
    {
        File programDir = new File(file, "program");
        File[] programs = programDir.listFiles();
        if (programs == null)
            return null;
        byte[] vertex = null, fragment = null, geometry = null;
        for (File file : programs)
        {
            if (file.getName().toLowerCase().startsWith(program.name().toLowerCase() + "."))
            {
                String extension = file.getName().substring(file.getName().indexOf(".") + 1);
                if (extension.equalsIgnoreCase("vsh")) vertex = FileUtils.read(file.getAbsolutePath());
                else if (extension.equalsIgnoreCase("fsh")) fragment = FileUtils.read(file.getAbsolutePath());
                else if (extension.equalsIgnoreCase("gsh")) geometry = FileUtils.read(file.getAbsolutePath());
            }
        }
        return new ShaderProgram(vertex != null ? new String(vertex) : null, fragment != null ? new String(fragment) : null, geometry != null ? new String(geometry) : null);
    }

    @Override
    public void onLoaded()
    {

    }
}
