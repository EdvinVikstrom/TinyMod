package net.fabricmc.tiny.screen.config;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.Constants;
import net.fabricmc.tiny.screen.widget.*;
import net.fabricmc.tiny.screen.widget.property.AbstractPropertyButtonWidget;
import net.fabricmc.tiny.screen.widget.property.BooleanPropertyButtonWidget;
import net.fabricmc.tiny.screen.widget.property.EnumPropertyButtonWidget;
import net.fabricmc.tiny.screen.widget.property.FloatPropertyButtonWidget;
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
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.*;

public class ConfigListWidget extends ElementListWidget<ConfigListWidget.AbstractEntry> {

    private final Map<String, AbstractProperty<?>> properties;
    private final List<PropertyEntry> allPropertyEntries = new ArrayList<>();
    private final List<AbstractEntry> visibleEntries = new ArrayList<>();

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
            addPropertyEntry(allPropertyEntries, key, property);
        }
    }

    public void filter(ICategory category, SortType sortType, String string)
    {
        filter(visibleEntries, allPropertyEntries, category, sortType, string);
    }

    public void filter(List<AbstractEntry> abstractEntries, List<PropertyEntry> propertyEntries, ICategory category, SortType sortType, String string)
    {
        abstractEntries.clear();
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
                abstractEntries.add(new CategoryEntry(cat));
            abstractEntries.addAll(entryList);
        }
        reload();
    }

    private void addPropertyEntry(List<PropertyEntry> propertyEntries, String key, AbstractProperty<?> property)
    {
        PropertyEntry propertyEntry = null;
        if (property instanceof BooleanProperty) propertyEntry = new BooleanEntry(key, (BooleanProperty) property);
        else if (property instanceof FloatProperty) propertyEntry = new FloatEntry(key, (FloatProperty) property);
        else if (property instanceof EnumProperty) propertyEntry = new EnumEntry(key, (EnumProperty) property);
        if (propertyEntry != null)
            propertyEntries.add(propertyEntry);
    }

    private void reload()
    {
        clearEntries();
        for (AbstractEntry entry : visibleEntries)
            addEntry(entry);
    }

    public static class CategoryEntry extends AbstractEntry {

        private final ICategory category;

        private final TranslatableText translatableText;

        public CategoryEntry(ICategory category)
        {
            this.category = category;
            translatableText = new TranslatableText("config." + category.name());
        }

        @Override
        public int getHeight()
        {
            return 24;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta)
        {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            String text = Formatting.YELLOW + Formatting.BOLD.toString() + translatableText.getString();
            int textWidth = textRenderer.getWidth(text);
            textRenderer.draw(matrices, text, x + ((entryWidth / 2.0F) - (textWidth / 2.0F)), y + (textRenderer.fontHeight / 2.0F), 0xFFFFFFFF);
        }
    }

    public static class BooleanEntry extends PropertyEntry {

        public BooleanEntry(String key, BooleanProperty property)
        {
            super(key, property, new BooleanPropertyButtonWidget(0, 0, 45, 20, "config." + key, property, null));
            children.add(buttonWidget);
        }
    }

    public static class FloatEntry extends PropertyEntry {

        public FloatEntry(String key, FloatProperty property)
        {
            super(key, property, new FloatPropertyButtonWidget(0, 0, 45, 20, "config." + key, property, null));
            children.add(buttonWidget);
        }
    }

    public static class EnumEntry extends PropertyEntry {

        public EnumEntry(String key, EnumProperty property)
        {
            super(key, property, new EnumPropertyButtonWidget(0, 0, 45, 20, "config." + key, property, null));
            children.add(buttonWidget);
        }
    }

    public static abstract class PropertyEntry extends AbstractEntry {

        protected final String key;
        protected final AbstractProperty<?> property;
        protected final AbstractPropertyButtonWidget<?> buttonWidget;
        protected final AbstractButtonWidget customizeButtonWidget;

        protected final TranslatableText translatableText;

        public PropertyEntry(String key, AbstractProperty<?> property, AbstractPropertyButtonWidget<?> buttonWidget)
        {
            this.buttonWidget = buttonWidget;
            this.key = key;
            this.property = property;
            children.add(buttonWidget);
            if (property.getChildren().size() > 0)
            {
                customizeButtonWidget = new TinyTexturedButtonWidget(0, 0, 8, 20, 0, 0, 64, 64, Constants.WIDGET_TEXTURE, button -> {

                });
                children.add(customizeButtonWidget);
            }else
                customizeButtonWidget = null;
            translatableText = new TranslatableText("config." + key);
        }

        public String getKey()
        {
            return key;
        }

        public AbstractProperty<?> getProperty()
        {
            return property;
        }

        private String getText()
        {
            StringBuilder prefix = new StringBuilder();
            if (property.hasFlag(AbstractProperty.FLAG_DEPRECATED))
                prefix.append(Formatting.STRIKETHROUGH.toString());
            return prefix.toString() + translatableText.getString();
        }

        private void update()
        {
            buttonWidget.active = !property.hasFlag(AbstractProperty.FLAG_DEPRECATED);
        }

        @Override
        public int getHeight()
        {
            return 24;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta)
        {
            update();
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            textRenderer.draw(matrices, getText(), x, y + (textRenderer.fontHeight / 2.0F), 0xFFFFFFFF);
            buttonWidget.x = x + entryWidth - 50;
            buttonWidget.y = y;
            buttonWidget.render(matrices, mouseX, mouseY, tickDelta);
            if (customizeButtonWidget != null)
            {
                customizeButtonWidget.x = x + entryWidth - 4;
                customizeButtonWidget.y = y;
                customizeButtonWidget.render(matrices, mouseX, mouseY, tickDelta);
            }
        }
    }

    public static abstract class AbstractEntry extends ElementListWidget.Entry<AbstractEntry> {

        protected final List<Element> children = new ArrayList<>();

        public abstract int getHeight();

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
