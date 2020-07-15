package net.fabricmc.tiny.shader;

import net.fabricmc.tiny.TinyMod;
import net.fabricmc.tiny.shader.uniform.IUniformStorage;
import net.fabricmc.tiny.shader.uniform.Uniform;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.BufferUtils;

import javax.annotation.Nullable;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.lwjgl.opengl.GL40.*;

public final class ShaderProgram {

    private final Type type;
    private final int programID;
    private final Set<GLSLShader> shaders;
    private IUniformStorage uniformStorage;

    public ShaderProgram(Type type, int programID, Set<GLSLShader> shaders, @Nullable IUniformStorage uniformStorage)
    {
        this.type = type;
        this.programID = programID;
        this.shaders = shaders;
        this.uniformStorage = uniformStorage;
    }

    public Type type()
    {
        return type;
    }

    public int programID()
    {
        return programID;
    }

    public Set<GLSLShader> shaders()
    {
        return shaders;
    }

    private void checkUniform(Uniform uniform)
    {
        if (!uniform.loaded())
            uniform.load(this);
    }

    public void loadUniforms(IUniformStorage uniformStorage)
    {
        this.uniformStorage = uniformStorage;
        List<Uniform> uniforms = new ArrayList<>();
        uniformStorage.loadUniforms(uniforms);
        for (Uniform uniform : uniforms)
            checkUniform(uniform);
    }

    public void uniform1f(Uniform uniform, float f)
    {
        checkUniform(uniform);
        glUniform1f(uniform.location(), f);
    }
    public void uniform1i(Uniform uniform, int i)
    {
        checkUniform(uniform);
        glUniform1i(uniform.location(), i);
    }
    public void uniform1ui(Uniform uniform, int i)
    {
        checkUniform(uniform);
        glUniform1ui(uniform.location(), i);
    }
    public void uniformMatrix4f(Uniform uniform, Matrix4f matrix)
    {
        checkUniform(uniform);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.writeToBuffer(buffer);
        glUniformMatrix4fv(uniform.location(), false, buffer);
    }

    public void use()
    {
        glUseProgram(programID());
    }

    public boolean link()
    {
        glLinkProgram(programID());
        IntBuffer result = BufferUtils.createIntBuffer(1);
        glGetProgramiv(programID(), GL_LINK_STATUS, result);
        if (result.get() != GL_TRUE)
        {
            String info = glGetProgramInfoLog(programID(), 1024);
            TinyMod.LOGGER.error("[ShaderProgram] failed to link program: " + info);
            return false;
        }
        if (uniformStorage != null)
            loadUniforms(uniformStorage);
        return true;
    }

    public static ShaderProgram linkProgram(Type type, Set<GLSLShader> shaders, @Nullable IUniformStorage uniformStorage)
    {
        ShaderProgram program = new ShaderProgram(type, glCreateProgram(), shaders, uniformStorage);
        for (GLSLShader shader : shaders)
            shader.attach(program);
        if (!program.link())
            return null;
        return program;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        else if (!(o instanceof ShaderProgram))
            return false;
        return type() == ((ShaderProgram) o).type();
    }

    public enum Type {
        FINAL, MESH
    }

}
