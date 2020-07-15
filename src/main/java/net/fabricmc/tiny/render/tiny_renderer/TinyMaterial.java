package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.tiny.shader.ShaderProgram;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.Identifier;

import static org.lwjgl.opengl.GL20.*;

public class TinyMaterial {

    private final Identifier identifier;
    private Vector4f color;
    private Sprite texture, normal;
    private float specular, emission;
    private final int index;

    protected TinyMaterial(Identifier identifier, Vector4f color, Sprite texture, Sprite normal, float specular, float emission, int index)
    {
        this.identifier = identifier;
        this.color = color;
        this.texture = texture;
        this.normal = normal;
        this.specular = specular;
        this.emission = emission;
        this.index = index;
    }

    public void bind()
    {
        TinyRenderer renderer = TinyRenderer.INSTANCE;
        ShaderProgram program = renderer.activeProgram();

        program.uniform1i(renderer.getUniforms().TEX_SAMPLER, 0);
        program.uniform1i(renderer.getUniforms().NOR_SAMPLER, 1);
        program.uniform1f(renderer.getUniforms().SPECULAR, specular);
        program.uniform1f(renderer.getUniforms().EMISSION, emission);
        glBindTexture(GL_TEXTURE_2D, texture.getAtlas().getGlId());
    }

    public Identifier identifier()
    {
        return identifier;
    }

    public Vector4f color()
    {
        return color;
    }

    public void color(Vector4f color)
    {
        this.color = color;
    }

    public Sprite texture()
    {
        return texture;
    }
    
    public void texture(Sprite texture)
    {
        this.texture = texture;
    }
    
    public Sprite normal()
    {
        return normal;
    }
    
    public void normal(Sprite normal)
    {
        this.normal = normal;
    }
    
    public float specular()
    {
        return specular;
    }
    
    public void specular(float specular)
    {
        this.specular = specular;
    }
    
    public float emission()
    {
        return emission;
    }
    
    public void emission(float emission)
    {
        this.emission = emission;
    }
    
    public int index()
    {
        return index;
    }
}
