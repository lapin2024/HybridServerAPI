package com.github.yyeerai.hybridserverapi.common.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventSubscriberForBukkit<T extends Event> {
    private final JavaPlugin plugin;
    private final Class<T> eventClass;
    private EventPriority priority = EventPriority.NORMAL;
    private boolean ignoreCancelled = false;
    private Predicate<T> filter = event -> true;
    private Consumer<T> handler;

    public EventSubscriberForBukkit(JavaPlugin plugin, Class<T> eventClass) {
        this.plugin = plugin;
        this.eventClass = eventClass;
    }

    public EventSubscriberForBukkit(JavaPlugin plugin, Class<T> eventClass, EventPriority priority) {
        this.plugin = plugin;
        this.eventClass = eventClass;
        this.priority = priority;
    }

    public EventSubscriberForBukkit(JavaPlugin plugin, Class<T> eventClass, EventPriority priority, boolean ignoreCancelled) {
        this.plugin = plugin;
        this.eventClass = eventClass;
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
    }

    public EventSubscriberForBukkit<T> filter(Predicate<T> filter) {
        this.filter = this.filter.and(filter);
        return this;
    }

    public void handler(Consumer<T> handler) {
        this.handler = handler;
        register();
    }

    private void register() {
        Bukkit.getPluginManager().registerEvent(eventClass, new Listener() {
        }, priority, createExecutor(), plugin, ignoreCancelled);
    }

    private EventExecutor createExecutor() {
        return (listener, event) -> {
            if (eventClass.isInstance(event) && filter.test(eventClass.cast(event))) {
                handler.accept(eventClass.cast(event));
            }
        };
    }
}