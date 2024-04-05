package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.menu.plugins.VaultHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class GiveMoney extends AbstractActionExecutor {
    private double money;

    public GiveMoney(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(10);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        try {
            String[] split = trim.split(",");
            money = Double.parseDouble(split[0].trim());
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("giveMoney动作执行器的值不是数字" + content);
        }
    }

    @Override
    public void execute(Player player, String argument, String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            player.getServer().getScheduler().runTaskLater(plugin, () -> giveMoney(player), delay);
        } else {
            giveMoney(player);
        }
    }

    private void giveMoney(Player player) {
        if (VaultHook.isVaultInstalled()) {
            VaultHook.getEconomy().depositPlayer(player, money);
        } else {
            plugin.getLogger().warning("Vault插件未安装");
        }
    }

}