package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.MaterialFinder;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.minecraft.util.Identifier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TinyMaterialManager implements MaterialFinder {

    private final ConcurrentMap<Identifier, TinyMaterial> materials = new ConcurrentHashMap<>();
    private final TinyMaterial material = new TinyMaterial(1);

    public TinyMaterial getMaterialById(Identifier id)
    {
        return materials.get(id);
    }

    public boolean registerMaterial(Identifier id, RenderMaterial material)
    {
        if (materials.containsKey(id))
            return false;
        return materials.put(id, new TinyMaterial(material.spriteDepth())) != null;
    }

    @Override
    public RenderMaterial find()
    {
        return material;
    }

    @Override
    public MaterialFinder clear()
    {
        return this;
    }

    @Override
    public MaterialFinder spriteDepth(int i)
    {
        material.setSpriteDepth(i);
        return this;
    }

    @Override
    public MaterialFinder blendMode(int i, BlendMode blendMode)
    {
        return this;
    }

    @Override
    public MaterialFinder disableColorIndex(int i, boolean b)
    {
        return this;
    }

    @Override
    public MaterialFinder disableDiffuse(int i, boolean b)
    {
        return this;
    }

    @Override
    public MaterialFinder disableAo(int i, boolean b)
    {
        return this;
    }

    @Override
    public MaterialFinder emissive(int i, boolean b)
    {
        return this;
    }
}
