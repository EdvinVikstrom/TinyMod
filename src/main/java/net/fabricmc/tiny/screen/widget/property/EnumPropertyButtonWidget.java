package net.fabricmc.tiny.screen.widget.property;

import net.fabricmc.tiny.utils.property.properties.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class EnumPropertyButtonWidget extends AbstractPropertyButtonWidget<EnumProperty> {

    public EnumPropertyButtonWidget(int x, int y, int width, int height, String translationKey, EnumProperty property, Event event)
    {
        super(x, y, width, height, translationKey, property, event);
    }

    @Override
    public Text getMessage()
    {
        return new TranslatableText(translationKey + "." + property.getCurrent());
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        super.onClick(mouseX, mouseY);
        property.cycle();
    }
}
