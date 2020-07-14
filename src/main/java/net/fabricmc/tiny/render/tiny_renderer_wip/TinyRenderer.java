package net.fabricmc.tiny.render.tiny_renderer_wip;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.shader.IShaderPack;
import net.fabricmc.tiny.shader.ShaderPackManager;
import net.fabricmc.tiny.shader.ShaderProgram;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;

public class TinyRenderer implements RenderEvent.Event {

    public static final TinyRenderer INSTANCE = new TinyRenderer();
    public static final Logger LOGGER = LogManager.getLogger("TinyRenderer");

    private final TinyMaterialManager materialManager;
    private final TinyMeshManager meshManager;
    private final TinyUniforms uniforms;

    public TinyRenderer()
    {
        materialManager = new TinyMaterialManager();
        meshManager = new TinyMeshManager();
        uniforms = new TinyUniforms();
    }

    private IShaderPack activeShaderProgram = null;
    private ShaderProgram activeProgram;
    public ShaderProgram meshProgram;

    public void useProgram(ShaderProgram program)
    {
        activeProgram = program;
        program.use();
    }

    public TinyUniforms getUniforms()
    {
        return uniforms;
    }

    public TinyMaterialManager materialManager()
    {
        return materialManager;
    }

    public TinyMeshManager meshManager()
    {
        return meshManager;
    }

    public IShaderPack activeShaderPack()
    {
        return activeShaderProgram;
    }

    public ShaderProgram activeProgram()
    {
        return activeProgram;
    }

    public void loadRenderer()
    {
        LOGGER.info("Loading renderer!");

        reloadRenderer();
    }

    public void reloadRenderer()
    {
        if (!GL.getCapabilities().OpenGL40)
        {
            Config.GL40_SUPPORTED.set(false);
            LOGGER.warn("OpenGL 4 not supported; using vanilla renderer");
            return;
        }
        Config.GL40_SUPPORTED.set(true);
        activeShaderProgram = ShaderPackManager.INSTANCE.getActivePack();
        meshProgram = activeShaderProgram.getProgram(ShaderProgram.Type.MESH);
        meshProgram.loadUniforms(getUniforms());
    }

    @Override
    public void RenderEvent_onInit(MinecraftClient client)
    {
        ShaderPackManager.INSTANCE.init();
        loadRenderer();
    }
}
