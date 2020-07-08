package net.fabricmc.tiny.screen.widget;

import net.fabricmc.tiny.utils.property.properties.FloatProperty;

public class FloatPropertyButtonWidget extends AbstractPropertyButtonWidget<FloatProperty> {

    public FloatPropertyButtonWidget(int x, int y, int width, int height, String translationKey, FloatProperty property)
    {
        super(x, y, width, height, translationKey, property);
    }
}
