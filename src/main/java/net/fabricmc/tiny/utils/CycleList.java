package net.fabricmc.tiny.utils;

import java.util.ArrayList;
import java.util.List;

public class CycleList<T> {

    private final List<T> values;
    private int index;

    public CycleList(List<T> values)
    {
        this.values = values;
        index = 0;
    }

    public CycleList()
    {
        this(new ArrayList<>());
    }

    public void add(T value)
    {
        values.add(value);
    }

    public void addAll(List<T> values)
    {
        this.values.addAll(values);
    }

    public void remove(T value)
    {
        values.remove(value);
    }

    public void removeAll(List<T> values)
    {
        this.values.removeAll(values);
    }

    @Deprecated
    public void clear()
    {
        values.clear();
        index = 0;
    }

    public T peek()
    {
        if (index < 0 || index >= values.size())
            return null;
        return values.get(index);
    }

    public void cycle(int step)
    {
        index+=step;
        if (index >= values.size())
            index = 0;
        else if (index < 0)
            index = values.size() - 1;
    }

}
