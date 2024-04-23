package com.github.yyeerai.hybridserverapi.common.broadcast;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class BossBar extends AbstractBroadcast {

    private static final BossBarManager bossBarManager = BossBarManager.getInstance();
    private static final String titlePattern = "title:([^,]*)";
    private static final String colorPattern = "color:([^,]*)";
    private static final String stylePattern = "style:([^,]*)";
    private static final String timePattern = "time:(.*)";
    private int time = 120;
    private org.bukkit.boss.BossBar bossBar;

    /**
     * 构造一个新的 AbstractBroadcast 实例。
     *
     * @param message 广播的消息内容
     */
    public BossBar(String message) {
        super(message);
    }

    @Override
    public void broadcast() {
        bossBar = createBossBar();
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
        bossBarManager.addBossBar(this);
    }


    @Override
    public void sendMessage(Player player) {
        org.bukkit.boss.BossBar bossBar = createBossBar();
        bossBar.addPlayer(player);
        bossBarManager.addBossBar(this);
    }

    @Override
    public void broadcast(Player player) {
        broadcast();
    }

    private org.bukkit.boss.BossBar createBossBar() {
        Pattern pattern;
        Matcher matcher;
        String title;
        BarColor color;
        BarStyle style;
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
        org.bukkit.boss.BossBar bossBar = Bukkit.createBossBar(HexUtils.colorify(title), color, style);
        bossBar.setVisible(false);
        bossBar.setProgress(1.0);
        return bossBar;
    }
}