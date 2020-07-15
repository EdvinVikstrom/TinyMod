package net.fabricmc.tiny.fixer;

import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.event.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class TinyFixesManager {

    public static final TinyFixesManager INSTANCE = new TinyFixesManager();

    private static final List<ITinyFixer> FIXERS = new ArrayList<>();
    static {
        FIXERS.add(new ConfigFileFixer());
    }

    public void initFixes()
    {
        for (ITinyFixer fixer : FIXERS)
        {
            TickEvent.Event tickEvent = fixer.tickEvent();
            RenderEvent.Event renderEvent = fixer.renderEvent();
            if (tickEvent != null) TickEvent.INSTANCE.registerEvent(tickEvent);
            if (renderEvent != null) RenderEvent.INSTANCE.registerEvent(renderEvent);
            fixer.init();
        }
    }

}
