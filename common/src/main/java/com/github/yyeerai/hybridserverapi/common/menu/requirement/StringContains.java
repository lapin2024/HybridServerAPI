package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

/**
 * 字符串比较器
 * 包含返回true
 * 不包含返回false
 * 支持PlaceholderAPI
 */
public class StringContains extends AbstractRequirementChecker {

    private final String compareString;
    private final String inputString;

    public StringContains(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(15).trim();
        String[] splits = substring.split(",");
        if (splits.length != 2) {
            plugin.getLogger().warning("stringcontains需求检查器的值不正确");
            compareString = "";
            inputString = "";
            return;
        }
        compareString = splits[0].trim();
        inputString = splits[1].trim();
    }

    @Override
    public boolean meetsRequirements(Player player, @Nullable String argument) {
        initializationParameters(argument);
        String s = PlaceholderAPI.setPlaceholders(player, this.compareString.replace("%arg%", this.argument == null ? "" : this.argument));
        String s1 = PlaceholderAPI.setPlaceholders(player, this.inputString.replace("%arg%", this.argument == null ? "" : this.argument));
        s = s.startsWith("!") ? s.substring(1) : s;
        return this.compareString.startsWith("!") != s1.contains(s);
    }
}