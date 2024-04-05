package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendTitle extends AbstractActionExecutor {
    private final String title;
    private final String subTitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    public SendTitle(JavaPlugin plugin, String content) {
        super(plugin, content);

        String substring = content.substring(6);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String str = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        String titlePattern = "title:([^,]*)";
        String subtitlePattern = "subtitle:([^,]*)";
        String fadeinPattern = "fadein:([^,]*)";
        String stayPattern = "stay:([^,]*)";
        String fadeoutPattern = "fadeout:(.*)";
        Pattern pattern;
        Matcher matcher;
        Map<String, String> map = new HashMap<>();
        pattern = Pattern.compile(titlePattern);
        matcher = pattern.matcher(str);
        map.put("title", matcher.find() ? matcher.group(1).trim() : "");
        pattern = Pattern.compile(subtitlePattern);
        matcher = pattern.matcher(str);
        map.put("subtitle", matcher.find() ? matcher.group(1).trim() : "");
        pattern = Pattern.compile(fadeinPattern);
        matcher = pattern.matcher(str);
        map.put("fadein", matcher.find() ? matcher.group(1).trim() : "");
        pattern = Pattern.compile(stayPattern);
        matcher = pattern.matcher(str);
        map.put("stay", matcher.find() ? matcher.group(1).trim() : "");
        pattern = Pattern.compile(fadeoutPattern);
        matcher = pattern.matcher(str);
        map.put("fadeout", matcher.find() ? matcher.group(1).trim() : "");
        title = map.get("title");
        subTitle = map.get("subtitle");
        fadeIn = Integer.parseInt(map.get("fadein"));
        stay = Integer.parseInt(map.get("stay"));
        fadeOut = Integer.parseInt(map.get("fadeout"));

    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> sendTitle(player), delay);
        } else {
            sendTitle(player);
        }
    }

    private void sendTitle(Player player) {
        String t = HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, title.replace("%arg%", (argument != null ? argument : "")).replace("%catchArg%", (catchArgument != null ? catchArgument : ""))));
        String s = HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, subTitle.replace("%arg%", (argument != null ? argument : "")).replace("%catchArg%", (catchArgument != null ? catchArgument : ""))));
        player.sendTitle(t, s, fadeIn, stay, fadeOut);
    }

}