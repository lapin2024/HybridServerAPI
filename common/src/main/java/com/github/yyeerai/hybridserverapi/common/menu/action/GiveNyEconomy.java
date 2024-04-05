package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.menu.plugins.NyEconomyHook;
import com.mc9y.nyeconomy.api.NyEconomyAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GiveNyEconomy extends AbstractActionExecutor {

    private String type;
    private int nyEconomy;

    public GiveNyEconomy(JavaPlugin plugin, String content) {
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
            plugin.getLogger().warning("givenyeconomy动作执行器的值不是数字 " + content);
        }
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            player.getServer().getScheduler().runTaskLater(plugin, () -> giveNyEconomy(player), delay);
        } else {
            giveNyEconomy(player);
        }
    }

    private void giveNyEconomy(Player player) {
        if (NyEconomyHook.isNyEconomyInstalled()) {
            NyEconomyAPI nyEconomyAPI = NyEconomyHook.getNyEconomyAPI();
            nyEconomyAPI.deposit(type, player.getName(), nyEconomy);
        } else {
            plugin.getLogger().warning("NyEconomy插件未安装");
        }
    }

}