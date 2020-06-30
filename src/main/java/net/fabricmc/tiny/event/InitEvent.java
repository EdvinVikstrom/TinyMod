package net.fabricmc.tiny.event;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitEvent {

    public static final InitEvent INSTANCE = new InitEvent();

    public interface Event {
        void InitEvent_onInit(MinecraftClient client);
    }

    private final List<Event> events;

    public InitEvent()
    {
        events = new ArrayList<>();
    }

    public void registerEvent(Event... events)
    {
        this.events.addAll(Arrays.asList(events));
    }

    public void unregisterEvent(Event... events)
    {
        this.events.removeAll(Arrays.asList(events));
    }

    public void onInit(MinecraftClient client)
    {
        for (Event event : events)
            event.InitEvent_onInit(client);
    }

}
