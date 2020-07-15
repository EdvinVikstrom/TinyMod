package net.fabricmc.tiny.render.tiny_renderer.util;

import net.fabricmc.tiny.render.tiny_renderer.TinyMaterial;
import net.fabricmc.tiny.render.tiny_renderer.TinyMesh;
import net.fabricmc.tiny.utils.FileUtils;
import net.fabricmc.tiny.utils.SpriteUtils;
import net.minecraft.client.texture.SpriteAtlasTexture;

import java.io.File;
import java.util.*;

public class ObjMeshExporter extends AbstractMeshExporter {

    public ObjMeshExporter(List<TinyMesh> meshes)
    {
        super(meshes);
    }

    @Override
    public boolean export(String filepath)
    {
        String directory = new File(filepath).getParent();
        String fileName = new File(filepath).getName();
        List<SpriteAtlasTexture> textures = new ArrayList<>();
        List<TinyMaterial> materials = new ArrayList<>();
        StringBuilder obj = new StringBuilder();
        obj.append("mtllib ").append(fileName).append(".mtl\n");
        for (TinyMesh mesh : meshes)
        {
            for (TinyMaterial material : mesh.materials())
            {
                materials.add(material);
                if (!textures.contains(material.texture().getAtlas()))
                    textures.add(material.texture().getAtlas());
            }
            obj.append("o ").append(mesh.identifier().getPath()).append("\n");
            List<float[]> positions = new ArrayList<>();
            List<float[]> normals = new ArrayList<>();
            List<float[]> texCoords = new ArrayList<>();
            List<float[]> colors = new ArrayList<>();
            while (mesh.vertices().hasRemaining())
            {
                float[] position = new float[3];
                float[] normal = new float[3];
                float[] texCoord = new float[2];
                //float[] color = new float[4];
                mesh.vertices().get(position);
                mesh.vertices().get(normal);
                mesh.vertices().get(texCoord);
                //mesh.vertices().get(color);
                positions.add(position);
                normals.add(normal);
                texCoords.add(texCoord);
                //colors.add(color);
            }
            for (float[] position : positions)
                obj.append(String.format(Locale.UK, "v %.9f %.9f %.9f\n", position[0], position[1], position[2]));
            for (float[] normal : normals)
                obj.append(String.format(Locale.UK, "vn %.9f %.9f %.9f\n", normal[0], normal[1], normal[2]));
            for (float[] texCoord : texCoords)
                obj.append(String.format(Locale.UK, "vt %.9f %.9f\n", texCoord[0], texCoord[1]));
            /*
            for (float[] color : colors)
                obj.append(String.format(Locale.UK, "what %.9f %.9f %.9f %.9f\n", color[0], color[1], color[2], color[3]));

             */
            obj.append("s off\n");
            int index = 0;
            TinyMaterial boundMaterial = null;
            while (mesh.indices().hasRemaining())
            {
                int[] v1 = new int[3];
                int[] v2 = new int[3];
                int[] v3 = new int[3];
                int[] v4 = new int[3];
                mesh.indices().get(v1);
                mesh.indices().get(v2);
                mesh.indices().get(v3);
                mesh.indices().get(v4);
                TinyMaterial material = mesh.materials().get(mesh.indices().get());
                if (material != boundMaterial && material != null)
                {
                    obj.append("usemtl ").append(material.identifier().getPath().replace("/", ".")).append("\n");
                    boundMaterial = material;
                }
                obj.append(String.format("f %d/%d/%d %d/%d/%d %d/%d/%d %d/%d/%d\n",
                        v1[0] + 1, v1[1] + 1, v1[2] + 1,
                        v2[0] + 1, v2[1] + 1, v2[2] + 1,
                        v3[0] + 1, v3[1] + 1, v3[2] + 1,
                        v4[0] + 1, v4[1] + 1, v4[2] + 1));
                index++;
            }
            obj.append("\n");
            mesh.vertices().flip();
            mesh.indices().flip();
        }
        StringBuilder mtl = new StringBuilder();
        for (TinyMaterial material : materials)
        {
            String texPath = fileName + "-" + material.texture().getAtlas().getId().getPath().replace("/", ".");
            mtl.append("newmtl ").append(material.identifier().getPath().replace("/", ".")).append("\n");
            mtl.append("Ns 323\n");
            mtl.append("Ka 1.0 1.0 1.0\n");
            mtl.append(String.format(Locale.UK, "Kd %.5f %.5f %.5f\n", material.color().getX(), material.color().getY(), material.color().getZ()));
            mtl.append(String.format(Locale.UK, "Ks %.5f %.5f %.5f\n", material.specular(), material.specular(), material.specular()));
            mtl.append(String.format(Locale.UK, "Ke %.5f %.5f %.5f\n", material.emission(), material.emission(), material.emission()));
            mtl.append("Ni 1.45\n");
            mtl.append("illum 2\n");
            mtl.append("map_Kd ").append(texPath).append("\n\n");
        }
        for (SpriteAtlasTexture atlasTexture : textures)
        {
            String texPath = filepath + "-" + atlasTexture.getId().getPath().replace("/", ".");
            SpriteUtils.writeAtlas(texPath, atlasTexture);
        }
        FileUtils.write(filepath + ".obj", obj.toString().getBytes());
        FileUtils.write(filepath + ".mtl", mtl.toString().getBytes());
        return true;
    }
}
