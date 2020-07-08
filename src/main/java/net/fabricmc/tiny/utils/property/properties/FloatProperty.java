package net.fabricmc.tiny.utils.property.properties;

import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.ICategory;

public class FloatProperty extends AbstractProperty<Double> {

    private final double min, max;
    private final float step;

    public FloatProperty(ICategory category, Double defaultValue, double min, double max, float step, Double value, Event event)
    {
        super(category, defaultValue, value, event);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public FloatProperty(ICategory category, Double defaultValue, double min, double max, float step, Event event)
    {
        super(category, defaultValue, event);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public double getMin()
    {
        return min;
    }

    public double getMax()
    {
        return max;
    }

    public float getStep()
    {
        return step;
    }

    @Override
    public String asString()
    {
        return Double.toString(value);
    }

    @Override
    public void fromString(String str)
    {
        value = Double.parseDouble(str);
    }
}
