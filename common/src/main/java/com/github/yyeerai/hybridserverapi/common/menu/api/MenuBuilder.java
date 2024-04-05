package com.github.yyeerai.hybridserverapi.common.menu.api;

import com.github.yyeerai.hybridserverapi.common.menu.button.Button;
import com.github.yyeerai.hybridserverapi.common.menu.sound.MenuSound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * 菜单构建器
 */
public class MenuBuilder {

    private final JavaPlugin plugin;
    private final String name;
    private final String title;
    private final int size;
    private final List<Button> buttons;
    private List<String> commands; //打开菜单的命令
    private ItemStack itemStack; //打开菜单的物品
    private String permission; //菜单权限
    private MenuSound openSound; //打开菜单的音效
    private MenuSound closeSound; //关闭菜单的音效
    private String argument; //菜单传递的参数
    private String catchArgument; //捕获的参数

    protected MenuBuilder(JavaPlugin plugin, String name, String title, int size, List<Button> buttons) {
        this.plugin = plugin;
        this.name = name;
        this.title = title;
        this.size = size;
        this.buttons = buttons;
    }

    public MenuBuilder setCommands(List<String> commands) {
        this.commands = commands;
        return this;
    }

    public MenuBuilder setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public MenuBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public MenuBuilder setOpenSound(MenuSound openSound) {
        this.openSound = openSound;
        return this;
    }

    public MenuBuilder setCloseSound(MenuSound closeSound) {
        this.closeSound = closeSound;
        return this;
    }

    public MenuBuilder setArgument(String argument) {
        this.argument = argument;
        return this;
    }

    public MenuBuilder setCatchArgument(String catchArgument) {
        this.catchArgument = catchArgument;
        return this;
    }

    public Menu build() {
        Menu menu = new Menu(plugin, name, title, size, buttons);
        menu.setCommands(commands);
        menu.setItemStack(itemStack);
        menu.setPermission(permission);
        menu.setOpenSound(openSound);
        menu.setCloseSound(closeSound);
        menu.setArgument(argument);
        menu.setCatchArgument(catchArgument);
        return menu;
    }
}