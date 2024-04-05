package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.menu.plugins.VaultHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TakeMoney extends AbstractActionExecutor {
    private double money;

    public TakeMoney(JavaPlugin plugin, String content) {
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
            plugin.getLogger().warning("takemoney动作执行器的值不是数字 " + content);
        }
    }

    @Override
    public void execute(Player player, String argument, String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> takeMoney(player), delay);
        } else {
            takeMoney(player);
        }
    }

    private void takeMoney(Player player) {
        if (VaultHook.isVaultInstalled()) {
            VaultHook.getEconomy().withdrawPlayer(player, money);
        } else {
            plugin.getLogger().warning("Vault插件未安装，无法扣除玩家金币");
        }
    }

}