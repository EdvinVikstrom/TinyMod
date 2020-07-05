package net.fabricmc.tiny.utils.property.properties;

import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.ICategory;

public class BooleanProperty extends AbstractProperty<Boolean> {

    public BooleanProperty(ICategory category, Boolean defaultValue, Boolean value, Event event)
    {
        super(category, defaultValue, value, event);
    }

    public BooleanProperty(ICategory category, Boolean defaultValue, Event event)
    {
        super(category, defaultValue, event);
    }

    public void toggle()
    {
        set(!value);
    }

    @Override
    public String asString()
    {
        return value ? "true" : "false";
    }

    @Override
    public void fromString(String str)
    {
        value = str.equalsIgnoreCase("true");
    }
}
