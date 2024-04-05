package com.github.yyeerai.hybridserverapi.common.command;

import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public class CommandBuilder {

    private final JavaPlugin plugin;

    private final String name;
    private final String description;
    private final String usage;
    private final List<String> aliases;

    private String permission;
    private String permissionMessage;
    private CommandExecutor executor;
    private TabCompleter completer;


    public CommandBuilder(JavaPlugin plugin, String name, String description, String usage, List<String> aliases) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    public CommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }

    public CommandBuilder setExecutor(CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    public CommandBuilder setCompleter(TabCompleter completer) {
        this.completer = completer;
        return this;
    }


    public void build() {
        CommandRegister commandRegister = new CommandRegister(plugin);
        commandRegister.register(this);
    }

}