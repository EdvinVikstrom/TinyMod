package net.fabricmc.tiny.render.tiny_renderer_wip;

import net.fabricmc.tiny.shader.ShaderProgram;
import net.minecraft.client.texture.Sprite;

import static org.lwjgl.opengl.GL20.*;

public class TinyMaterial {

    private Sprite texture, normal;
    private float specular, emission;
    private final int index;

    protected TinyMaterial(Sprite texture, Sprite normal, float specular, float emission, int index)
    {
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
