package net.fabricmc.tiny.mixin.render;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.render.tiny_renderer_wip.TinyRenderer;
import net.fabricmc.tiny.render.tiny_renderer_wip.world.TinyBuiltChunk;
import net.fabricmc.tiny.render.tiny_renderer_wip.world.TinyChunkBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRenderingMixin {

    @Shadow private ClientWorld world;
    @Shadow @Final private MinecraftClient client;
    @Final @Shadow private VertexFormat vertexFormat;
    private TinyChunkBuilder tinyChunkBuilder;

    private ChunkPos builtPos = null;
    private TinyBuiltChunk builtChunk = null;

    @Inject(at = @At("HEAD"), method = "setupTerrain", cancellable = true)
    private void setupTerrain(Camera camera, Frustum frustum, boolean hasForcedFrustum, int frame, boolean spectator, CallbackInfo info)
    {
        if (Config.TINY_RENDERER.get())
        {
            ChunkPos chunkPos = new ChunkPos(camera.getBlockPos());
            if (!chunkPos.equals(builtPos))
            {
                builtChunk = tinyChunkBuilder.buildChunk(chunkPos);
                builtPos = chunkPos;
            }
            info.cancel();
        }
    }

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gl/VertexBuffer;draw(Lnet/minecraft/util/math/Matrix4f;I)V"),
            method = "renderLayer")
    private void renderLayer(VertexBuffer vertexBuffer, Matrix4f matrix, int mode)
    {
        if (Config.TINY_RENDERER.get())
            TinyRenderer.INSTANCE.meshManager().renderMesh(builtChunk.getMesh(), matrix);
    }

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/BackgroundRenderer;applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V"),
            method = "render")
    private void renderFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog)
    {
        if (!camera.getSubmergedFluidState().isEmpty())
            BackgroundRenderer.applyFog(camera, fogType, viewDistance, thickFog);
        else
        {
            if (Config.RENDER_FOG.get() == 1)
                viewDistance*=Config.RENDER_FOG$FAR_VALUE.get();
            else if (Config.RENDER_FOG.get() == 2)
                viewDistance*=Config.RENDER_FOG$NEAR_VALUE.get();
            if (Config.RENDER_FOG.get() != 3)
                BackgroundRenderer.applyFog(camera, fogType, viewDistance, thickFog);
        }
    }

    @Inject(at = @At("HEAD"), method = "renderSky", cancellable = true)
    private void renderSky(MatrixStack matrices, float tickDelta, CallbackInfo info)
    {
        if (!Config.RENDER_SKY.get())
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderStars()V", cancellable = true)
    private void renderStars(CallbackInfo info)
    {
        if (!Config.RENDER_STARS.get())
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    private void renderWeather(LightmapTextureManager manager, float f, double d, double e, double g, CallbackInfo info)
    {
        if (!Config.RENDER_WEATHER.get())
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "setWorld")
    private void setWorld(ClientWorld clientWorld, CallbackInfo info)
    {
        tinyChunkBuilder = new TinyChunkBuilder(MinecraftClient.getInstance(), clientWorld);
    }

}
