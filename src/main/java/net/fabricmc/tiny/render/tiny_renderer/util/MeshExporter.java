package net.fabricmc.tiny.render.tiny_renderer.util;

import net.fabricmc.tiny.render.tiny_renderer.TinyMesh;

import java.util.ArrayList;
import java.util.List;

public final class MeshExporter {

    private final List<TinyMesh> meshes;

    public MeshExporter()
    {
        meshes = new ArrayList<>();
    }

    public void append(TinyMesh mesh)
    {
        meshes.add(mesh);
    }

    public void exportMesh(String filepath, Format format)
    {
        AbstractMeshExporter exporter = null;
        if (format == Format.COLLADA)
            exporter = new ColladaMeshExporter(meshes);
        else if (format == Format.OBJ)
            exporter = new ObjMeshExporter(meshes);
        if (exporter != null)
            exporter.export(filepath);
    }

    public enum Format {
        COLLADA, OBJ
    }
}
