package net.fabricmc.tiny.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderPackLoader extends Thread {

    private static final Logger logger = LogManager.getLogger("ShaderPack Loader");

    private final AbstractShaderPack shaderPack;

    private float progress = 0.0F;
    private boolean prepared, applied;

    public ShaderPackLoader(AbstractShaderPack shaderPack)
    {
        super("ShaderPack Loader");
        this.shaderPack = shaderPack;
    }

    private void cancel()
    {
        progress = 1.0F;
        prepared = false;
        applied = false;
        stop();
    }

    private void prepare()
    {
        for (int i = 0; i < ShaderProgram.Type.values().length; i++)
        {
            ShaderProgram.Type type = ShaderProgram.Type.values()[i];
            ShaderProgram program = shaderPack.getShaderProgram(type);
            if (program != null)
            {
                logger.debug("linking shader program `" + type.name() + "`");
                if (!program.linkProgram())
                    cancel();
                shaderPack.putShaderProgram(type, program);
            }
            progress = (float) i / (float) ShaderProgram.Type.values().length;
        }
        prepared = true;
    }

    private void apply()
    {
        shaderPack.onLoaded();
        ShaderManager.INSTANCE.useShaderPack(shaderPack);
        applied = true;
    }

    @Override
    public void run()
    {
        prepare();
        apply();
    }

    public float getProgress()
    {
        return progress;
    }

    public boolean isPrepared()
    {
        return prepared;
    }

    public boolean isApplied()
    {
        return applied;
    }
}
