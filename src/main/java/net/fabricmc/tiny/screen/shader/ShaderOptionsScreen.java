package net.fabricmc.tiny.screen.shader;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.shaders.AbstractShaderPack;
import net.fabricmc.tiny.shaders.ShaderManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ShaderOptionsScreen extends Screen {

    private final Screen parent;
    private ShaderListWidget shaderListWidget;

    public ShaderOptionsScreen(Screen parent)
    {
        super(new LiteralText("options.shaders"));
        this.parent = parent;
    }

    private void exit()
    {
        String shaderPackId = shaderListWidget.getSelected() != null ? shaderListWidget.getSelected().getShaderPack().getShaderId() : "none";
        if (!shaderPackId.equals(Config.SHADER_PACK) && ShaderManager.INSTANCE.getShaderPacks().containsKey(shaderPackId))
        {
            Config.SHADER_PACK = shaderPackId;
            AbstractShaderPack shaderPack = ShaderManager.INSTANCE.getShaderPacks().get(shaderPackId);
            ShaderSplashScreen splashScreen = new ShaderSplashScreen(client, shaderPack);
            client.setOverlay(splashScreen);
        }
        client.openScreen(parent);
    }

    private void cancel()
    {
        client.openScreen(parent);
    }

    @Override
    protected void init()
    {
        ShaderManager.INSTANCE.updateEntries();
        shaderListWidget = new ShaderListWidget(this, client, width, height, 32, height - 64, 20);
        shaderListWidget.updateEntries(ShaderManager.INSTANCE.getShaderPacks().values(), Config.SHADER_PACK);

        this.addChild(shaderListWidget);
        this.addButton(new ButtonWidget(this.width / 2 - 100 + 150, this.height - 27, 200, 20, ScreenTexts.DONE, (button) -> exit()));
        this.addButton(new ButtonWidget(this.width / 2 - 100 - 150, this.height - 27, 200, 20, ScreenTexts.CANCEL, (button) -> cancel()));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 16777215);
        shaderListWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose()
    {
        exit();
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return true;
    }

    public void setSelect(ShaderListWidget.ShaderEntry entry)
    {
        shaderListWidget.setSelected(entry);
    }

}
