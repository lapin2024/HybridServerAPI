package com.github.yyeerai.hybridserverapi.common.menu.listen;

import com.github.yyeerai.hybridserverapi.common.event.BukkitEvents;
import com.github.yyeerai.hybridserverapi.common.menu.api.Menu;
import com.github.yyeerai.hybridserverapi.common.menu.api.MenuApi;
import com.github.yyeerai.hybridserverapi.common.menu.api.MenuCache;
import com.github.yyeerai.hybridserverapi.common.menu.button.Button;
import com.github.yyeerai.hybridserverapi.common.menu.enums.EnumClickType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public class MenuListener {

    public static void eventSubscriber(JavaPlugin plugin) {
        BukkitEvents.subscribe(plugin, InventoryClickEvent.class, EventPriority.HIGHEST)
                .filter(MenuListener::isValidInventoryClickEvent)
                .handler(MenuListener::handleInventoryClickEvent);
        BukkitEvents.subscribe(plugin, InventoryDragEvent.class, EventPriority.HIGHEST)
                .filter(MenuListener::isValidInventoryClickEvent)
                .handler(MenuListener::handlePlayerInteractEvent);

        BukkitEvents.subscribe(plugin, PlayerInteractEvent.class, EventPriority.HIGHEST)
                .filter(MenuListener::isValidPlayerInteractEvent)
                .handler(MenuListener::handlePlayerInteractEvent);
        BukkitEvents.subscribe(plugin, InventoryCloseEvent.class, EventPriority.HIGHEST)
                .filter(MenuListener::isValidPlayerInteractEvent)
                .handler(MenuListener::handlePlayerInteractEvent);
        BukkitEvents.subscribe(plugin, PlayerCommandPreprocessEvent.class, EventPriority.HIGHEST)
                .handler(MenuListener::handlePlayerCommandPreprocessEvent);
    }

    private static void handlePlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        String message = playerCommandPreprocessEvent.getMessage();
        for (MenuCache menuCach : MenuApi.getMenuCaches()) {
            if (menuCach.getCommands().contains(message.replace("/", ""))) {
                menuCach.toMenu().open(playerCommandPreprocessEvent.getPlayer());
                playerCommandPreprocessEvent.setCancelled(true);
                break;
            }
        }
    }

    private static void handlePlayerInteractEvent(InventoryCloseEvent inventoryCloseEvent) {
        Menu menu = (Menu) inventoryCloseEvent.getInventory().getHolder();
        if (menu == null || !MenuApi.getMenuCache(menu.getName()).isPresent()) {
            return;
        }
        if (menu.getCloseSound() != null) {
            menu.getCloseSound().play((Player) inventoryCloseEvent.getPlayer());
        }
    }

    private static boolean isValidPlayerInteractEvent(InventoryCloseEvent inventoryCloseEvent) {
        return inventoryCloseEvent.getPlayer() instanceof Player
                && inventoryCloseEvent.getInventory().getHolder() instanceof Menu;
    }

    private static void handlePlayerInteractEvent(InventoryDragEvent inventoryDragEvent) {
        inventoryDragEvent.setCancelled(true);
    }

    private static boolean isValidInventoryClickEvent(InventoryDragEvent inventoryDragEvent) {
        return inventoryDragEvent.getInventory().getHolder() instanceof Menu;
    }

    private static boolean isValidInventoryClickEvent(InventoryClickEvent event) {
        return event.getClickedInventory() != null
                && event.getInventory().getHolder() instanceof Menu
                && event.getWhoClicked() instanceof Player;
    }

    private static void handleInventoryClickEvent(InventoryClickEvent event) {
        event.setCancelled(true);
        Menu menu = (Menu) event.getInventory().getHolder();
        if (menu == null || !MenuApi.getMenuCache(menu.getName()).isPresent()) {
            return;
        }
        if (menu.getButtonMap().containsKey(event.getRawSlot())) {
            handleButtonClick(event, menu);
        }
    }

    private static void handleButtonClick(InventoryClickEvent event, Menu menu) {
        ClickType click = event.getClick();
        Player player = (Player) event.getWhoClicked();
        Button button = menu.getButtonMap().get(event.getRawSlot());
        button.execute(player, EnumClickType.DEFAULT);
        EnumClickType clickType = EnumClickType.getClickType(click.name());
        button.execute(player, clickType);
    }

    private static boolean isValidPlayerInteractEvent(PlayerInteractEvent event) {
        return event.getHand() != null
                && event.getHand().equals(EquipmentSlot.HAND)
                && event.getItem() != null;
    }

    private static void handlePlayerInteractEvent(PlayerInteractEvent event) {
        Collection<MenuCache> menuCaches = MenuApi.getMenuCaches();
        for (MenuCache menuCache : menuCaches) {
            if (event.getItem() == null) {
                continue;
            }
            if (event.getItem().isSimilar(menuCache.getOpenItem())) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    menuCache.toMenu().open(event.getPlayer());
                    break;
                }
            }
        }
    }
}