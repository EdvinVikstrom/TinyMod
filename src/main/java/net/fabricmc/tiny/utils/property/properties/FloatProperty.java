package net.fabricmc.tiny.utils.property.properties;

import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.ICategory;

public class FloatProperty extends AbstractProperty<Double> {

    private final double min, max;

    public FloatProperty(ICategory category, Double defaultValue, double min, double max, Double value, Event event)
    {
        super(category, defaultValue, value, event);
        this.min = min;
        this.max = max;
    }

    public FloatProperty(ICategory category, Double defaultValue, double min, double max, Event event)
    {
        super(category, defaultValue, event);
        this.min = min;
        this.max = max;
    }

    public double getMin()
    {
        return min;
    }

    public double getMax()
    {
        return max;
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
