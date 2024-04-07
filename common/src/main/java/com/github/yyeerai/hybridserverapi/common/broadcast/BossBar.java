package com.github.yyeerai.hybridserverapi.common.broadcast;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BossBar extends AbstractBroadcast{
    private static final String titlePattern = "title:([^,]*)";
    private static final String colorPattern = "color:([^,]*)";
    private static final String stylePattern = "style:([^,]*)";
    private static final String timePattern = "time:(.*)";
    /**
     * 构造一个新的 AbstractBroadcast 实例。
     *
     * @param message 广播的消息内容
     */
    public BossBar(String message) {
        super(message);
    }

    @Override
    public void broadcast(Player player) {
        Pattern pattern;
        Matcher matcher;
        String title;
        BarColor color;
        BarStyle style;
        int time;
        pattern = Pattern.compile(titlePattern);
        matcher = pattern.matcher(message);
        title = matcher.find() ? matcher.group(1).trim() : "";
        pattern = Pattern.compile(colorPattern);
        matcher = pattern.matcher(message);
        color = matcher.find() ? BarColor.valueOf(matcher.group(1).trim().toUpperCase()) : BarColor.RED;
        pattern = Pattern.compile(stylePattern);
        matcher = pattern.matcher(message);
        style = matcher.find() ? BarStyle.valueOf(matcher.group(1).trim().toUpperCase()) : BarStyle.SOLID;
        pattern = Pattern.compile(timePattern);
        matcher = pattern.matcher(message);
        time = matcher.find() ? Integer.parseInt(matcher.group(1).trim()) : 120;
        String finalTitle = player != null ? PlaceholderAPI.setPlaceholders(player, title) : title;
        org.bukkit.boss.BossBar bossBar = Bukkit.createBossBar(HexUtils.colorify(title), color, style);
        bossBar.setVisible(true);
        bossBar.setProgress(1.0);
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HybridServerAPI")), bossBar::removeAll, time);
    }
}