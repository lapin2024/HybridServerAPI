package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HasPermission extends AbstractRequirementChecker {

    private final String permission;

    public HasPermission(JavaPlugin plugin, String content) {
        super(plugin, content);
        permission = content.substring(14).trim();
    }

    @Override
    public boolean meetsRequirements(Player player, String argument) {
        initializationParameters(argument);
        if (permission == null) {
            return true;
        }
        return permission.startsWith("!") ? !player.hasPermission(permission.replace("!", "").replace("%arg%", argument == null ? "" : argument)) : player.hasPermission(permission.replace("%arg%", argument == null ? "" : argument));
    }

}