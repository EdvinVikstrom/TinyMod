package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;

public class TinyMeshManager implements MeshBuilder {

    private final TinyQuadEmitter quadEmitter;
    private final TinyMesh mesh;

    public TinyMeshManager()
    {
        quadEmitter = new TinyQuadEmitter();
        mesh = new TinyMesh();
    }

    @Override
    public QuadEmitter getEmitter()
    {
        return quadEmitter;
    }

    @Override
    public Mesh build()
    {
        return mesh;
    }
}
