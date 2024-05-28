package com.github.yyeerai.hybridserverapi.common.uiapi;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单库存类，实现了库存持有者接口
 * 这个类用于管理玩家的菜单UI
 */
@SuppressWarnings("unused")
public class MenuInventory implements InventoryHolder {

    private final Player player;  // 玩家
    private final Inventory inventory;  // ui
    @Getter
    private final Map<Integer, PageButton> pageButtonMap;  // 页数按钮映射
    private final Map<Integer, Button> buttonMap = new HashMap<>(); // 按钮映射
    @Getter
    @Setter
    private int currentPage = 1;  // 当前页数

    /**
     * 构造函数
     *
     * @param player        玩家
     * @param title         标题
     * @param layout        布局
     * @param pageButtonMap 页数按钮映射
     */
    public MenuInventory(Player player, String title, List<String> layout, Map<Integer, PageButton> pageButtonMap) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, layout.size(), title);
        // 布局
        this.pageButtonMap = pageButtonMap;
    }

    /**
     * 构造函数
     *
     * @param player        玩家
     * @param title         标题
     * @param size          大小
     * @param pageButtonMap 页数按钮映射
     */
    public MenuInventory(Player player, String title, int size, Map<Integer, PageButton> pageButtonMap) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, size, title);
        // 布局
        this.pageButtonMap = pageButtonMap;
    }

    /**
     * 绘制ui
     * 清空按钮映射，然后根据当前页数和布局来绘制UI
     */
    public void draw() {
        inventory.clear();
        buttonMap.clear();
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > pageButtonMap.size()) {
            currentPage = pageButtonMap.size();
        }
        PageButton buttonMap = pageButtonMap.getOrDefault(currentPage, null);
        if (buttonMap == null) {
            return;
        }
        PageButton pageButton = pageButtonMap.get(currentPage);
        pageButton.getButtonMap().forEach((k, v) -> {
            inventory.setItem(k, v.getItemStack(player));
            this.buttonMap.put(k, v);
        });
    }

    /**
     * 打开ui界面
     * 先关闭玩家的库存，然后打开新的库存
     */
    public void open() {
        player.closeInventory();
        player.openInventory(inventory);
    }

    /**
     * 关闭ui界面
     */
    public void close() {
        player.closeInventory();
    }

    /**
     * 处理点击
     * 根据插槽位置获取按钮，如果按钮存在，则处理点击事件
     *
     * @param slot 插槽
     */
    public void handleClick(InventoryClickEvent event, MenuInventory menuInventory, int slot) {
        Button button = buttonMap.get(slot);
        if (button != null) {
            button.handle(event, menuInventory);
        }
    }

    /**
     * 获取库存
     *
     * @return 库存
     */
    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }


    /**
     * Builder类，用于创建MenuInventory对象
     */
    public static class Builder {
        private Player player;
        private String title;
        private int size;
        private Map<Integer, PageButton> pageButtonMap;

        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder pageButtonMap(Map<Integer, PageButton> pageButtonMap) {
            this.pageButtonMap = pageButtonMap;
            return this;
        }

        public MenuInventory build() {
            return new MenuInventory(player, title, size, pageButtonMap);
        }

    }

}