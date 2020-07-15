package net.fabricmc.tiny.shader.shader_pack;

import net.fabricmc.tiny.Constants;
import net.fabricmc.tiny.render.tiny_renderer.TinyUniforms;
import net.fabricmc.tiny.shader.GLSLShader;
import net.fabricmc.tiny.shader.IShaderPack;
import net.fabricmc.tiny.shader.ShaderProgram;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultShaderPack implements IShaderPack {

    private final Identifier identifier = new Identifier(Constants.MODID, "shader_pack/default");
    private final Map<ShaderProgram.Type, ShaderProgram> programs = new ConcurrentHashMap<>();

    private static final String VERTEX_SHADER =
            "#version 400 core\n" +
                    "\n" +
                    "layout(location = 0) in vec4 position;\n" +
                    "layout(location = 1) in vec3 normal;\n" +
                    "layout(location = 2) in vec2 texCoord;\n" +
                    "layout(location = 3) in vec4 color;\n" +
                    "\n" +
                    "uniform mat4 model;\n" +
                    "\n" +
                    "out vec3 f_normal;\n" +
                    "out vec2 f_texCoord;\n" +
                    "out vec4 f_color;\n" +
                    "\n" +
                    "void main()\n" +
                    "{\n" +
                    "  gl_Position = model * position;\n" +
                    "  f_normal = normal;\n" +
                    "  f_texCoord = texCoord;\n" +
                    "  f_color = color;\n" +
                    "}";

    private static final String FRAGMENT_SHADER =
            "#version 400 core\n" +
                    "\n" +
                    "in vec3 f_normal;\n" +
                    "in vec2 f_texCoord;\n" +
                    "in vec4 f_color;\n" +
                    "\n" +
                    "uniform sampler2D texture;\n" +
                    "\n" +
                    "layout(location = 0) out vec4 outColor;\n" +
                    "\n" +
                    "void main()\n" +
                    "{\n" +
                    "    outColor = vec4(0.5, 0.1, 0.6, 1.0);\n" +
                    "}";

    @Override
    public boolean loadPack()
    {
        Set<GLSLShader> shaders = new HashSet<>();
        shaders.add(GLSLShader.compileShader(VERTEX_SHADER, GLSLShader.Type.VERTEX));
        shaders.add(GLSLShader.compileShader(FRAGMENT_SHADER, GLSLShader.Type.FRAGMENT));
        ShaderProgram meshProgram = ShaderProgram.linkProgram(ShaderProgram.Type.MESH, shaders, new TinyUniforms());
        programs.put(ShaderProgram.Type.MESH, meshProgram);
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
