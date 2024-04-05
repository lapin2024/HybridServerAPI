package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

/**
 * 字符串比较器
 * 相同返回true
 * 不同返回false
 * 支持PlaceholderAPI
 */
public class StringEquals extends AbstractRequirementChecker {

    private final boolean ignoreCase;
    private final String compareString;
    private final String inputString;

    public StringEquals(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(13).trim();
        String[] splits = substring.split(",");
        if (splits.length != 3) {
            plugin.getLogger().warning("stringequals需求检查器的值不正确");
            ignoreCase = false;
            compareString = "";
            inputString = "";
            return;
        }
        ignoreCase = Boolean.parseBoolean(splits[0].trim());
        compareString = splits[1].trim();
        inputString = splits[2].trim();
    }

    @Override
    public boolean meetsRequirements(Player player, @Nullable String argument) {
        initializationParameters(argument);
        String c = PlaceholderAPI.setPlaceholders(player, this.compareString.replace("%arg%", this.argument == null ? "" : this.argument));
        String i = PlaceholderAPI.setPlaceholders(player, this.inputString.replace("%arg%", this.argument == null ? "" : this.argument));
        boolean equals;
        boolean not = this.compareString.startsWith("!");
        c = c.startsWith("!") ? c.substring(1) : c;
        if (not) {
            equals = this.ignoreCase ? !c.equalsIgnoreCase(i) : !c.equals(i);
        } else {
            equals = this.ignoreCase ? c.equalsIgnoreCase(i) : c.equals(i);
        }
        return equals;
    }
}