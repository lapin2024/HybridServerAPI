package com.github.yyeerai.hybridserverapi.common.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitEvents {

    public static <T extends Event> EventSubscriberForBukkit<T> subscribe(JavaPlugin plugin, Class<T> eventClass) {
        return new EventSubscriberForBukkit<>(plugin, eventClass);
    }

    public static <T extends Event> EventSubscriberForBukkit<T> subscribe(JavaPlugin plugin, Class<T> eventClass, EventPriority priority) {
        return new EventSubscriberForBukkit<>(plugin, eventClass, priority);
    }

    public static <T extends Event> EventSubscriberForBukkit<T> subscribe(JavaPlugin plugin, Class<T> eventClass, EventPriority priority, boolean ignoreCancelled) {
        return new EventSubscriberForBukkit<>(plugin, eventClass, priority, ignoreCancelled);
    }
}