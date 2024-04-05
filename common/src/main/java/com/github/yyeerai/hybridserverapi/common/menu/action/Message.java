package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Message extends AbstractActionExecutor {
    private final String message;

    public Message(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(8);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        String[] split = trim.split(",");
        message = split[0].trim();
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (message == null || message.isEmpty()) {
            return;
        }
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> sendMessage(player), delay);
        } else {
            sendMessage(player);
        }
    }

    private void sendMessage(Player player) {
        String processedMessage = HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, message.replace("%arg%", this.argument != null ? this.argument : "").replace("%catchArg%", this.catchArgument != null ? this.catchArgument : "")));
        player.sendMessage(processedMessage);
    }

}