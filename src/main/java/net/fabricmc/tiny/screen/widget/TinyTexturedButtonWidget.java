package net.fabricmc.tiny.screen.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class TinyTexturedButtonWidget extends ButtonWidget {

    private final int u, v;
    private final int textureWidth, textureHeight;
    private final Identifier texture;

    public TinyTexturedButtonWidget(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, Identifier texture, PressAction pressAction)
    {
        super(x, y, width, height, LiteralText.EMPTY, pressAction);
        this.u = u;
        this.v = v;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.texture = texture;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        int i = v;
        if (isHovered())
            i+=height;
        MinecraftClient.getInstance().getTextureManager().bindTexture(this.texture);
        drawTexture(matrices, x, y, (float) u, (float) i, width, height, textureWidth, textureHeight);
    }
}
