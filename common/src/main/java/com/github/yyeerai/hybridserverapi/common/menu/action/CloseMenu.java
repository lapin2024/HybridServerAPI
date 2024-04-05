package com.github.yyeerai.hybridserverapi.common.menu.action;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CloseMenu extends AbstractActionExecutor {

    private final boolean close;

    public CloseMenu(JavaPlugin plugin, String content) {
        super(plugin, content);        String substring = content.substring(10);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        String[] split = trim.split(",");
        this.close = Boolean.parseBoolean(split[0].trim());
    }

    @Override
    public void execute(Player player, String argument, String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (close) {
            if (chance > 0.0D && Math.random() > chance) {
                return;
            }
            if (delay > 0) {
                player.getServer().getScheduler().runTaskLater(plugin, player::closeInventory, delay);
            } else {
                player.closeInventory();
            }
        }
    }

}