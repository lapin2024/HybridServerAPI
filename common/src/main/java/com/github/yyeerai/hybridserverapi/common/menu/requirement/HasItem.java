package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class HasItem extends AbstractRequirementChecker {


    private final ItemStack itemStack;
    public HasItem(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(8);
        itemStack = extractItems(substring.trim());
    }

    @Override
    public boolean meetsRequirements(Player player, String argument) {
        initializationParameters(argument);
        ItemStack copy = itemStack.clone();
        ItemMeta itemMeta = copy.hasItemMeta() ? copy.getItemMeta() : Bukkit.getItemFactory().getItemMeta(copy.getType());
        assert itemMeta != null;
        String displayName = itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : null;
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : null;
        if (displayName != null) {
            displayName = HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, displayName.replace("%arg%", this.argument == null ? "" : this.argument)));
            itemMeta.setDisplayName(displayName);
        }
        if (lore != null) {
            lore.replaceAll(string -> HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, string.replace("%arg%", this.argument == null ? "" : this.argument))));
            itemMeta.setLore(lore);
        }
        copy.setItemMeta(itemMeta);
        return player.getInventory().containsAtLeast(copy, copy.getAmount());
    }
}