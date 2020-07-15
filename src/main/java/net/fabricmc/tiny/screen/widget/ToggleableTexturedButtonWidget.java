package net.fabricmc.tiny.screen.widget;

import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ToggleableTexturedButtonWidget extends TexturedButtonWidget {

    private boolean enabled;

    public ToggleableTexturedButtonWidget(int x, int y, int width, int height, int u, int v, Identifier texture, boolean enabled)
    {
        super(x, y, width, height, u, v, 0, texture, button -> {});
        this.enabled = enabled;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        super.renderButton(matrices, mouseX, mouseY, delta);
        if (enabled)
            fill(matrices, x, y, width, height, 0xA0FFFFFF);
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        enabled = !enabled;
    }
}
