package net.fabricmc.tiny.event;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityEvent {

    public static final EntityEvent INSTANCE = new EntityEvent();

    public interface Event {
        default void EntityEvent_onEntityTick(Entity entity) { }
        default void EntityEvent_onEntityMove(Entity entity, double lastX, double lastY, double lastZ) { }
    }

    private final List<Event> events;

    public EntityEvent()
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

    /* don't get called when onEntityMove gets called. Why!? PERFORMANCE! */
    public void onEntityTick(Entity entity)
    {
        for (Event event : events)
            event.EntityEvent_onEntityTick(entity);
    }

    public void onEntityMove(Entity entity, double lastX, double lastY, double lastZ)
    {
        for (Event event : events)
        {
            event.EntityEvent_onEntityTick(entity);
            event.EntityEvent_onEntityMove(entity, lastX, lastY, lastZ);
        }
    }
}
