package net.fabricmc.tiny.render.tiny_renderer;

import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyMaterialManager {

    private final Map<Identifier, TinyMaterial> materials;

    public TinyMaterialManager()
    {
        materials = new ConcurrentHashMap<>();
    }

    public TinyMaterial registerMaterial(Identifier identifier, TinyMaterial material)
    {
        materials.put(identifier, material);
        return material;
    }

    public TinyMaterial getMaterialById(Identifier identifier)
    {
        return materials.get(identifier);
    }

    public TinyMaterial makeMaterial(Sprite texture, Sprite normal, float specular, float emission)
    {
        return new TinyMaterial(texture, normal, specular, emission, materials.size() + 1);
    }
}
