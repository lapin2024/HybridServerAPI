package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Broadcast extends AbstractActionExecutor {

    private final String message;

    public Broadcast(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(10).trim();
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        int delay = getDelay(angleBracketsContent);
        double chance = getChance(angleBracketsContent);
        String[] split = trim.split(",");
        this.message = split[0].trim();
        this.delay = delay;
        this.chance = chance;
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (message.isEmpty()) {
            return;
        }
        String newMessage = HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, message.replace("%arg%", this.argument != null ? this.argument : "").replace("%catchArg%", this.catchArgument != null ? this.catchArgument : "")));
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            player.getServer().getScheduler().runTaskLater(plugin, () -> player.getServer().broadcastMessage(newMessage), delay);
        } else {
            player.getServer().broadcastMessage(newMessage);
        }
    }

}