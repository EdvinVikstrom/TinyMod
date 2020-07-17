package net.fabricmc.tiny.utils.property;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractProperty<T> {

    // TODO: better flag system
    public static final byte FLAG_DEPRECATED = 0;
    public static final byte FLAG_WIP = 1;

    public interface Event {
        void update(AbstractProperty<?> property);
    }

    protected final ICategory category;
    protected final T defaultValue;
    protected T value;
    protected final List<Byte> flags;
    protected final Event event;

    public AbstractProperty(ICategory category, T defaultValue, T value, @Nullable Event event)
    {
        this.category = category;
        this.defaultValue = defaultValue;
        this.value = value;
        this.event = event;
        flags = new ArrayList<>();
    }

    public AbstractProperty(ICategory category, T defaultValue, @Nullable Event event)
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

    public boolean hasFlag(byte b)
    {
        return flags.contains(b);
    }

    public void putFlag(byte b)
    {
        flags.add(b);
    }

    public void set(T value)
    {
        this.value = value;
        if (event != null)
            event.update(this);
    }

    public void reset()
    {
        value = defaultValue;
    }

    public abstract String asString();
    public abstract void fromString(String str);

}
