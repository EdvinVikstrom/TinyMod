package net.fabricmc.tiny.shader;

import net.fabricmc.tiny.TinyMod;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;

public final class GLSLShader {

    private final int shaderID;
    private final Type type;

    public GLSLShader(int shaderID, Type type)
    {
        this.shaderID = shaderID;
        this.type = type;
    }

    public int shaderID()
    {
        return shaderID;
    }

    public Type type()
    {
        return type;
    }

    public void attach(ShaderProgram program)
    {
        glAttachShader(program.programID(), shaderID());
    }

    public static GLSLShader compileShader(String source, Type type)
    {
        GLSLShader shader = new GLSLShader(glCreateShader(type.gl), type);
        glShaderSource(shader.shaderID(), source);
        glCompileShader(shader.shaderID());
        IntBuffer status = BufferUtils.createIntBuffer(1);
        glGetShaderiv(shader.shaderID(), GL_COMPILE_STATUS, status);
        if (status.get() != GL_TRUE)
        {
            String info = glGetShaderInfoLog(shader.shaderID(), 1024);
            TinyMod.LOGGER.error("[GLSL] failed to compile shader: " + info);
            return null;
        }
        return shader;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        else if (!(o instanceof GLSLShader))
            return false;
        return type() == ((GLSLShader) o).type();
    }

    public enum Type {
        VERTEX(GL_VERTEX_SHADER), FRAGMENT(GL_FRAGMENT_SHADER);

        public final int gl;

        Type(int gl)
        {
            this.gl = gl;
        }

    }

}
