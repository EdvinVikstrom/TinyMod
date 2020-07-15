package net.fabricmc.tiny.shader;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IShaderPack {

    Logger LOGGER = LogManager.getLogger("ShaderPack");

    boolean loadPack();

    Identifier getIdentifier();
    ShaderProgram getProgram(ShaderProgram.Type type);

}
