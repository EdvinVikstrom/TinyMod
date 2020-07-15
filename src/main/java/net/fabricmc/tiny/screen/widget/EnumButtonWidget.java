package net.fabricmc.tiny.screen.widget;

import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class EnumButtonWidget extends AbstractButtonWidget {

    private final Enum<?>[] enums;
    private int index;

    public EnumButtonWidget(int x, int y, int width, int height, Enum<?>[] enums, int index)
    {
        super(x, y, width, height, LiteralText.EMPTY);
        this.enums = enums;
        this.index = index;
    }

    public void cycle()
    {
        index++;
        if (index > enums.length)
            index = 0;
    }

    public Enum<?> getValue()
    {
        return enums[index];
    }

    @Override
    public Text getMessage()
    {
        return new LiteralText(getValue().name());
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        cycle();
    }
}
