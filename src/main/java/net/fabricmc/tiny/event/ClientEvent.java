package net.fabricmc.tiny.event;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientEvent {

    public static final ClientEvent INSTANCE = new ClientEvent();

    public interface Event {
        default void ClientEvent_reloadResources(MinecraftClient client) { }
    }

    private final List<Event> events;

    public ClientEvent()
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

    // TODO: important
    public void reloadResources(MinecraftClient client)
    {
        for (Event event : events)
            event.ClientEvent_reloadResources(client);
    }

}
