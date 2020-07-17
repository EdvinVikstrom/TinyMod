package net.fabricmc.tiny.screen.widget;

import net.minecraft.text.StringRenderable;

import java.util.List;

public interface ITooltipProvider {

    List<StringRenderable> getTooltip(List<StringRenderable> list);

}
