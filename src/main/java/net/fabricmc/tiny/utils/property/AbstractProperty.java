package net.fabricmc.tiny.utils.property;

import javax.annotation.Nullable;

public abstract class AbstractProperty<T> {

    public interface Event {
        void update(AbstractProperty<?> property);
    }

    protected final ICategory category;
    protected final T defaultValue;
    protected T value;
    protected final Event event;

    public AbstractProperty(ICategory category, T defaultValue, T value, @Nullable Event event)
    {
        this.category = category;
        this.defaultValue = defaultValue;
        this.value = value;
        this.event = event;
    }

    public AbstractProperty(ICategory category, T defaultValue, Event event)
    {
        this(category, defaultValue, defaultValue, event);
    }

    public ICategory getCategory()
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
        if (event != null)
            event.update(this);
    }

    public abstract String asString();
    public abstract void fromString(String str);

}
