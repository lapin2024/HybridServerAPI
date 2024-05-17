package com.github.yyeerai.sponge.hybridserverapi;

import com.google.inject.Inject;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.util.logging.Logger;

@Plugin("hybridserverapi")
public class HybridServerAPI {

    public static HybridServerAPI INSTANCE;
    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(final StartedEngineEvent<Server> event) {
        INSTANCE = this;
    }



}