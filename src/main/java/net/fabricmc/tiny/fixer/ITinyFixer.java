package net.fabricmc.tiny.fixer;

import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.event.TickEvent;

public interface ITinyFixer {

    /*
       i don't know why i need this but \_O_/
     */
    void init();
    TickEvent.Event tickEvent();
    RenderEvent.Event renderEvent();

}
