package net.fabricmc.tiny.screen.widget;

import net.fabricmc.tiny.utils.property.properties.BooleanProperty;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BooleanPropertyButtonWidget extends AbstractPropertyButtonWidget<BooleanProperty> {

    /*
        TODO:
            click a button and drag mouse over other buttons to make them enable/disable.
            and maybe scroll down/up if the cursor are out of bounds
     */

    public BooleanPropertyButtonWidget(int x, int y, int width, int height, String translationKey, BooleanProperty property, Event event)
    {
        super(x, y, width, height, translationKey, property, event);
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
        if (event != null)
            event.PropertyButtonWidget_onClicked(this);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY)
    {
        super.onDrag(mouseX, mouseY, deltaX, deltaY);
        if (event != null)
            event.PropertyButtonWidget_onDragged(this);
    }
}
