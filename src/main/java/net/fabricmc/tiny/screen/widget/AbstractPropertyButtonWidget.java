package net.fabricmc.tiny.screen.widget;

import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class AbstractPropertyButtonWidget<T extends AbstractProperty<?>> extends AbstractButtonWidget {

    protected final String translationKey;
    protected final T property;

    public AbstractPropertyButtonWidget(int x, int y, int width, int height, String translationKey, T property)
    {
        super(x, y, width, height, null);
        this.translationKey = translationKey;
        this.property = property;
    }

    public T getProperty()
    {
        return property;
    }

    @Override
    public Text getMessage()
    {
        return new LiteralText(property.asString());
    }
}
