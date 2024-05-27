package com.github.yyeerai.hybridserverapi.common.uiapi;

import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Button类，用于处理按钮相关的操作
 */
public class Button {

    @Getter
    private final int slot;
    // ItemStack对象，代表按钮
    private final ItemStack itemStack;
    // IButtonHandle对象，用于处理按钮的行为
    @Setter
    private IButtonHandle buttonHandle;

    // 私有构造函数，只能通过Builder创建Button对象
    private Button(int slot, ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    /**
     * 处理按钮的行为
     */
    public void handle(InventoryClickEvent event,MenuInventory menuInventory) {
        if (buttonHandle != null) {
            buttonHandle.handle(event,menuInventory);
        }
    }

    /**
     * 获取ItemStack对象，并处理其元数据
     *
     * @param player Player对象，用于设置占位符
     * @return 处理后的ItemStack对象
     */
    public ItemStack getItemStack(Player player) {
        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;
        if (itemMeta != null) {
            String displayName = itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : null;
            if (displayName != null) {
                itemMeta.setDisplayName(PlaceholderAPI.setPlaceholders(player, displayName));
            }
            List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : null;
            if (lore != null) {
                lore.replaceAll(text -> PlaceholderAPI.setPlaceholders(player, text));
                itemMeta.setLore(lore);
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    /**
     * Button的构建者类，用于创建Button对象
     */
    public static class Builder {
        private int slot;
        // ItemStack对象，代表按钮
        private ItemStack itemStack;
        // IButtonHandle对象，用于处理按钮的行为
        private IButtonHandle buttonHandle;

        /**
         * 设置ItemStack对象
         *
         * @param itemStack ItemStack对象
         * @return Builder对象，用于链式调用
         */
        public Builder itemStack(int slot, ItemStack itemStack) {
            this.slot = slot;
            this.itemStack = itemStack;
            return this;
        }

        /**
         * 设置IButtonHandle对象
         *
         * @param buttonHandle IButtonHandle对象
         * @return Builder对象，用于链式调用
         */
        public Builder handle(IButtonHandle buttonHandle) {
            this.buttonHandle = buttonHandle;
            return this;
        }

        /**
         * 创建Button对象
         *
         * @return Button对象
         */
        public Button build() {
            Button button = new Button(slot, itemStack);
            button.setButtonHandle(buttonHandle);
            return button;
        }
    }

}