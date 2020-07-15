package net.fabricmc.tiny.screen;

import net.fabricmc.tiny.render.tiny_renderer.util.ChunkMeshExporter;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.ChunkPos;

import java.io.File;

public class MeshExporterScreen extends Screen {

    private final Screen parent;

    private TextFieldWidget meshNameTextField;
    private ChunkPos from, to;

    public MeshExporterScreen(Screen parent)
    {
        super(new TranslatableText("mesh_exporter.title"));
        this.parent = parent;
    }

    private void export()
    {
        File file = new File(meshNameTextField.getText());
        File dir = file.getParentFile();
        if (dir != null && !dir.exists())
            dir.mkdirs();
        ChunkMeshExporter exporter = new ChunkMeshExporter(client, from, to);
        exporter.buildChunks();
        exporter.exportChunks(meshNameTextField.getText());
    }

    public void exit()
    {
        client.openScreen(parent);
    }

    @Override
    public void onClose()
    {
        exit();
    }

    @Override
    protected void init()
    {
        TextRenderer textRenderer = client.textRenderer;
        meshNameTextField = new TextFieldWidget(textRenderer, 8, 8, 250, 20, new LiteralText("./exported/mesh/untitled"));
        addChild(meshNameTextField);
        addButton(new ButtonWidget(262, 8, 80, 20, new TranslatableText("mesh_exporter.export"), button -> export()));
        ChunkPos chunkPos = new ChunkPos(client.player.chunkX, client.player.chunkZ);
        from = new ChunkPos(chunkPos.x, chunkPos.z);
        to = new ChunkPos(chunkPos.x + 1, chunkPos.z + 1);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderBackground(matrices);
        meshNameTextField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean charTyped(char chr, int keyCode)
    {
        return meshNameTextField.charTyped(chr, keyCode);
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return true;
    }
}
