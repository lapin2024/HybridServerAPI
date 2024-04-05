package com.github.yyeerai.hybridserverapi.common.menu.api;

import com.github.yyeerai.hybridserverapi.common.menu.button.Button;
import com.github.yyeerai.hybridserverapi.common.menu.sound.MenuSound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class MenuApi {
    private static final Map<String, MenuCache> menuMap = new ConcurrentHashMap<>(); //菜单缓存


    /**
     * 注册菜单
     *
     * @param name 菜单名称
     * @param menu 菜单缓存
     */
    public static void registerMenu(String name, MenuCache menu) {
        menuMap.put(name, menu);
    }

    /**
     * 注销菜单
     *
     * @param name 菜单名称
     */
    public static void unregisterMenu(String name) {
        menuMap.remove(name);
    }

    /**
     * 获取菜单缓存
     *
     * @param name 菜单名称
     * @return 菜单缓存
     */
    public static Optional<MenuCache> getMenuCache(String name) {
        return Optional.ofNullable(menuMap.get(name));
    }

    public static Collection<MenuCache> getMenuCaches() {
        return menuMap.values();
    }

    public static void unregisterAll() {
        menuMap.clear();
    }

    public static class MenuCacheBuilder {

        private JavaPlugin plugin;
        private String name;
        private String title;
        private int size;
        private List<Button> buttons;
        private List<String> openCommands;
        private ItemStack openItem;
        private String permission;
        private MenuSound openSound;
        private MenuSound closeSound;
        private String argument; //菜单传递的参数
        private String catchArgument; //捕获的参数


        public MenuCacheBuilder setPlugin(JavaPlugin plugin) {
            this.plugin = plugin;
            return this;
        }

        public MenuCacheBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public MenuCacheBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public MenuCacheBuilder setSize(int size) {
            this.size = size;
            return this;
        }

        public MenuCacheBuilder setPermission(String permission) {
            this.permission = permission;
            return this;
        }

        public MenuCacheBuilder setOpenSound(MenuSound openSound) {
            this.openSound = openSound;
            return this;
        }

        public MenuCacheBuilder setCloseSound(MenuSound closeSound) {
            this.closeSound = closeSound;
            return this;
        }

        public MenuCacheBuilder setButtons(List<Button> buttons) {
            this.buttons = buttons;
            return this;
        }

        public MenuCacheBuilder setOpenCommands(List<String> openCommands) {
            this.openCommands = openCommands;
            return this;
        }

        public MenuCacheBuilder setOpenItem(ItemStack openItem) {
            this.openItem = openItem;
            return this;
        }

        public MenuCacheBuilder setArgument(String argument) {
            this.argument = argument;
            return this;
        }

        public MenuCacheBuilder setCatchArgument(String catchArgument) {
            this.catchArgument = catchArgument;
            return this;
        }

        public MenuCache build() {
            MenuCache menuCache = new MenuCache(plugin, name, title, size, buttons, openCommands, openItem, permission, openSound, closeSound);
            menuCache.setArgument(argument);
            menuCache.setCatchArgument(catchArgument);
            return menuCache;
        }
    }

}