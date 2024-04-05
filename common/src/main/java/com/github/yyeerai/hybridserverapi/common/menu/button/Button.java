package com.github.yyeerai.hybridserverapi.common.menu.button;

import com.github.yyeerai.hybridserverapi.common.menu.api.Menu;
import com.github.yyeerai.hybridserverapi.common.menu.enums.EnumClickType;
import com.github.yyeerai.hybridserverapi.common.menu.requirement.RequirementChecker;
import com.github.yyeerai.hybridserverapi.common.serializeitem.ItemUtil;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 按钮类
 */
@Getter
@Setter
public class Button {

    private List<Integer> slots; //按钮所在的位置(直接指定多个位置)
    private Map<String, Object> icon; //按钮图标
    private List<RequirementChecker> viewRequirements; //查看条件

    private String argument; //菜单传递的参数
    private String catchArgument; //捕获的参数

    private int priority; //优先级
    private int refreshDelay; //刷新延迟

    private ButtonAction buttonAction; //按钮动作

    private BukkitTask task; //菜单刷新任务

    protected Button() {
    }

    public void execute(Player player, EnumClickType clickType) {
        if (buttonAction != null) {
            buttonAction.execute(player, clickType, getArgument(player), getCatchArgument(player));
        }
    }


    public boolean isViewable(Player player) {
        if (viewRequirements == null || viewRequirements.isEmpty()) {
            return true;
        }
        return viewRequirements.stream().allMatch(requirement -> requirement.meetsRequirements(player, getArgument(player)));
    }


    /**
     * 更新按钮
     *
     * @param player 玩家
     * @param menu   菜单
     */
    public void update(Player player, Menu menu) {
        if (refreshDelay > 0) {
            task = Bukkit.getScheduler().runTaskTimer(menu.getPlugin(), () -> {
                if (!menu.getInventory().getViewers().isEmpty() && menu.getInventory().getViewers().contains(player)) {
                    if (slots != null && !slots.isEmpty()) {
                        for (int i : slots) {
                            if (i < 0 || i >= menu.getSize()) {
                                continue;
                            }
                            ItemStack icon1 = getIcon(player);
                            if (icon1.equals(menu.getInventory().getItem(i))) {
                                continue;
                            }
                            menu.getButtonMap().remove(i);
                            menu.getInventory().setItem(i, getIcon(player));
                            menu.getButtonMap().put(i, this);
                        }
                    }
                } else {
                    task.cancel();
                }
            }, 0, refreshDelay);
        }
    }

    public String getArgument(Player player) {
        return formatString(player, argument);
    }

    public String getCatchArgument(Player player) {
        return formatString(player, catchArgument);
    }


    private String formatString(Player player, String input) {
        if (input == null) {
            return "";
        }
        return PlaceholderAPI.setPlaceholders(player, input);
    }

    @SuppressWarnings("unchecked")
    public ItemStack getIcon(Player player) {
        Map<String, Object> newIcon = new HashMap<>();
        icon.forEach((key, value) -> {
            switch (key) {
                case "name":
                case "nbt":
                    newIcon.put(key, processPlaceholder(player, (String) value));
                    break;
                case "lore":
                    List<String> lore = ((List<String>) value).stream()
                            .map(string -> processPlaceholder(player, string))
                            .collect(Collectors.toList());
                    newIcon.put("lore", lore);
                    break;
                default:
                    newIcon.put(key, value);
                    break;
            }
        });
        return ItemUtil.translateColor(ItemUtil.deserializeItemStack(newIcon), false);
    }

    private String processPlaceholder(Player player, String input) {
        return PlaceholderAPI.setPlaceholders(player, input.replace("%arg%", getArgument(player)).replace("%catchArg%", getCatchArgument(player)));
    }

    @Override
    public String toString() {
        return "Button{" +
                "slots=" + slots +
                ", icon=" + icon +
                ", viewRequirements=" + viewRequirements +
                ", argument='" + argument + '\'' +
                ", catchArgument='" + catchArgument + '\'' +
                ", priority=" + priority +
                ", refreshDelay=" + refreshDelay +
                ", buttonAction=" + buttonAction +
                ", task=" + task +
                '}';
    }

}