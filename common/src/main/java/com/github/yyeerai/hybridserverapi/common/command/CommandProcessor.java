package com.github.yyeerai.hybridserverapi.common.command;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * CommandProcessor
 * <p>
 * This class is used to register and unregister commands.
 */
public class CommandProcessor {

    /**
     * Register a command
     *
     * @return CommandBuilder
     */
    public static CommandBuilder registerCommand(JavaPlugin plugin, String name, String description, String usage, List<String> aliases) {
        return new CommandBuilder(plugin, name, description, usage, aliases);
    }

    /**
     * Unregister a command
     *
     * @param name The name of the command to unregister
     */
    public static void unregisterCommand(String name) {
        CommandRegister.unregister(name);
    }
}