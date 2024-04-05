package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import com.github.yyeerai.hybridserverapi.common.menu.plugins.PlayerPointsHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HasPoints extends AbstractRequirementChecker {

    private int points;

    public HasPoints(JavaPlugin plugin, String content) {
        super(plugin, content);
        try {
            points = Integer.parseInt(content.substring(10).trim());
        } catch (NumberFormatException e) {
            points = Integer.MAX_VALUE;
            plugin.getLogger().warning("points需求检查器的值不是数字 " + content);
        }
    }

    @Override
    public boolean meetsRequirements(Player player, String argument) {
        initializationParameters(argument);
        boolean result = false;
        if (PlayerPointsHook.isPlayerPointsInstalled()) {
            result = PlayerPointsHook.getPlayerPointsAPI().look(player.getUniqueId()) >= points;
        } else {
            plugin.getLogger().warning("PlayerPoints未安装, 无法检查积分要求");
        }
        return result;
    }

}