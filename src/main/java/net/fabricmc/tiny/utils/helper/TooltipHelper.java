package net.fabricmc.tiny.utils.helper;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.StringRenderable;

import java.util.List;

public class TooltipHelper {

    public static int getTooltipWidth(TextRenderer renderer, List<StringRenderable> tooltip)
    {
        int width = 0;
        for (StringRenderable stringRenderable : tooltip)
        {
            int textWidth = renderer.getWidth(stringRenderable.getString());
            if (textWidth > width)
                width = textWidth;
        }
        return width + 24;
    }

}
