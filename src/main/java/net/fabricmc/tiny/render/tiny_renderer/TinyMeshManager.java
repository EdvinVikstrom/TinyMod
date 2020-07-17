package net.fabricmc.tiny.render.tiny_renderer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyMeshManager {

    private final Map<Long, TinyMesh> meshes;

    public TinyMeshManager()
    {
        meshes = new ConcurrentHashMap<>();
    }

    
    public void registerMesh(Long identifier, TinyMesh mesh)
    {
        meshes.put(identifier, mesh);
    }
    
    public TinyMesh getMeshById(long identifier)
    {
        return meshes.get(identifier);
    }
}
