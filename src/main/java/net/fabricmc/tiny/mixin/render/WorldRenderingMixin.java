package net.fabricmc.tiny.mixin.render;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.render.world.TinyWorldRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRenderingMixin {

    private TinyWorldRenderer tinyWorldRenderer;


    @Final @Shadow private VertexFormat vertexFormat;
    @Shadow private ClientWorld world;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(MinecraftClient client, BufferBuilderStorage bufferBuilders, CallbackInfo info)
    {
        tinyWorldRenderer = new TinyWorldRenderer(client, vertexFormat, bufferBuilders, world);
    }

    @Inject(at = @At("HEAD"), method = "setupTerrain", cancellable = true)
    private void setupTerrain(Camera camera, Frustum frustum, boolean hasForcedFrustum, int frame, boolean spectator, CallbackInfo info)
    {
        if (Config.TINY_RENDERER.get())
        {
            tinyWorldRenderer.setupTerrain(camera, frustum, hasForcedFrustum, frame, spectator);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "renderLayer", cancellable = true)
    private void renderLayer(RenderLayer renderLayer, MatrixStack matrixStack, double d, double e, double f, CallbackInfo info)
    {
        if (Config.TINY_RENDERER.get())
        {
            tinyWorldRenderer.renderLayer(renderLayer, matrixStack, d, e, f);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "setWorld")
    private void setWorld(ClientWorld world, CallbackInfo info)
    {
        tinyWorldRenderer.setWorld(world);
    }

}
