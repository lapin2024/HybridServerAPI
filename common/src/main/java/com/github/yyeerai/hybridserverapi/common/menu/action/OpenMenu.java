package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.menu.api.Menu;
import com.github.yyeerai.hybridserverapi.common.menu.api.MenuApi;
import com.github.yyeerai.hybridserverapi.common.menu.api.MenuCache;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class OpenMenu extends AbstractActionExecutor {

    private final MenuCache menuCache;

    public OpenMenu(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(9);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        String[] splits = trim.split(",");
        String menuName = splits[0].trim();
        this.argument = splits.length > 1 ? splits[1].trim() : "";
        menuCache = MenuApi.getMenuCache(menuName).orElse(null);
        if (menuCache != null) {
            menuCache.setArgument(argument);
        }
    }

    @Override
    public void execute(Player player, String argument, String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> openMenu(player), delay);
        } else {
            openMenu(player);
        }
    }

    private void openMenu(Player player) {
        if (menuCache != null) {
            Menu menu = menuCache.toMenu();
            menu.setArgument(PlaceholderAPI.setPlaceholders(player, menuCache.getArgument() != null ? menuCache.getArgument() : ""));
            menu.open(player);
        } else {
            player.sendMessage("§c菜单不存在");
        }
    }
}