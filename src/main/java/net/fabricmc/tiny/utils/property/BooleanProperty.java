package net.fabricmc.tiny.utils.property;

public class BooleanProperty extends AbstractProperty<Boolean> {

    public BooleanProperty(Category category, Boolean defaultValue, Boolean value)
    {
        super(category, defaultValue, value);
    }

    public BooleanProperty(Category category, Boolean defaultValue)
    {
        super(category, defaultValue);
    }

    public void toggle()
    {
        value = !value;
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
