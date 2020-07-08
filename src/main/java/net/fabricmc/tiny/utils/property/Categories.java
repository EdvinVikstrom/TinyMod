package net.fabricmc.tiny.utils.property;

import java.util.ArrayList;
import java.util.List;

public final class Categories {

    private static final List<ICategory> CATEGORIES = new ArrayList<>();

    public static final ICategory GRAPHICS;
    public static final ICategory PERFORMANCE;
    public static final ICategory DETAILS;
    public static final ICategory OTHER;

    public static List<ICategory> getCategories()
    {
        return CATEGORIES;
    }

    public static ICategory cycle(ICategory category, int step)
    {
        int index = category.index() + step;
        if (index >= CATEGORIES.size())
            index = 0;
        else if (index < 0)
            index = CATEGORIES.size() - 1;
        return CATEGORIES.get(index);
    }

    static {
        GRAPHICS = ICategory.create("graphics", 0, true);
        PERFORMANCE = ICategory.create("performance", 1, true);
        DETAILS = ICategory.create("details", 2, true);
        OTHER = ICategory.create("other", 3, true);

        CATEGORIES.add(GRAPHICS);
        CATEGORIES.add(PERFORMANCE);
        CATEGORIES.add(DETAILS);
        CATEGORIES.add(OTHER);
    }

}
