package net.fabricmc.tiny.render.tiny_renderer;

import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;

public class TinyMaterial implements RenderMaterial {

    private int spriteDepth;

    public TinyMaterial(int spriteDepth)
    {
        this.spriteDepth = spriteDepth;
    }

    @Override
    public int spriteDepth()
    {
        return spriteDepth;
    }

    public void setSpriteDepth(int spriteDepth)
    {
        this.spriteDepth = spriteDepth;
    }
}
