package com.github.yyeerai.hybridserverapi.common.menu.plugins;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

import java.util.Objects;

public class VaultHook {

    public static boolean isVaultInstalled() {
        return Bukkit.getPluginManager().getPlugin("Vault") != null;
    }

    public static Economy getEconomy() {
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();
    }

}