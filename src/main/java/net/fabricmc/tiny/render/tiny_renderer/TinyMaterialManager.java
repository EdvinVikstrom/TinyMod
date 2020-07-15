package net.fabricmc.tiny.render.tiny_renderer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.tiny.Constants;
import net.fabricmc.tiny.TinyMod;
import net.fabricmc.tiny.event.ClientEvent;
import net.fabricmc.tiny.utils.ResourceUtils;
import net.fabricmc.tiny.utils.SpriteUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyMaterialManager implements ClientEvent.Event {

    private static final Identifier MATERIALS = new Identifier(Constants.MODID, "materials.json");

    private final Map<Identifier, TinyMaterial> materials;
    private final List<Identifier> preMaterials;

    public TinyMaterialManager()
    {
        materials = new ConcurrentHashMap<>();
        preMaterials = new ArrayList<>();
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

    public TinyMaterial makeMaterial(Identifier identifier, Vector4f color, Sprite texture, Sprite normal, float specular, float emission)
    {
        return new TinyMaterial(identifier, color, texture, normal, specular, emission, materials.size() + 1);
    }

    public void init(MinecraftClient client)
    {
        loadMaterials(client);
    }

    @Override
    public void ClientEvent_reloadResources(MinecraftClient client)
    {
        loadMaterials(client);
    }

    public boolean loadMaterials(MinecraftClient client)
    {
        for (Identifier key : preMaterials)
        {
            if (materials.containsKey(key))
                materials.remove(key, materials.get(key));
        }
        preMaterials.clear();
        byte[] data = ResourceUtils.loadResource(client, MATERIALS);
        if (data == null)
        {
            TinyMod.LOGGER.error("[MaterialLoader] failed to load materials");
            return false;
        }
        String materialsJson = new String(data);
        JsonObject jsonObject = new JsonParser().parse(materialsJson).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
        {
            Identifier identifier = new Identifier(entry.getKey());
            JsonObject jsonMaterial = entry.getValue().getAsJsonObject();
            float specular = jsonMaterial.has("specular") ? jsonMaterial.get("specular").getAsFloat() : 0.0F;
            float emission = jsonMaterial.has("emission") ? jsonMaterial.get("emission").getAsFloat() : 0.0F;
            float roughness = jsonMaterial.has("roughness") ? jsonMaterial.get("roughness").getAsFloat() : 0.0F;
            Identifier textureID = jsonMaterial.has("texture") ? new Identifier(jsonMaterial.get("texture").getAsString()) : null;
            Identifier normalID = jsonMaterial.has("normal") ? new Identifier(jsonMaterial.get("normal").getAsString()) : null;
            float[] color = new float[] {1.0F, 1.0F, 1.0F, 1.0F};
            if (jsonMaterial.has("color"))
            {
                JsonArray jsonElements = jsonMaterial.get("color").getAsJsonArray();
                for (int i = 0; i < jsonElements.size() && i < color.length; i++)
                    color[i] = jsonElements.get(i).getAsFloat();
            }

            Sprite texture = textureID != null ? SpriteUtils.getSprite(textureID) : null;
            Sprite normal = normalID != null ? SpriteUtils.getSprite(normalID) : null;
            preMaterials.add(identifier);
            registerMaterial(identifier, makeMaterial(identifier, new Vector4f(
                    color[0], color[1], color[2], color[3]
            ), texture, normal, specular, emission));
        }
        return true;
    }
}
