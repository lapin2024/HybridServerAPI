package com.github.yyeerai.hybridserverapi.common.uiapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryClick implements Listener {

    public InventoryClick(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }
        if (event.getInventory().getHolder() instanceof MenuInventory) {
            event.setCancelled(true);
            MenuInventory menuInventory = (MenuInventory) event.getInventory().getHolder();
            menuInventory.handleClick(event.getRawSlot());
        }
    }

}