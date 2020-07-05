package net.fabricmc.tiny.mixin.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.tiny.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Inject(at = @At("HEAD"), method = "applyFog", cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo info)
    {
        FluidState fluidState = camera.getSubmergedFluidState();
        if (!Config.getBoolean("renderFog").get() && fluidState.getFluid() == Fluids.EMPTY && fogType != BackgroundRenderer.FogType.FOG_SKY)
            info.cancel();
        if (Config.getBoolean("bedrockFog").get() && camera.getPos().getY() <= 12 && MinecraftClient.getInstance().world != null)
        {
            double d = (float) camera.getPos().getY() + 1.75F;
            d = Math.max(d, 0.7F);
            Random random = MinecraftClient.getInstance().world.random;
            double x = camera.getPos().getX() + (random.nextDouble() * (d * 2)) - d;
            double y = camera.getPos().getY() + (random.nextDouble() * (d * 2)) - d;
            double z = camera.getPos().getZ() + (random.nextDouble() * (d * 2)) - d;
            MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.ASH, x, y, z,
                    random.nextDouble(), random.nextDouble(), random.nextDouble());
            RenderSystem.fogStart(0.0F);
            RenderSystem.fogEnd((float) d);
            RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
            RenderSystem.setupNvFogDistance();
            //info.cancel();
        }
    }

}
