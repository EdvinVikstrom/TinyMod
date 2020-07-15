package net.fabricmc.tiny.screen.option;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.text.Text;

public class OptionMenuBuilder extends Screen {

    private ButtonListWidget buttonListWidget;

    public OptionMenuBuilder(Text title)
    {
        super(title);
    }

    @Override
    protected void init()
    {
        buttonListWidget = new ButtonListWidget(client, width, height, 32, height - 32, 25);

    }
}
