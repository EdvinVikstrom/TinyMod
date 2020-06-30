package net.fabricmc.tiny.utils.property;

public class FloatProperty extends AbstractProperty<Double> {

    private final double min, max;

    public FloatProperty(Category category, Double defaultValue, double min, double max, Double value)
    {
        super(category, defaultValue, value);
        this.min = min;
        this.max = max;
    }

    public FloatProperty(Category category, Double defaultValue, double min, double max)
    {
        super(category, defaultValue);
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
