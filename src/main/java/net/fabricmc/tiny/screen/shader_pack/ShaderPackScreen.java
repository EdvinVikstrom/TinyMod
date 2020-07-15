package net.fabricmc.tiny.screen.shader_pack;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

public class ShaderPackScreen extends Screen {

    private final Screen parent;

    public ShaderPackScreen(Screen parent)
    {
        super(new LiteralText("screen.shader_pack"));
        this.parent = parent;
    }

    private void reloadDirectory()
    {

    }

}
