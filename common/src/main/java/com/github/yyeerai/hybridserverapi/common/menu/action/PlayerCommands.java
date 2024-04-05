package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayerCommands extends AbstractActionExecutor {
    private final List<String> commands;


    public PlayerCommands(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(15);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        commands = extractArray(trim);
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (commands == null || commands.isEmpty()) {
            return;
        }
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }

        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> executeCommands(player), delay);
        } else {
            executeCommands(player);
        }

    }

    private void executeCommands(Player player) {
        for (String command : commands) {
            command = HexUtils.colorify(command);
            Bukkit.dispatchCommand(player, PlaceholderAPI.setPlaceholders(player, command.replace("%arg%", this.argument == null ? "" : this.argument).replace("%catchArg%", this.catchArgument == null ? "" : this.catchArgument)));
        }
    }

}