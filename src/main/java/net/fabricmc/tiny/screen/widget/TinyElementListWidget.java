package net.fabricmc.tiny.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class TinyElementListWidget extends AbstractParentElement {

    private final int x, y;
    private final int width, height, entryWidth;

    private final List<AbstractEntry> entries;
    private final List<Element> children;

    public TinyElementListWidget(int x, int y, int width, int height, int entryWidth)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entryWidth = entryWidth;
        entries = new ArrayList<>();
        children = new ArrayList<>();
    }

    public void addEntry(AbstractEntry entry)
    {
        entries.add(entry);
    }

    public void clearEntries()
    {
        entries.clear();
    }

    protected void renderBounds()
    {
        int left = 0;
        int right = width;
        int top = y;
        int bottom = height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        MinecraftClient.getInstance().getTextureManager().bindTexture(DrawableHelper.BACKGROUND_TEXTURE);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(519);
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(left, top, -100.0D).texture(0.0F, (float) top / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((left + width), (double)top, -100.0D).texture((float) width / 32.0F, (float) top / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((left + width), 0.0D, -100.0D).texture((float) width / 32.0F, 0.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(left, 0.0D, -100.0D).texture(0.0F, 0.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(left, height, -100.0D).texture(0.0F, (float) height / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((left + width), height, -100.0D).texture((float) width / 32.0F, (float) height / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((left + width), bottom, -100.0D).texture((float) width / 32.0F, (float) bottom / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(left, bottom, -100.0D).texture(0.0F, (float) bottom / 32.0F).color(64, 64, 64, 255).next();
        tessellator.draw();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
        RenderSystem.disableAlphaTest();
        RenderSystem.shadeModel(7425);
        RenderSystem.disableTexture();
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(left, (top + 4), 0.0D).texture(0.0F, 1.0F).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(right, (top + 4), 0.0D).texture(1.0F, 1.0F).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(right, top, 0.0D).texture(1.0F, 0.0F).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(left, top, 0.0D).texture(0.0F, 0.0F).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(left, bottom, 0.0D).texture(0.0F, 1.0F).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(right, bottom, 0.0D).texture(1.0F, 1.0F).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(right, (bottom - 4), 0.0D).texture(1.0F, 0.0F).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(left, (bottom - 4), 0.0D).texture(0.0F, 0.0F).color(0, 0, 0, 0).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7424);
        RenderSystem.enableAlphaTest();
        RenderSystem.disableBlend();
    }

    public void renderList(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        children.clear();
        int y = 0;
        for (int i = 0; i < entries.size(); i++)
        {
            AbstractEntry entry = entries.get(i);
            entry.render(matrices, i, this.y + y, x, entryWidth, entry.getHeight(), mouseX, mouseY, false, delta);
            children.addAll(entry.children());
            for (int j = 0; j < entry.entries().size(); j++)
            {
                AbstractEntry child = entry.entries().get(j);
                child.render(matrices, j, this.y + y, x, entryWidth, child.getHeight(), mouseX, mouseY, false, delta);
                y+=child.getHeight();
            }
            y+=entry.getHeight();
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderList(matrices, mouseX, mouseY, delta);
        renderBounds();
    }

    @Override
    public List<? extends Element> children()
    {
        return children;
    }

    public static abstract class AbstractEntry {

        protected final List<AbstractEntry> entries = new ArrayList<>();
        protected final List<Element> children = new ArrayList<>();

        public abstract int getHeight();
        public abstract void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta);
        public List<AbstractEntry> entries()
        {
            return entries;
        }
        public List<? extends Element> children()
        {
            return children;
        }
    }

}
