package com.github.yyeerai.hybridserverapi.common.menu.plugins;

import com.mc9y.nyeconomy.api.NyEconomyAPI;
import org.bukkit.Bukkit;

public class NyEconomyHook {

    public static boolean isNyEconomyInstalled() {
        return Bukkit.getPluginManager().getPlugin("NyEconomy") != null;
    }

    public static NyEconomyAPI getNyEconomyAPI() {
        return NyEconomyAPI.getInstance();
    }

}