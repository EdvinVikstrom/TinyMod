package net.fabricmc.tiny.screen.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.utils.CycleList;
import net.fabricmc.tiny.utils.property.Categories;
import net.fabricmc.tiny.utils.property.ICategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ConfigMenuScreen extends Screen {

    private final Screen parent;

    private ConfigListWidget configListWidget;
    private CycleList<ICategory> categoryFilter;
    private ConfigListWidget.SortType sortFilter;

    private TextFieldWidget searchWidget;
    private AbstractButtonWidget categoryButton, sortButton;
    private Text getCategoryButtonText()
    {
        TranslatableText categoryText = categoryFilter.peek() != null ? new TranslatableText("config." + categoryFilter.peek().name()) : new TranslatableText("config.all");
        return new TranslatableText("config.category", categoryText);
    }
    private Text getSortButtonText()
    {
        return new TranslatableText("config.sort", sortFilter.getDisplay());
    }

    public ConfigMenuScreen(Screen parent)
    {
        super(new TranslatableText("config.title"));
        this.parent = parent;
    }

    private void exit(boolean save)
    {
        if (save)
            Config.write();
        else
            Config.load();
        client.openScreen(parent);
    }

    @Override
    public void onClose()
    {
        exit(true);
    }

    @Override
    protected void init()
    {
        categoryFilter = new CycleList<>();
        sortFilter = ConfigListWidget.SortType.A_Z;
        categoryFilter.add(null);
        categoryFilter.addAll(Categories.getCategories());

        configListWidget = new ConfigListWidget(client, width, height, 64, height -32, 24, Config.getProperties());
        configListWidget.init();
        configListWidget.filter(categoryFilter.peek(), sortFilter, "");

        searchWidget = new TextFieldWidget(textRenderer, 8, 8, 234, 20, searchWidget, new TranslatableText("selectWorld.search"));
        searchWidget.setChangedListener(string -> configListWidget.filter(categoryFilter.peek(), sortFilter, string));
        categoryButton = new ButtonWidget(8, 32, 150, 20, getCategoryButtonText(), button -> {
            categoryFilter.cycle(1);
            button.setMessage(getCategoryButtonText());
            configListWidget.filter(categoryFilter.peek(), sortFilter, searchWidget.getText());
        });
        sortButton = new ButtonWidget(162, 32, 80, 20, getSortButtonText(), button -> {
            sortFilter = ConfigListWidget.SortType.cycle(sortFilter, 1);
            button.setMessage(getSortButtonText());
            configListWidget.filter(categoryFilter.peek(), sortFilter, searchWidget.getText());
        });

        addChild(configListWidget);
        addChild(searchWidget);
        addButton(categoryButton);
        addButton(sortButton);
        addButton(new ButtonWidget(this.width / 2 + 4, this.height - 29, 150, 20, ScreenTexts.DONE, button -> exit(true)));
        addButton(new ButtonWidget(this.width / 2 - 154, this.height - 29, 150, 20, ScreenTexts.CANCEL, button -> exit(false)));
        addButton(new ButtonWidget(8, this.height - 29, 120, 20, new TranslatableText("config.reset"), button -> Config.resetProperties()));
        setInitialFocus(searchWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderBackground(matrices);
        configListWidget.render(matrices, mouseX, mouseY, delta);
        searchWidget.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, textRenderer, title, width / 2, 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean charTyped(char chr, int keyCode)
    {
        return searchWidget.charTyped(chr, keyCode);
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return true;
    }

    public static class Factory implements ConfigScreenFactory<ConfigMenuScreen> {

        public static final Factory INSTANCE = new Factory();

        @Override
        public ConfigMenuScreen create(Screen screen)
        {
            return new ConfigMenuScreen(screen);
        }
    }

}
