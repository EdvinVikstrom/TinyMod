package net.fabricmc.tiny.render;

import net.minecraft.client.util.math.MatrixStack;

import static org.lwjgl.opengl.GL11.*;

public class GraphRenderer {

    private static final int RESOLUTION = 16;

    private double memUsage;
    private long lastTime = 0;

    public GraphRenderer()
    {
        memUsage = 0;
    }

    private double getMemUsage()
    {
        long max = Runtime.getRuntime().maxMemory();
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        long l = total - free;
        return (double) l / (double) max;
    }

    private void update()
    {
        memUsage = getMemUsage();
    }

    public void render(MatrixStack matrixStack, int x, int y, int scale)
    {
        if ((System.currentTimeMillis() / 100L) > (lastTime / 100L))
        {
            update();
            lastTime = System.currentTimeMillis();
        }
        renderGraph(memUsage, matrixStack, x, y, (scale / 1.5D), 0.2F, 0.3F, 0.8F);
    }

    private static void renderGraph(double p, MatrixStack matrixStack, double x, double y, double scale, float red, float green, float blue)
    {
        double pi2 = Math.PI * 2;
        double pi05 = Math.PI / 2.0D;
        glColor4f(red, green, blue, 1.0F);
        glBegin(GL_TRIANGLE_FAN);
        glVertex2d(x, y);
        for (double i = 0; i < pi2 * p; i+=(pi2 / (double) RESOLUTION))
        {
            double s = x + Math.sin(i) * scale;
            double c = y + Math.cos(i) * scale;
            glVertex2d(s, c);
        }
        glEnd();
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
