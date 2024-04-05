package com.github.yyeerai.hybridserverapi.common.menu.api;


import com.github.yyeerai.hybridserverapi.common.menu.button.Button;
import com.github.yyeerai.hybridserverapi.common.menu.sound.MenuSound;
import com.github.yyeerai.hybridserverapi.common.serializeitem.ItemUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuCache {


    private final JavaPlugin plugin;
    private final String name;
    private final String title;
    private final int size;
    private final List<Button> buttons;

    private final List<String> commands;
    private final ItemStack itemStack;
    private final String permission;
    private final MenuSound openSound;
    private final MenuSound closeSound;
    @Setter
    private String argument; //菜单传递的参数
    @Setter
    private String catchArgument; //捕获的参数


    public MenuCache(JavaPlugin plugin, String name, String title, int size, List<Button> buttons, List<String> commands, ItemStack itemStack, String permission, MenuSound openSound, MenuSound closeSound) {
        this.plugin = plugin;
        this.name = name;
        this.title = title;
        this.size = size;
        this.buttons = buttons;
        this.commands = commands;
        this.itemStack = itemStack;
        this.permission = permission;
        this.openSound = openSound;
        this.closeSound = closeSound;
    }

    public Menu toMenu() {
        return new MenuBuilder(plugin, name, title, size, buttons)
                .setCommands(commands)
                .setItemStack(getOpenItem())
                .setPermission(permission)
                .setOpenSound(openSound)
                .setCloseSound(closeSound)
                .setArgument(argument)
                .setCatchArgument(catchArgument)
                .build();
    }

    public ItemStack getOpenItem() {
        if (itemStack == null) {
            return null;
        }
        ItemStack clone = this.itemStack.clone();
        ItemMeta meta = clone.hasItemMeta() ? clone.getItemMeta() : Bukkit.getItemFactory().getItemMeta(clone.getType());
        assert meta != null;
        String displayName = meta.hasDisplayName() ? meta.getDisplayName() : null;
        if (displayName != null) {
            meta.setDisplayName(displayName);
        }
        List<String> lore = meta.hasLore() ? meta.getLore() : null;
        if (lore != null) {
            List<String> newLore = new ArrayList<>(lore);
            meta.setLore(newLore);
        }
        clone.setItemMeta(meta);
        return ItemUtil.translateColor(clone, false);
    }

    public void registerHandler() {
        MenuApi.registerMenu(name, this);
    }

    public void unregisterHandler() {
        MenuApi.unregisterMenu(name);
    }

}