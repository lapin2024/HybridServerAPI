package com.github.yyeerai.hybridserverapi.v1_12_2.api.forgelistener;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

public class ForgeEvents {

    public static <T extends Event> EventSubscriberForForge<T> subscribe(EventBus bus, Class<T> eventClass, EventPriority priority) {
        return new EventSubscriberForForge<>(bus, eventClass, priority);
    }

}