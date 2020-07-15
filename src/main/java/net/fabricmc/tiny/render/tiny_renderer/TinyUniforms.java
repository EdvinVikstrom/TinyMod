package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.tiny.shader.uniform.IUniformStorage;
import net.fabricmc.tiny.shader.uniform.Uniform;

import java.util.List;

public class TinyUniforms implements IUniformStorage {

    public final Uniform PROJ_MATRIX = new Uniform("projection");
    public final Uniform VIEW_MATRIX = new Uniform("view");
    public final Uniform MODEL_MATRIX = new Uniform("model");

    public final Uniform TEX_SAMPLER = new Uniform("activeTexture");
    public final Uniform NOR_SAMPLER = new Uniform("activeNormalTexture");
    public final Uniform SPECULAR = new Uniform("specular");
    public final Uniform EMISSION = new Uniform("emission");

    @Override
    public void loadUniforms(List<Uniform> uniforms)
    {
        uniforms.add(PROJ_MATRIX);
        uniforms.add(VIEW_MATRIX);
        uniforms.add(MODEL_MATRIX);

        uniforms.add(TEX_SAMPLER);
        uniforms.add(NOR_SAMPLER);
        uniforms.add(SPECULAR);
        uniforms.add(EMISSION);
    }
}
