package net.fabricmc.tiny.utils.property;

public class IntProperty extends AbstractProperty<Integer> {

    private final int min, max;

    public IntProperty(Category category, Integer defaultValue, int min, int max, Integer value)
    {
        super(category, defaultValue, value);
        this.min = min;
        this.max = max;
    }

    public IntProperty(Category category, Integer defaultValue, int min, int max)
    {
        super(category, defaultValue);
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
