package net.fabricmc.tiny.event;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExecuteEvent implements Runnable {

    public static final ExecuteEvent INSTANCE = new ExecuteEvent();

    private final Queue<Runnable> events;

    public ExecuteEvent()
    {
        events = new ConcurrentLinkedQueue<>();
    }

    public void execute(Runnable event)
    {
        events.add(event);
    }

    @Override
    public void run()
    {
        Runnable event;
        while ((event = events.poll()) != null)
            event.run();
    }
}
