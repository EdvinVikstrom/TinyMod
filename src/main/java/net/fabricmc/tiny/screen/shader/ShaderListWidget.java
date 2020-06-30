package net.fabricmc.tiny.screen.shader;

import net.fabricmc.tiny.shaders.AbstractShaderPack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Collection;

public class ShaderListWidget extends AlwaysSelectedEntryListWidget<ShaderListWidget.ShaderEntry> {

    private final ShaderOptionsScreen screen;

    public ShaderListWidget(ShaderOptionsScreen screen, MinecraftClient minecraftClient, int width, int height, int top, int bottom, int entryHeight)
    {
        super(minecraftClient, width, height, top, bottom, entryHeight);
        this.screen = screen;
    }

    public void updateEntries(Collection<AbstractShaderPack> shaderPacks, String selected)
    {
        clearEntries();
        for (AbstractShaderPack shaderPack : shaderPacks)
        {
            ShaderEntry entry = new ShaderEntry(screen, client, shaderPack);
            if (shaderPack.getShaderId().equals(selected))
                setSelected(entry);
            addEntry(entry);
        }
    }

    public static class ShaderEntry extends AlwaysSelectedEntryListWidget.Entry<ShaderEntry> {

        private final ShaderOptionsScreen screen;
        private final MinecraftClient client;
        private final AbstractShaderPack shaderPack;

        public ShaderEntry(ShaderOptionsScreen screen, MinecraftClient client, AbstractShaderPack shaderPack)
        {
            this.screen = screen;
            this.client = client;
            this.shaderPack = shaderPack;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta)
        {
            TextRenderer textRenderer = client.textRenderer;
            String text = shaderPack.getShaderName();
            int textWidth = textRenderer.getWidth(text);
            int center = x + ((entryWidth / 2) - (textWidth / 2));
            textRenderer.draw(matrices, text, center, y + (textRenderer.fontHeight / 2.0F), 0xFFFFFFFF);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button)
        {
            screen.setSelect(this);
            return false;
        }

        public AbstractShaderPack getShaderPack()
        {
            return shaderPack;
        }
    }

}
