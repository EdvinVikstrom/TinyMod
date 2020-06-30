package net.fabricmc.tiny.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL40.*;

public class ShaderProgram {

    private static final Logger logger = LogManager.getLogger("ShaderProgram");

    public enum Type {
        FINAL, WORLD
    }

    private int programId;
    private final String vertexShader, fragmentShader, geometryShader;

    public ShaderProgram(String vertexShader, String fragmentShader, String geometryShader)
    {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        this.geometryShader = geometryShader;
    }

    private int compileShader(String data, int type)
    {
        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, data);
        glCompileShader(shaderId);
        IntBuffer result = BufferUtils.createIntBuffer(1);
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, result);
        if (result.get() != GL_TRUE)
        {
            String message = glGetShaderInfoLog(programId);
            logger.error("Failed to compile shader: " + message);
            return -1;
        }
        return shaderId;
    }

    public boolean linkProgram()
    {
        programId = glCreateProgram();
        glUseProgram(programId);
        if (vertexShader != null)
        {
            int result = compileShader(vertexShader, GL_VERTEX_SHADER);
            if (result < 0)
                return false;
            glAttachShader(programId, result);
        }
        if (fragmentShader != null)
        {
            int result = compileShader(fragmentShader, GL_FRAGMENT_SHADER);
            if (result < 0)
                return false;
            glAttachShader(programId, result);
        }
        if (geometryShader != null)
        {
            int result = compileShader(geometryShader, GL_GEOMETRY_SHADER);
            if (result < 0)
                return false;
            glAttachShader(programId, result);
        }
        glLinkProgram(programId);
        IntBuffer result = BufferUtils.createIntBuffer(1);
        glGetProgramiv(programId, GL_LINK_STATUS, result);
        if (result.get() != GL_TRUE)
        {
            String message = glGetProgramInfoLog(programId);
            logger.error("Failed to link program: " + message);
            return false;
        }
        return true;
    }

    public void use()
    {
        glUseProgram(programId);
    }

}
