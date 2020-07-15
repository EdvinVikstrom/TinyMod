package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.tiny.render.api.MeshQuad;
import net.fabricmc.tiny.render.api.Vertex;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.opengl.GL40.*;

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

    public TinyMesh makeMesh(Identifier identifier, List<MeshQuad> quads)
    {
        TinyMesh mesh = TinyMesh.build(identifier, glGenVertexArrays(), quads);

        int positionVBO = glGenBuffers();
        int IBO = glGenBuffers();

        glBindVertexArray(mesh.glID());
        glBindBuffer(GL_ARRAY_BUFFER, positionVBO);
        glBufferData(GL_ARRAY_BUFFER, mesh.vertices(), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, mesh.indices(), GL_STATIC_DRAW);

        int stride = Vertex.BYTES;

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 12);

        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 24);

        glEnableVertexAttribArray(3);
        glVertexAttribPointer(3, 4, GL_FLOAT, false, stride, 32);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);

        glBindVertexArray(0);
        return mesh;
    }

    public void bindMesh(TinyMesh mesh)
    {
        TinyRenderer.INSTANCE.useProgram(TinyRenderer.INSTANCE.meshProgram);

        //mesh.materials().get(0).bind();
        glBindVertexArray(mesh.glID());

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
    }

    public void unbindMesh()
    {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);

        glBindVertexArray(0);
    }

    public void renderMesh(TinyMesh mesh, Matrix4f matrix)
    {
        glPushMatrix();
        glLoadIdentity();

        TinyRenderer.INSTANCE.activeProgram().uniformMatrix4f(TinyRenderer.INSTANCE.getUniforms().MODEL_MATRIX, matrix);
        glDrawElements(GL_TRIANGLES, mesh.vertices().capacity(), GL_UNSIGNED_INT, 0L);

        glPopMatrix();
    }
}
