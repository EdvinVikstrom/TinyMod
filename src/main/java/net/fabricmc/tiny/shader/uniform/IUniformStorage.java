package net.fabricmc.tiny.shader.uniform;

import net.fabricmc.tiny.shader.uniform.Uniform;

import java.util.List;

public interface IUniformStorage {
    void loadUniforms(List<Uniform> uniforms);
}
