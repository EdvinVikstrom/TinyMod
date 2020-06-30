package net.fabricmc.tiny.screen.shader;

import net.fabricmc.tiny.shaders.AbstractShaderPack;
import net.fabricmc.tiny.shaders.ShaderPackLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashScreen;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.util.Unit;

import java.util.concurrent.CompletableFuture;

public class ShaderSplashScreen extends SplashScreen {

    public ShaderSplashScreen(MinecraftClient client, AbstractShaderPack shaderPack)
    {
        super(client, new Monitor(new ShaderPackLoader(shaderPack)), throwable -> {}, true);
    }

    private static class Monitor implements ResourceReloadMonitor {

        private final ShaderPackLoader shaderPackLoader;

        public Monitor(ShaderPackLoader shaderPackLoader)
        {
            this.shaderPackLoader = shaderPackLoader;
            shaderPackLoader.start();
        }

        @Override
        public CompletableFuture<Unit> whenComplete()
        {
            return new CompletableFuture<>();
        }

        @Override
        public float getProgress()
        {
            return shaderPackLoader.getProgress();
        }

        @Override
        public boolean isPrepareStageComplete()
        {
            return shaderPackLoader.isPrepared();
        }

        @Override
        public boolean isApplyStageComplete()
        {
            return shaderPackLoader.isApplied();
        }

        @Override
        public void throwExceptions()
        {

        }
    }
}
