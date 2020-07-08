package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.material.MaterialFinder;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.minecraft.util.Identifier;

public class TinyRenderer implements Renderer {

    private final TinyMeshManager meshManager;
    private final TinyMaterialManager materialManager;

    public TinyRenderer()
    {
        meshManager = new TinyMeshManager();
        materialManager = new TinyMaterialManager();
    }

    @Override
    public MeshBuilder meshBuilder()
    {
        return meshManager;
    }

    @Override
    public MaterialFinder materialFinder()
    {
        return materialManager;
    }

    @Override
    public RenderMaterial materialById(Identifier identifier)
    {
        return materialManager.getMaterialById(identifier);
    }

    @Override
    public boolean registerMaterial(Identifier identifier, RenderMaterial renderMaterial)
    {
        return materialManager.registerMaterial(identifier, renderMaterial);
    }
}
