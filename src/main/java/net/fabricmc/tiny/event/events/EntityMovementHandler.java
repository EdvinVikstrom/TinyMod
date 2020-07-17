package net.fabricmc.tiny.event.events;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.EntityEvent;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityMovementHandler implements EntityEvent.Event {

    public static final EntityMovementHandler INSTANCE = new EntityMovementHandler();

    private final Map<Entity, Double> entityVelocities;

    public EntityMovementHandler()
    {
        entityVelocities = new ConcurrentHashMap<>();
    }

    @Override
    public void EntityEvent_onEntityMove(Entity entity, double lastX, double lastY, double lastZ)
    {
        boolean shouldCheck = Config.SHOW_VELOCITY.get();
        if (shouldCheck)
        {
            double offX = Math.abs(entity.getVelocity().x);
            double offZ = Math.abs(entity.getVelocity().z);
            double velocity = (offX + offZ) * 20.0D;
            entityVelocities.put(entity, velocity);
        }
    }

    public double getEntityVelocity(Entity entity)
    {
        Double velocity = entityVelocities.get(entity);
        return velocity != null ? velocity : 0.0D;
    }
}
