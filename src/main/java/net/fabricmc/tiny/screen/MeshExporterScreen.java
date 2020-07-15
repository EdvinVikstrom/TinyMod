package net.fabricmc.tiny.screen;

import net.fabricmc.tiny.render.tiny_renderer.util.MeshExporter;
import net.fabricmc.tiny.render.tiny_renderer.world.ChunkMeshBuilder;
import net.fabricmc.tiny.screen.widget.EnumButtonWidget;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.ChunkPos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// WIP
public class MeshExporterScreen extends Screen {

    private static final int NAME_TEXT_WIDTH = 250, NAME_TEXT_HEIGHT = 20;
    private static final int EXPORT_BUTTON_WIDTH = 60, EXPORT_BUTTON_HEIGHT = 20;
    private static final int FORMAT_BUTTON_WIDTH = 60, FORMAT_BUTTON_HEIGHT = 20;

    private final Screen parent;
    private final List<ChunkPos> chunks;

    private TextFieldWidget nameTextField;
    private EnumButtonWidget formatButton;

    public MeshExporterScreen(Screen parent)
    {
        super(new TranslatableText("mesh_exporter.title"));
        chunks = new ArrayList<>();
        this.parent = parent;
    }

    private void export()
    {
        File file = new File(nameTextField.getText());
        File dir = file.getParentFile();
        if (dir != null && !dir.exists())
            dir.mkdirs();
        ChunkMeshBuilder exporter = new ChunkMeshBuilder(client, client.world, chunks);
        exporter.renderChunks();
        exporter.exportChunks(nameTextField.getText(), new MeshExporter((MeshExporter.Format) formatButton.getValue()));
    }

    private void exit()
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
        nameTextField = new TextFieldWidget(textRenderer, (width / 2) - (NAME_TEXT_WIDTH / 2), (height / 2) - (NAME_TEXT_HEIGHT / 2), NAME_TEXT_WIDTH, NAME_TEXT_HEIGHT, nameTextField, new LiteralText("./exported/mesh/untitled"));
        ButtonWidget exportButton = new ButtonWidget(nameTextField.x + nameTextField.getWidth() - EXPORT_BUTTON_WIDTH, nameTextField.y + nameTextField.getHeight() + 2, EXPORT_BUTTON_WIDTH, EXPORT_BUTTON_HEIGHT, new TranslatableText("mesh_exporter.export"), button -> export());
        formatButton = new EnumButtonWidget(exportButton.x - FORMAT_BUTTON_WIDTH - 2, exportButton.y, FORMAT_BUTTON_WIDTH, FORMAT_BUTTON_HEIGHT, MeshExporter.Format.values(), 0);
        addChild(nameTextField);
        addButton(exportButton);
        addButton(formatButton);
        renderChunkMap();
    }

    private void renderChunkMap()
    {
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderBackground(matrices);
        nameTextField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean charTyped(char chr, int keyCode)
    {
        return nameTextField.charTyped(chr, keyCode);
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return true;
    }
}
