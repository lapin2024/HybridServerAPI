package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.menu.plugins.PlayerPointsHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class GivePoints extends AbstractActionExecutor {
    private int points;

    public GivePoints(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(11);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        try {
            String[] split = trim.split(",");
            points = Integer.parseInt(split[0].trim());
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("givepoints动作执行器的值不是数字 " + content);
        }
    }

    @Override
    public void execute(Player player, String argument, String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            player.getServer().getScheduler().runTaskLater(plugin, () -> givePoints(player), delay);
        } else {
            givePoints(player);
        }
    }

    private void givePoints(Player player) {
        if (PlayerPointsHook.isPlayerPointsInstalled()) {
            PlayerPointsHook.getPlayerPointsAPI().give(player.getUniqueId(), points);
        } else {
            plugin.getLogger().warning("PlayerPoints插件未安装");
        }
    }


}