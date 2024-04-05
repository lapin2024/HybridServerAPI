package com.github.yyeerai.hybridserverapi.common.menu.plugins;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;

public class PlayerPointsHook {
    public static boolean isPlayerPointsInstalled() {
        return Bukkit.getPluginManager().getPlugin("PlayerPoints") != null;
    }

    public static PlayerPointsAPI getPlayerPointsAPI() {
        return PlayerPoints.getInstance().getAPI();
    }

}