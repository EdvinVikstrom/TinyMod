package net.fabricmc.tiny.render.tiny_renderer.util;

import net.fabricmc.tiny.render.tiny_renderer.TinyMesh;
import net.fabricmc.tiny.render.tiny_renderer.util.format.ColladaMeshExporter;
import net.fabricmc.tiny.render.tiny_renderer.util.format.ObjMeshExporter;

import java.util.ArrayList;
import java.util.List;

public final class MeshExporter {

    private final Format format;
    private final List<TinyMesh> meshes;

    public MeshExporter(Format format)
    {
        this.format = format;
        meshes = new ArrayList<>();
    }

    public Format getFormat()
    {
        return format;
    }

    public void append(TinyMesh mesh)
    {
        meshes.add(mesh);
    }

    public void exportMeshes(String filepath)
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
