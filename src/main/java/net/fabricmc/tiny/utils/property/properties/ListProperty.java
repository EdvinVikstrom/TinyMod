package net.fabricmc.tiny.utils.property.properties;

import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.ICategory;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListProperty extends AbstractProperty<Map<String, AbstractProperty<?>>> {

    public ListProperty(ICategory category, Map<String, AbstractProperty<?>> value, @Nullable Event event)
    {
        super(category, null, value, event);
    }

    public ListProperty(ICategory category, Event event)
    {
        super(category, new LinkedHashMap<>(), event);
    }

    public Map<String, AbstractProperty<?>> asMap()
    {
        return value;
    }

    public void pushProperty(String id, AbstractProperty<?> property)
    {
        value.put(id, property);
    }

    public AbstractProperty<?> pullProperty(String id)
    {
        return value.get(id);
    }

    @Override
    public String asString()
    {
        return null;
    }

    @Override
    public void fromString(String str)
    {

    }
}
