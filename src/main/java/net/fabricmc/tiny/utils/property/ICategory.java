package net.fabricmc.tiny.utils.property;

public interface ICategory {

    String getName();
    int getPriority();

    static ICategory create(String name, int priority)
    {
        return new ICategory() {
            @Override
            public String getName()
            {
                return name;
            }

            @Override
            public int getPriority()
            {
                return priority;
            }
        };
    }

    default boolean equals(ICategory category)
    {
        return getName().equals(category.getName());
    }

}
