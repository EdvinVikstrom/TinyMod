package net.fabricmc.tiny.screen.widget;

import net.fabricmc.tiny.utils.property.properties.BooleanProperty;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BooleanPropertyButtonWidget extends AbstractPropertyButtonWidget<BooleanProperty> {

    public BooleanPropertyButtonWidget(int x, int y, int width, int height, String translationKey, BooleanProperty property)
    {
        super(x, y, width, height, translationKey, property);
    }

    @Override
    public Text getMessage()
    {
        return new LiteralText((property.get() ? Formatting.GREEN + ScreenTexts.ON.getString() : Formatting.RED + ScreenTexts.OFF.getString()));
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        super.onClick(mouseX, mouseY);
        property.toggle();
    }
}
