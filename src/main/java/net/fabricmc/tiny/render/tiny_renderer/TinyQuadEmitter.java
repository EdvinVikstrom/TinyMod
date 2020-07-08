package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;

public class TinyQuadEmitter implements QuadEmitter {

    @Override
    public QuadEmitter material(RenderMaterial renderMaterial)
    {
        return this;
    }

    @Override
    public QuadEmitter cullFace(Direction direction)
    {
        return this;
    }

    @Override
    public QuadEmitter nominalFace(Direction direction)
    {
        return this;
    }

    @Override
    public QuadEmitter colorIndex(int i)
    {
        return this;
    }

    @Override
    public QuadEmitter fromVanilla(int[] ints, int i, boolean b)
    {
        return this;
    }

    @Override
    public QuadEmitter tag(int i)
    {
        return this;
    }

    @Override
    public QuadEmitter pos(int i, float v, float v1, float v2)
    {
        return this;
    }

    @Override
    public MutableQuadView normal(int i, float v, float v1, float v2)
    {
        return this;
    }

    @Override
    public QuadEmitter lightmap(int i, int i1)
    {
        return this;
    }

    @Override
    public QuadEmitter spriteColor(int i, int i1, int i2)
    {
        return this;
    }

    @Override
    public QuadEmitter sprite(int i, int i1, float v, float v1)
    {
        return this;
    }

    @Override
    public QuadEmitter spriteBake(int i, Sprite sprite, int i1)
    {
        return this;
    }

    @Override
    public QuadEmitter emit()
    {
        return this;
    }

    @Override
    public void toVanilla(int i, int[] ints, int i1, boolean b)
    {

    }

    @Override
    public void copyTo(MutableQuadView mutableQuadView)
    {

    }

    @Override
    public RenderMaterial material()
    {
        return null;
    }

    @Override
    public int colorIndex()
    {
        return 0;
    }

    @Override
    public Direction lightFace()
    {
        return null;
    }

    @Override
    public Direction cullFace()
    {
        return null;
    }

    @Override
    public Direction nominalFace()
    {
        return null;
    }

    @Override
    public Vector3f faceNormal()
    {
        return null;
    }

    @Override
    public int tag()
    {
        return 0;
    }

    @Override
    public Vector3f copyPos(int i, Vector3f vector3f)
    {
        return null;
    }

    @Override
    public float posByIndex(int i, int i1)
    {
        return 0;
    }

    @Override
    public float x(int i)
    {
        return 0;
    }

    @Override
    public float y(int i)
    {
        return 0;
    }

    @Override
    public float z(int i)
    {
        return 0;
    }

    @Override
    public boolean hasNormal(int i)
    {
        return false;
    }

    @Override
    public Vector3f copyNormal(int i, Vector3f vector3f)
    {
        return null;
    }

    @Override
    public float normalX(int i)
    {
        return 0;
    }

    @Override
    public float normalY(int i)
    {
        return 0;
    }

    @Override
    public float normalZ(int i)
    {
        return 0;
    }

    @Override
    public int lightmap(int i)
    {
        return 0;
    }

    @Override
    public int spriteColor(int i, int i1)
    {
        return 0;
    }

    @Override
    public float spriteU(int i, int i1)
    {
        return 0;
    }

    @Override
    public float spriteV(int i, int i1)
    {
        return 0;
    }
}
