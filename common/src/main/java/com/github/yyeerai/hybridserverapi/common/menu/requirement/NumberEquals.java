package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

/**
 * 字符串比较器
 * 对比类型,可选: equal,greater,less,greaterOrEqual,lessOrEqual,notEqual
 * 支持PlaceholderAPI
 */
public class NumberEquals extends AbstractRequirementChecker {

    private String type;
    private double compareNumber;
    private double inputNumber;


    public NumberEquals(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(13).trim();
        String[] split = substring.split(",");
        if (split.length != 3) {
            plugin.getLogger().warning("numberequals需求检查器的值不正确");
            return;
        }
        try {
            this.type = split[0].trim();
            this.compareNumber = Double.parseDouble(split[1].trim());
            this.inputNumber = Double.parseDouble(split[2].trim());
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("numberequals需求检查器的值不是数字 " + content);
        }
    }

    @Override
    public boolean meetsRequirements(Player player, @Nullable String argument) {
        initializationParameters(argument);
        boolean result = false;
        switch (this.type) {
            case "equal":
                result = this.compareNumber == this.inputNumber;
                break;
            case "greater":
                result = this.compareNumber > this.inputNumber;
                break;
            case "less":
                result = this.compareNumber < this.inputNumber;
                break;
            case "greaterOrEqual":
                result = this.compareNumber >= this.inputNumber;
                break;
            case "lessOrEqual":
                result = this.compareNumber <= this.inputNumber;
                break;
            case "notEqual":
                result = this.compareNumber != this.inputNumber;
                break;
            default:
                break;
        }
        return result;
    }
}