package net.fabricmc.tiny.utils.property;

public class StringProperty extends AbstractProperty<String> {

    public StringProperty(Category category, String defaultValue, String value)
    {
        super(category, defaultValue, value);
    }

    public StringProperty(Category category, String defaultValue)
    {
        super(category, defaultValue);
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
