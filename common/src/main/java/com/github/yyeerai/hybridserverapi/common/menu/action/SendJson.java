package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.util.PluginMethods;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public class SendJson extends AbstractActionExecutor {

    private final String json;

    public SendJson(JavaPlugin plugin, String content) {
        super(plugin, content);
        json = content.substring(9).trim();
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        PluginMethods.sendJsonMessage(player, PlaceholderAPI.setPlaceholders(player, json.replace("%arg%", argument != null ? argument : "").replace("%catchArg%", catchArgument != null ? catchArgument : "")));
    }

}