package net.fabricmc.tiny.utils.property.properties;

import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.ICategory;

public class IntProperty extends AbstractProperty<Integer> {

    private final int min, max;

    public IntProperty(ICategory category, Integer defaultValue, int min, int max, Integer value, Event event)
    {
        super(category, defaultValue, value, event);
        this.min = min;
        this.max = max;
    }

    public IntProperty(ICategory category, Integer defaultValue, int min, int max, Event event)
    {
        super(category, defaultValue, event);
        this.min = min;
        this.max = max;
    }

    public int getMin()
    {
        return min;
    }

    public int getMax()
    {
        return max;
    }

    @Override
    public String asString()
    {
        return Integer.toString(value);
    }

    @Override
    public void fromString(String str)
    {
        value = Integer.parseInt(str);
    }
}
