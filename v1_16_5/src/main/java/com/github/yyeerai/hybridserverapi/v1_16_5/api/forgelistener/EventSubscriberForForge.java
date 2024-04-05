package com.github.yyeerai.hybridserverapi.v1_16_5.api.forgelistener;



import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventSubscriberForForge<T extends Event> {
    private final IEventBus bus;
    private final Class<T> eventClass;
    private final EventPriority priority;
    private Predicate<T> filter = event -> true;
    private Consumer<T> handler;

    private ForgeListener<T> tForgeListener;

    public EventSubscriberForForge(IEventBus bus, Class<T> eventClass, EventPriority priority) {
        this.bus = bus;
        this.eventClass = eventClass;
        this.priority = priority;
    }

    public EventSubscriberForForge<T> filter(Predicate<T> filter) {
        this.filter = this.filter.and(filter);
        return this;
    }

    public void handler(Consumer<T> handler) {
        this.handler = handler;
        register();
    }

    private void register() {
        tForgeListener = new ForgeListener<>(bus, eventClass, handler, priority);
        tForgeListener.register();
    }

    //取消注册
    public void unregister() {
        tForgeListener.unregister();
    }


    public void onEvent(T event) {
        if (filter.test(event)) {
            handler.accept(event);
        }
    }
}