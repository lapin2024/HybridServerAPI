package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import com.github.yyeerai.hybridserverapi.common.menu.plugins.NyEconomyHook;
import com.mc9y.nyeconomy.api.NyEconomyAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public class HasNyEconomy extends AbstractRequirementChecker {

    private String type;
    private int nyEconomy;

    public HasNyEconomy(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(13).trim();
        String[] split = substring.split(",");
        try {
            type = split[0].trim();
            nyEconomy = Integer.parseInt(split[1].trim());
        } catch (NumberFormatException e) {
            nyEconomy = Integer.MAX_VALUE;
            plugin.getLogger().warning("nyEconomy需求检查器的值不是数字 " + content);
        }
    }

    @Override
    public boolean meetsRequirements(Player player, @Nullable String argument) {
        initializationParameters(argument);
        boolean result = false;
        if (NyEconomyHook.isNyEconomyInstalled()) {
            NyEconomyAPI nyEconomyAPI = NyEconomyHook.getNyEconomyAPI();
            result = nyEconomyAPI.getBalance(type, player.getName()) >= nyEconomy;
        } else {
            plugin.getLogger().warning("NyEconomy未安装, 无法检查金钱要求");
        }
        return result;
    }
}