package net.fabricmc.tiny.utils.property;

public interface ICategory {

    String name();
    int index();
    boolean visible();

    static ICategory create(String name, int index, boolean visible)
    {
        return new ICategory() {
            @Override
            public String name()
            {
                return name;
            }

            @Override
            public int index()
            {
                return index;
            }

            @Override
            public boolean visible()
            {
                return visible;
            }
        };
    }

    default boolean equals(ICategory category)
    {
        return name().equals(category.name());
    }

}
