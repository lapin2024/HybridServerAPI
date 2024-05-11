package com.github.yyeerai.sponge.hybridserverapi;

import com.google.inject.Inject;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.io.File;
import java.util.logging.Logger;

@Plugin("hybridserverapi")
public class HybridServerAPI {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(final StartedEngineEvent<Server> event) {
        logger.info("Successfully running ExamplePlugin!!!");
        ConfigurationLoader<CommentedConfigurationNode> configurationLoader = HoconConfigurationLoader.builder().file(new File("config.cfg")).build();
        try {
            String string = configurationLoader.load().node("main").getString();
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

}