package net.fabricmc.tiny.render.tiny_renderer.util;

import net.fabricmc.tiny.render.tiny_renderer.TinyMesh;

import java.util.List;

public abstract class AbstractMeshExporter {

    protected final List<TinyMesh> meshes;

    public AbstractMeshExporter(List<TinyMesh> meshes)
    {
        this.meshes = meshes;
    }

    public abstract boolean export(String filepath);

}
