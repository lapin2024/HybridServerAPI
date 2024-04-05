package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.menu.plugins.NyEconomyHook;
import com.mc9y.nyeconomy.api.NyEconomyAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TakeNyEconomy extends AbstractActionExecutor {

    private String type;
    private int nyEconomy;

    public TakeNyEconomy(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(14);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        try {
            String[] split = trim.split(",");
            type = split[0].trim();
            nyEconomy = Integer.parseInt(split[1].trim());
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("takenyeconomy动作执行器的值不是数字 " + content);
        }
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> takeNyEconomy(player), delay);
        } else {
            takeNyEconomy(player);
        }
    }

    private void takeNyEconomy(Player player) {
        if (NyEconomyHook.isNyEconomyInstalled()) {
            NyEconomyAPI nyEconomyAPI = NyEconomyHook.getNyEconomyAPI();
            nyEconomyAPI.withdraw(type, player.getName(), nyEconomy);
        } else {
            plugin.getLogger().warning("NyEconomy插件未安装，无法扣除玩家经济");
        }
    }

}