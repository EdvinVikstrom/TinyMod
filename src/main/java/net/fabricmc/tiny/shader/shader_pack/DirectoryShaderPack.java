package net.fabricmc.tiny.shader.shader_pack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.tiny.Constants;
import net.fabricmc.tiny.shader.GLSLShader;
import net.fabricmc.tiny.shader.IShaderPack;
import net.fabricmc.tiny.shader.ShaderProgram;
import net.fabricmc.tiny.utils.reading.FileUtils;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.EnumUtils;

import java.io.File;
import java.util.*;

public class DirectoryShaderPack implements IShaderPack {

    private final File file;

    private Identifier identifier;
    private final Map<ShaderProgram.Type, ShaderProgram> programs;

    public DirectoryShaderPack(File file)
    {
        this.file = file;
        programs = new LinkedHashMap<>();
    }

    @Override
    public boolean loadPack()
    {
        File packFile = new File(file, "pack.json");
        if (!packFile.exists())
        {
            LOGGER.error("`pack.json` file not found");
            return false;
        }
        byte[] packFileData = FileUtils.read(packFile.getAbsolutePath());
        if (packFileData == null)
        {
            LOGGER.error("failed to read file `pack.json`");
            return false;
        }

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(new String(packFileData)).getAsJsonObject();
        identifier = new Identifier(Constants.MODID, "shader_pack/" + (jsonObject.has("name") ? jsonObject.get("name").getAsString() : packFile.getPath()));
        if (jsonObject.has("program"))
        {
            JsonArray programArray = jsonObject.get("program").getAsJsonArray();
            for (int i = 0; i < programArray.size(); i++)
            {
                JsonObject programObject = programArray.get(i).getAsJsonObject();
                String programTypeString = programObject.get("type").getAsString().toUpperCase();
                if (!EnumUtils.isValidEnum(ShaderProgram.Type.class, programTypeString))
                {
                    LOGGER.error("unknown program type `" + programTypeString + "`");
                    return false;
                }
                ShaderProgram.Type programType = ShaderProgram.Type.valueOf(programTypeString);
                Set<GLSLShader> shaders = new HashSet<>();
                JsonArray shaderArray = programObject.get("shader").getAsJsonArray();
                for (int j = 0; j < shaderArray.size(); j++)
                {
                    JsonObject shaderObject = shaderArray.get(j).getAsJsonObject();
                    String shaderTypeString = shaderObject.get("type").getAsString();
                    if (!EnumUtils.isValidEnum(GLSLShader.Type.class, shaderTypeString))
                    {
                        LOGGER.error("unknown shader type `" + shaderTypeString + "`");
                        return false;
                    }
                    GLSLShader.Type shaderType = GLSLShader.Type.valueOf(shaderTypeString);
                    String shaderFilePath = file.getAbsolutePath() + "/" + shaderObject.get("path").getAsString();
                    if (!FileUtils.fileExists(shaderFilePath))
                    {
                        LOGGER.error("shader not found `" + shaderFilePath + "`");
                        return false;
                    }
                    byte[] shaderData = FileUtils.read(shaderFilePath);
                    if (shaderData == null)
                    {
                        LOGGER.error("failed to read file `" + shaderFilePath + "`");
                        return false;
                    }
                    shaders.add(GLSLShader.compileShader(new String(shaderData), shaderType));
                }
                programs.put(programType, ShaderProgram.linkProgram(programType, shaders, null));
            }
        }
        return true;
    }

    @Override
    public Identifier getIdentifier()
    {
        return identifier;
    }

    @Override
    public ShaderProgram getProgram(ShaderProgram.Type type)
    {
        return programs.get(type);
    }
}
