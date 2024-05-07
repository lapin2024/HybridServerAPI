package com.github.yyeerai.hybridserverapi.v1_20_1.api.forgelistener;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

public class ForgeEvents {

    public static <T extends Event> EventSubscriberForForge<T> subscribe(IEventBus bus, Class<T> eventClass, EventPriority priority) {
        return new EventSubscriberForForge<>(bus, eventClass, priority);
    }

}