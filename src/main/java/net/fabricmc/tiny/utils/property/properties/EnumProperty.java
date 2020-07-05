package net.fabricmc.tiny.utils.property.properties;

import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.ICategory;

public class EnumProperty extends AbstractProperty<Integer> {

    private final String[] valid;

    public EnumProperty(ICategory category, Integer defaultValue, String[] valid, Integer value, Event event)
    {
        super(category, defaultValue, value, event);
        this.valid = valid;
    }

    public EnumProperty(ICategory category, Integer defaultValue, String[] valid, Event event)
    {
        super(category, defaultValue, event);
        this.valid = valid;
    }

    public void cycle()
    {
        value++;
        if (value >= valid.length)
            value = 0;
        if (event != null)
            event.update(this);
    }

    public String[] getValid()
    {
        return valid;
    }

    public String getCurrent()
    {
        return valid[value];
    }

    @Override
    public String asString()
    {
        return valid[value];
    }

    @Override
    public void fromString(String str)
    {
        for (int i = 0; i < valid.length; i++)
        {
            if (valid[i].equals(str))
                value = i;
        }
    }
}
