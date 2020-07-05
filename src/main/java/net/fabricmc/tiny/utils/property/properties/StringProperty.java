package net.fabricmc.tiny.utils.property.properties;

import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.ICategory;

public class StringProperty extends AbstractProperty<String> {

    public StringProperty(ICategory category, String defaultValue, String value, Event event)
    {
        super(category, defaultValue, value, event);
    }

    public StringProperty(ICategory category, String defaultValue, Event event)
    {
        super(category, defaultValue, event);
    }

    @Override
    public String asString()
    {
        return "\"" + value + "\"";
    }

    @Override
    public void fromString(String str)
    {
        int start = str.indexOf("\"");
        int end = str.lastIndexOf("\"");
        value = str.substring(start + 1, end);
    }
}
