package net.fabricmc.tiny.render.api;

import net.fabricmc.tiny.mixin.imixin.IBakedQuad;
import net.fabricmc.tiny.render.tiny_renderer.TinyMaterial;
import net.fabricmc.tiny.render.tiny_renderer.TinyRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class MeshQuad {

    public final Vertex[] vertices;
    public final TinyMaterial material;

    public MeshQuad(Vertex[] vertices, TinyMaterial material)
    {
        this.vertices = vertices;
        this.material = material;
    }

    public MeshQuad(MatrixStack.Entry matrixEntry, BakedQuad quad, float red, float green, float blue, float alpha)
    {
        Sprite sprite = ((IBakedQuad) quad).getSprite();
        TinyRenderer renderer = TinyRenderer.INSTANCE;
        TinyMaterial material = renderer.materialManager().getMaterialById(sprite.getId());
        if (material == null)
            material = renderer.materialManager().registerMaterial(sprite.getId(), renderer.materialManager().makeMaterial(sprite.getId(), new Vector4f(red, green, blue, alpha), sprite, null, 0.7F, 0.1F));
        this.material = material;

        Vec3i faceVector = quad.getFace().getVector();
        Vector3f normal = new Vector3f((float) faceVector.getX(), (float) faceVector.getY(), (float) faceVector.getZ());
        normal.transform(matrixEntry.getNormal());

        ByteBuffer buffer = BufferUtils.createByteBuffer(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSize());
        IntBuffer intBuffer = buffer.asIntBuffer();
        Vertex[] quadBuffer = new Vertex[4];
        vertices = new Vertex[4];
        for (int i = 0; i < quad.getVertexData().length / 8; ++i)
        {
            intBuffer.clear();
            intBuffer.put(quad.getVertexData(), i * 8, 8);
            float x = buffer.getFloat(0);
            float y = buffer.getFloat(4);
            float z = buffer.getFloat(8);
            float u = buffer.getFloat(16);
            float v = buffer.getFloat(20);
            Vector4f position = new Vector4f(x, y, z, 1.0F);
            position.transform(matrixEntry.getModel());
            vertices[i] = new Vertex(new float[]{position.getX(), position.getY(), position.getZ()}, new float[]{normal.getX(), normal.getY(), normal.getZ()}, new float[]{u, 1.0F - v}, new float[]{red, green, blue, alpha});
        }
        //vertices = MeshUtils.quadToTris(quadBuffer);
    }

    public static final int SIZE = Vertex.SIZE * 4;
    public static final int BYTES = SIZE * Float.BYTES;

}
