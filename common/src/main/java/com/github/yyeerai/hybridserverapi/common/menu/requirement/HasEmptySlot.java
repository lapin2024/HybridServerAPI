package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public class HasEmptySlot extends AbstractRequirementChecker {

    private int emptySlot;

    public HasEmptySlot(JavaPlugin plugin, String content) {
        super(plugin, content);
        try {
            emptySlot = Integer.parseInt(content.substring(13).trim());
        } catch (NumberFormatException e) {
            emptySlot = 0;
            plugin.getLogger().warning("HasEmptySlot初始化失败,参数错误");
        }
    }

    @Override
    public boolean meetsRequirements(Player player, @Nullable String argument) {
        initializationParameters(argument);
        //检查玩家背包是否有空位,空位的数量是否大于等于emptySlot
        int sum = 0;
        PlayerInventory inventory = player.getInventory();
        ItemStack[] contents = inventory.getContents();
        for (ItemStack content : contents) {
            if (content == null) {
                sum++;
            }
        }
        return sum >= emptySlot;
    }
}