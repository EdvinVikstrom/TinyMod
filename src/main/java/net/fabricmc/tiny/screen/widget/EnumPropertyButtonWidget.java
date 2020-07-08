package net.fabricmc.tiny.screen.widget;

import net.fabricmc.tiny.utils.property.properties.EnumProperty;

public class EnumPropertyButtonWidget extends AbstractPropertyButtonWidget<EnumProperty> {

    public EnumPropertyButtonWidget(int x, int y, int width, int height, String translationKey, EnumProperty property)
    {
        super(x, y, width, height, translationKey, property);
    }
}
