package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ConsoleCommands extends AbstractActionExecutor {


    private final List<String> commands;

    public ConsoleCommands(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(16);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        commands = extractArray(trim);
    }

    @Override
    public void execute(Player player, String argument, String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (commands == null || commands.isEmpty()) {
            return;
        }
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> executeCommands(player), delay);
        } else {
            executeCommands(player);
        }
    }

    private void executeCommands(Player player) {
        for (String command : commands) {
            command = HexUtils.colorify(command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, HexUtils.colorify(command.replace("%arg%", this.argument == null ? "" : this.argument).replace("%catchArg%", this.catchArgument == null ? "" : this.catchArgument))));
        }
    }


}