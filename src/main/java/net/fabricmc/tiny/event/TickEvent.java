package net.fabricmc.tiny.event;

import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TickEvent implements ClientTickCallback {

    public static final TickEvent INSTANCE = new TickEvent();

    public interface Event {
        void TickEvent_onClientTick(MinecraftClient client);
    }

    private final List<Event> events;

    public TickEvent()
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

    public void register()
    {
        ClientTickCallback.EVENT.register(this);
    }

    @Override
    public void tick(MinecraftClient client)
    {
        for (Event event : events)
            event.TickEvent_onClientTick(client);
    }

}
