package net.fabricmc.tiny.event;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.tiny.TinyMod;
import net.fabricmc.tiny.mixin.imixin.IMinecraftClientMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenderEvent implements HudRenderCallback {

    public static final RenderEvent INSTANCE = new RenderEvent();

    public static class WorldRendererContext {
        private final MatrixStack matrices;
        private final float tickDelta;
        private final long limitTime;
        private final boolean renderBlockOutline;
        private final Camera camera;
        private final GameRenderer gameRenderer;
        private final LightmapTextureManager lightmapTextureManager;
        private final Matrix4f matrix4f;
        private final BufferBuilderStorage bufferBuilders;
        private final ClientWorld world;

        public WorldRendererContext(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, BufferBuilderStorage bufferBuilders, ClientWorld world)
        {
            this.matrices = matrices;
            this.tickDelta = tickDelta;
            this.limitTime = limitTime;
            this.renderBlockOutline = renderBlockOutline;
            this.camera = camera;
            this.gameRenderer = gameRenderer;
            this.lightmapTextureManager = lightmapTextureManager;
            this.matrix4f = matrix4f;
            this.bufferBuilders = bufferBuilders;
            this.world = world;
        }

        public MatrixStack getMatrices()
        {
            return matrices;
        }

        public float getTickDelta()
        {
            return tickDelta;
        }

        public long getLimitTime()
        {
            return limitTime;
        }

        public boolean isRenderBlockOutline()
        {
            return renderBlockOutline;
        }

        public Camera getCamera()
        {
            return camera;
        }

        public GameRenderer getGameRenderer()
        {
            return gameRenderer;
        }

        public LightmapTextureManager getLightmapTextureManager()
        {
            return lightmapTextureManager;
        }

        public Matrix4f getMatrix4f()
        {
            return matrix4f;
        }

        public BufferBuilderStorage getBufferBuilders()
        {
            return bufferBuilders;
        }

        public ClientWorld getWorld()
        {
            return world;
        }
    }

    public interface Event {
        default void RenderEvent_onInit(MinecraftClient client) { };
        default void RenderEvent_onRender(MinecraftClient client) { };
        default void RenderEvent_onWorldRender(WorldRendererContext context) { }
        default void RenderEvent_onHudRender(MatrixStack matrixStack, float v) { }
    }

    private final List<Event> events;
    private boolean init = false;

    public RenderEvent()
    {
        events = new ArrayList<>();
    }

    public void register()
    {
        HudRenderCallback.EVENT.register(this);
    }

    public void registerEvent(Event... events)
    {
        this.events.addAll(Arrays.asList(events));
        if (init)
            TinyMod.LOGGER.warn("registered RenderEvent after init");
    }

    public void unregisterEvent(Event... events)
    {
        this.events.removeAll(Arrays.asList(events));
    }

    public void onRender(MinecraftClient client)
    {
        if (!init)
        {
            for (Event event : events)
                event.RenderEvent_onInit(client);
            init = true;
        }
        for (Event event : events)
            event.RenderEvent_onRender(client);
    }

    public void onWorldRender(WorldRendererContext context)
    {
        for (Event event : events)
            event.RenderEvent_onWorldRender(context);
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, float v)
    {
        for (Event event : events)
            event.RenderEvent_onHudRender(matrixStack, v);
    }

    public static int getCurrentFPS()
    {
        return ((IMinecraftClientMixin) MinecraftClient.getInstance()).getCurrentFps();
    }
}
