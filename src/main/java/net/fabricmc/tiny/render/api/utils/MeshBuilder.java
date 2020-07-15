package net.fabricmc.tiny.render.api.utils;

import net.fabricmc.tiny.render.api.MeshQuad;
import net.fabricmc.tiny.render.tiny_renderer.TinyMesh;
import net.fabricmc.tiny.render.tiny_renderer.TinyRenderer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MeshBuilder {

    private final List<MeshQuad> quads;

    public MeshBuilder(int initialCapacity)
    {
        quads = new ArrayList<>(initialCapacity);
    }

    public void quad(MeshQuad quad)
    {
        quads.add(quad);
    }

    public TinyMesh build(Identifier identifier)
    {
        return new TinyRenderer().meshManager().makeMesh(identifier, quads);
    }

}
