package net.fabricmc.tiny.screen.util;

import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.text.Text;

public class OptionButtonWidget extends Option {

    private final Text text;
    private final ButtonWidget.TooltipSupplier tooltipSupplier;
    private final ButtonWidget.PressAction pressAction;

    public OptionButtonWidget(String key, Text text, ButtonWidget.TooltipSupplier tooltipSupplier, ButtonWidget.PressAction pressAction)
    {
        super(key);
        this.text = text;
        this.tooltipSupplier = tooltipSupplier;
        this.pressAction = pressAction;
    }

    @Override
    public AbstractButtonWidget createButton(GameOptions options, int x, int y, int width)
    {
        if (tooltipSupplier != null)
            return new ButtonWidget(x, y, width, 20, text, pressAction, tooltipSupplier);
        else
            return new ButtonWidget(x, y, width, 20, text, pressAction);
    }
}
