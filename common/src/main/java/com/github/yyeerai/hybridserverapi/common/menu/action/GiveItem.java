package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class GiveItem extends AbstractActionExecutor {
    private final ItemStack itemStack;

    public GiveItem(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(9);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        this.delay = getDelay(angleBracketsContent);
        this.chance = getChance(angleBracketsContent);
        itemStack = extractItems(trim);
    }

    @Override
    public void execute(Player player, String argument, String catchArgument) {
        initializationParameters(argument, catchArgument);
        ItemStack copy = itemStack.clone();
        ItemMeta itemMeta = copy.hasItemMeta() ? copy.getItemMeta() : Bukkit.getItemFactory().getItemMeta(copy.getType());
        assert itemMeta != null;
        String displayName = itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : null;
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : null;
        if (displayName != null) {
            displayName = HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, displayName.replace("%arg%", this.argument == null ? "" : this.argument).replace("%catchArg%", this.catchArgument == null ? "" : this.catchArgument)));
            itemMeta.setDisplayName(displayName);
        }
        if (lore != null) {
            lore.replaceAll(string -> HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, string.replace("%arg%", this.argument == null ? "" : this.argument).replace("%catchArg%", this.catchArgument == null ? "" : this.catchArgument))));
            itemMeta.setLore(lore);
        }
        copy.setItemMeta(itemMeta);

        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> giveItem(player, copy), delay);
        } else {
            giveItem(player, copy);
        }
    }

    private void giveItem(Player player, ItemStack copy) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(copy);
        } else {
            player.getWorld().dropItem(player.getLocation(), copy);
        }
    }

}