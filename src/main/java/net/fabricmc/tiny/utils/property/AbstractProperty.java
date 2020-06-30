package net.fabricmc.tiny.utils.property;

public abstract class AbstractProperty<T> {

    protected final Category category;
    protected final T defaultValue;
    protected T value;

    public AbstractProperty(Category category, T defaultValue, T value)
    {
        this.category = category;
        this.defaultValue = defaultValue;
        this.value = value;
    }

    public AbstractProperty(Category category, T defaultValue)
    {
        this(category, defaultValue, defaultValue);
    }

    public Category getCategory()
    {
        return category;
    }

    public T getDefault()
    {
        return defaultValue;
    }

    public T get()
    {
        return value;
    }

    public void set(T value)
    {
        this.value = value;
    }

    public abstract String asString();
    public abstract void fromString(String str);

}
