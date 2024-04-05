package com.github.yyeerai.hybridserverapi.common.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class CommandRegister {

    private final Plugin plugin;

    public CommandRegister(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Unregister a command
     *
     * @param name The name of the command to unregister
     */
    public static void unregister(String name) {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
            Command command = commandMap.getCommand(name);
            if (command != null) {
                command.unregister(commandMap);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(CommandBuilder builder) {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
            Command command = new Command(builder.getName(), builder.getDescription(), builder.getUsage(), builder.getAliases()) {
                @Override
                public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
                    if (builder.getExecutor() == null) {
                        return true;
                    }
                    return builder.getExecutor().onCommand(commandSender, this, s, strings);
                }

                @Override
                public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException {
                    if (builder.getCompleter() == null) {
                        return Collections.emptyList();
                    }
                    List<String> strings = builder.getCompleter().onTabComplete(sender, this, alias, args) != null ? builder.getCompleter().onTabComplete(sender, this, alias, args) : Collections.emptyList();
                    if (strings != null) {
                        return strings;
                    } else {
                        return Collections.emptyList();
                    }
                }
            };

            command.setPermission(builder.getPermission());
            command.setPermissionMessage(builder.getPermissionMessage());

            commandMap.register(plugin.getName(), command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}