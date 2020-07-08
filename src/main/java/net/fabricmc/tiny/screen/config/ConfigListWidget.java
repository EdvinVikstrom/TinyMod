package net.fabricmc.tiny.screen.config;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.screen.widget.AbstractPropertyButtonWidget;
import net.fabricmc.tiny.screen.widget.BooleanPropertyButtonWidget;
import net.fabricmc.tiny.screen.widget.EnumPropertyButtonWidget;
import net.fabricmc.tiny.screen.widget.FloatPropertyButtonWidget;
import net.fabricmc.tiny.utils.StrUtils;
import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.Categories;
import net.fabricmc.tiny.utils.property.ICategory;
import net.fabricmc.tiny.utils.property.properties.BooleanProperty;
import net.fabricmc.tiny.utils.property.properties.EnumProperty;
import net.fabricmc.tiny.utils.property.properties.FloatProperty;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigListWidget extends ElementListWidget<ConfigListWidget.AbstractEntry> {

    private final Map<String, AbstractProperty<?>> properties;
    private final List<PropertyEntry> propertyEntries = new ArrayList<>();

    public ConfigListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, int m, Map<String, AbstractProperty<?>> properties)
    {
        super(minecraftClient, i, j, k, l, m);
        this.properties = properties;
    }

    public void init()
    {
        for (String key : properties.keySet())
        {
            AbstractProperty<?> property = properties.get(key);

            if (property instanceof BooleanProperty) propertyEntries.add(new BooleanEntry(key, (BooleanProperty) property));
            else if (property instanceof FloatProperty) propertyEntries.add(new FloatEntry(key, (FloatProperty) property));
            else if (property instanceof EnumProperty) propertyEntries.add(new EnumEntry(key, (EnumProperty) property));
        }
    }

    public void filter(ICategory category, SortType sortType, String string)
    {
        clearEntries();
        Map<ICategory, List<PropertyEntry>> entryMap = new LinkedHashMap<>();
        for (ICategory cat : Categories.getCategories())
        {
            if (!Config.getProperties(cat).isEmpty() && (category == null || category == cat))
                entryMap.put(cat, new ArrayList<>());
        }
        for (PropertyEntry entry : propertyEntries)
        {
            List<PropertyEntry> entryList = entryMap.get(entry.getProperty().getCategory());
            if (entryList == null)
                continue;
            if (entry.getKey().toLowerCase().contains(string.toLowerCase()))
                entryList.add(entry);
        }
        for (ICategory cat : entryMap.keySet())
        {
            List<PropertyEntry> entryList = entryMap.get(cat);
            entryList.sort((t1, t2) -> SortType.sort(sortType, t1.key, t2.key));
            boolean addCategory = false;
            for (PropertyEntry entry : entryList)
            {
                if (entry.getProperty().getCategory() == cat)
                {
                    addCategory = true;
                    break;
                }
            }
            if (addCategory)
                addEntry(new CategoryEntry(cat));
            for (PropertyEntry entry : entryList)
                addEntry(entry);
        }
    }

    public static class CategoryEntry extends AbstractEntry {

        private final ICategory category;

        public CategoryEntry(ICategory category)
        {
            this.category = category;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta)
        {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            textRenderer.draw(matrices, Formatting.BOLD + Formatting.YELLOW.toString() + (new TranslatableText("config." + category.name()).getString()), x, y + (textRenderer.fontHeight / 2.0F), 0xFFFFFFFF);
        }
    }

    public static class BooleanEntry extends PropertyEntry {

        public BooleanEntry(String key, BooleanProperty property)
        {
            super(key, property, new BooleanPropertyButtonWidget(0, 0, 45, 20, "config." + key, property));
            children.add(buttonWidget);
        }
    }

    public static class FloatEntry extends PropertyEntry {

        public FloatEntry(String key, FloatProperty property)
        {
            super(key, property, new FloatPropertyButtonWidget(0, 0, 45, 20, "config." + key, property));
            children.add(buttonWidget);
        }
    }

    public static class EnumEntry extends PropertyEntry {

        public EnumEntry(String key, EnumProperty property)
        {
            super(key, property, new EnumPropertyButtonWidget(0, 0, 45, 20, "config." + key, property));
            children.add(buttonWidget);
        }
    }

    public static abstract class PropertyEntry extends AbstractEntry {

        protected final String key;
        protected final AbstractProperty<?> property;
        protected final AbstractPropertyButtonWidget<?> buttonWidget;

        public PropertyEntry(String key, AbstractProperty<?> property, AbstractPropertyButtonWidget<?> buttonWidget)
        {
            this.buttonWidget = buttonWidget;
            this.key = key;
            this.property = property;
            children.add(buttonWidget);
        }

        public String getKey()
        {
            return key;
        }

        public AbstractProperty<?> getProperty()
        {
            return property;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta)
        {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            textRenderer.draw(matrices, new TranslatableText(key), x + 8, y + (textRenderer.fontHeight / 2.0F), 0xFFFFFFFF);
            buttonWidget.x = x + entryWidth - 50;
            buttonWidget.y = y;
            buttonWidget.render(matrices, mouseX, mouseY, tickDelta);
        }
    }

    public static abstract class AbstractEntry extends ElementListWidget.Entry<AbstractEntry> {

        protected final List<Element> children = new ArrayList<>();

        @Override
        public List<? extends Element> children()
        {
            return children;
        }
    }

    public enum SortType {

        A_Z("A-Z"), Z_A("Z-A");

        private final String display;

        SortType(String display)
        {
            this.display = display;
        }

        public String getDisplay()
        {
            return display;
        }

        public static int sort(SortType type, String str1, String str2)
        {
            return StrUtils.sortStringAlphabetically(str1, str2, type == A_Z, false);
        }

        public static SortType cycle(SortType type, int step)
        {
            int index = 0;
            for (int i = 0; i < values().length; i++)
            {
                SortType value = values()[i];
                if (value == type)
                    index = i + step;
            }
            if (index >= values().length)
                index = 0;
            else if (index < 0)
                index = values().length - 1;
            return values()[index];
        }
    }

}
