package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import com.github.yyeerai.hybridserverapi.common.menu.plugins.VaultHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HasMoney extends AbstractRequirementChecker {
    private double money;

    public HasMoney(JavaPlugin plugin, String content) {
        super(plugin, content);
        try {
            money = Double.parseDouble(content.substring(9).trim());
        } catch (NumberFormatException e) {
            money = Double.MAX_VALUE;
            plugin.getLogger().warning("money需求检查器的值不是数字 " + content);
        }
    }

    @Override
    public boolean meetsRequirements(Player player, String argument) {
        initializationParameters(argument);
        boolean result = false;
        if (VaultHook.isVaultInstalled()) {
            result = VaultHook.getEconomy().getBalance(player) >= money;
        } else {
            plugin.getLogger().warning("Vault未安装, 无法检查金钱要求");
        }
        return result;
    }
}