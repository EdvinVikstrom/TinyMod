package net.fabricmc.tiny.utils.property;

public class EnumProperty extends AbstractProperty<Integer> {

    private final String[] valid;

    public EnumProperty(Category category, Integer defaultValue, String[] valid, Integer value)
    {
        super(category, defaultValue, value);
        this.valid = valid;
    }

    public EnumProperty(Category category, Integer defaultValue, String[] valid)
    {
        super(category, defaultValue);
        this.valid = valid;
    }

    public void cycle()
    {
        value++;
        if (value >= valid.length)
            value = 0;
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
