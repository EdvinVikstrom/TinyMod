package net.fabricmc.tiny.screen.widget.property;

import net.fabricmc.tiny.utils.property.properties.FloatProperty;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class FloatPropertyButtonWidget extends AbstractPropertyButtonWidget<FloatProperty> {

    private double oldValue;

    public FloatPropertyButtonWidget(int x, int y, int width, int height, String translationKey, FloatProperty property, Event event)
    {
        super(x, y, width, height, translationKey, property, event);
    }

    private void setValueFromMouse(double offsetX)
    {
        offsetX*=property.getStep();
        property.set(oldValue + offsetX);
        if (property.get() > property.getMax())
            property.set(property.getMax());
        else if (property.get() < property.getMin())
            property.set(property.getMin());
    }

    @Override
    public Text getMessage()
    {
        if (property.getStep() >= 1.0F)
            return new LiteralText(Integer.toString(property.get().intValue()));
        else if (property.getStep() >= 0.1F)
            return new LiteralText(String.format("%.1f", property.get()));
        else if (property.getStep() >= 0.01F)
            return new LiteralText(String.format("%.2f", property.get()));
        return new LiteralText(String.format("%.3f", property.get()));
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        super.renderButton(matrices, mouseX, mouseY, delta);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY)
    {
        oldValue = property.get();
        setValueFromMouse(deltaX);
        super.onDrag(mouseX, mouseY, deltaX, deltaY);
    }
}
