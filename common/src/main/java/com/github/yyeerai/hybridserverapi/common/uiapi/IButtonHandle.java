package com.github.yyeerai.hybridserverapi.common.uiapi;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface IButtonHandle {
    void handle(InventoryClickEvent event, MenuInventory menuInventory);
}