package com.github.yyeerai.hybridserverapi.common.menu.api;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.common.menu.button.Button;
import com.github.yyeerai.hybridserverapi.common.menu.sound.MenuSound;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
@Setter
@SuppressWarnings("unused")
public class Menu implements InventoryHolder {


    //一下内容是菜单的基本属性
    private final JavaPlugin plugin; //注册菜单的插件
    private final String name; //菜单名称
    private final String title; //菜单标题
    private final int size; //菜单大小
    private final List<Button> buttons; //菜单按钮

    private List<String> commands; //打开菜单的命令
    private ItemStack itemStack; //打开菜单的物品
    private String permission; //菜单权限
    private MenuSound openSound; //打开菜单的音效
    private MenuSound closeSound; //关闭菜单的音效
    private String argument; //菜单传递的参数
    private String catchArgument; //捕获的参数

    private Inventory inventory; //菜单所在的容器
    private Map<Integer, Button> buttonMap; //按钮所在的位置
    private List<Button> newButtons; //新的按钮


    protected Menu(JavaPlugin plugin, String name, String title, int size, List<Button> buttons) {
        this.plugin = plugin;
        this.name = name;
        this.title = title;
        this.size = size;
        this.buttons = buttons;
    }

    private void draw(Player player) {
        this.buttonMap = new HashMap<>();
        this.inventory = Bukkit.createInventory(this, this.size, getTitle(player));
        newButtons = new ArrayList<>(buttons);
        newButtons.sort(Comparator.comparing(Button::getPriority, Comparator.reverseOrder()));
        for (Button button : newButtons) {
            if (getArgument(player) != null && !getArgument(player).isEmpty()) {
                button.setArgument(getArgument(player));
            }
            if (getCatchArgument() != null && !getCatchArgument().isEmpty()) {
                button.setCatchArgument(getCatchArgument());
            }
            List<Integer> slots = button.getSlots();
            if (slots != null && !slots.isEmpty()) {
                for (int i : slots) {
                    if (i < 0 || i >= size) {
                        continue;
                    }
                    if (button.isViewable(player)) {
                        if (!this.buttonMap.containsKey(i)) {
                            inventory.setItem(i, button.getIcon(player));
                            this.buttonMap.put(i, button);
                        }
                    }
                }
            }
        }
    }

    /**
     * 打开菜单
     *
     * @param player 玩家
     */
    public void open(Player player) {
        if (getPermission(player) != null && !getPermission(player).isEmpty() && !player.hasPermission(getPermission(player))) {
            player.sendMessage(HexUtils.colorify("§c你没有权限打开这个菜单"));
            return;
        }
        draw(player);
        player.openInventory(inventory);
        if (openSound != null) {
            openSound.play(player);
        }
        //更新按钮
        for (Button button : newButtons) {
            button.update(player, this);
        }
    }

    /**
     * 获得菜单标题
     *
     * @param player 玩家
     * @return 标题
     */
    public String getTitle(Player player) {
        return HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, title.replace("%arg%", getArgument(player) == null ? "" : getArgument(player))));
    }

    /**
     * 获得菜单额外参数
     *
     * @param player 玩家
     * @return 参数
     */
    public String getArgument(Player player) {
        if (argument == null) {
            return "";
        }
        return PlaceholderAPI.setPlaceholders(player, argument);
    }

    /**
     * 获得菜单权限
     *
     * @param player 玩家
     * @return 权限
     */
    public String getPermission(Player player) {
        if (permission == null) {
            return "";
        }
        return PlaceholderAPI.setPlaceholders(player, permission.replace("%arg%", getArgument(player) == null ? "" : getArgument(player)));
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}