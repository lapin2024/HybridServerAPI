package com.github.yyeerai.hybridserverapi.common.broadcast;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Title extends AbstractBroadcast {

    private static final String titlePattern = "title:([^,]*)";
    private static final String subtitlePattern = "subtitle:([^,]*)";
    private static final String fadeinPattern = "fadein:([^,]*)";
    private static final String stayPattern = "stay:([^,]*)";
    private static final String fadeoutPattern = "fadeout:(.*)";

    /**
     * 构造一个新的 AbstractBroadcast 实例。
     *
     * @param message 广播的消息内容
     */
    public Title(String message) {
        super(message);
    }

    @Override
    public void broadcast() {
        Pattern pattern;
        Matcher matcher;
        String title;
        String subTitle;
        int fadeIn;
        int stay;
        int fadeOut;
        pattern = Pattern.compile(titlePattern);
        matcher = pattern.matcher(message);
        title = matcher.find() ? matcher.group(1).trim() : "";
        pattern = Pattern.compile(subtitlePattern);
        matcher = pattern.matcher(message);
        subTitle = matcher.find() ? matcher.group(1).trim() : "";
        pattern = Pattern.compile(fadeinPattern);
        matcher = pattern.matcher(message);
        fadeIn = matcher.find() ? Integer.parseInt(matcher.group(1).trim()) : 20;
        pattern = Pattern.compile(stayPattern);
        matcher = pattern.matcher(message);
        stay = matcher.find() ? Integer.parseInt(matcher.group(1).trim()) : 40;
        pattern = Pattern.compile(fadeoutPattern);
        matcher = pattern.matcher(message);
        fadeOut = matcher.find() ? Integer.parseInt(matcher.group(1).trim()) : 20;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(HexUtils.colorify(title), HexUtils.colorify(subTitle), fadeIn, stay, fadeOut);
        }
    }

    @Override
    public void sendMessage(Player player) {
        Pattern pattern;
        Matcher matcher;
        String title;
        String subTitle;
        int fadeIn;
        int stay;
        int fadeOut;
        pattern = Pattern.compile(titlePattern);
        matcher = pattern.matcher(message);
        title = matcher.find() ? matcher.group(1).trim() : "";
        pattern = Pattern.compile(subtitlePattern);
        matcher = pattern.matcher(message);
        subTitle = matcher.find() ? matcher.group(1).trim() : "";
        pattern = Pattern.compile(fadeinPattern);
        matcher = pattern.matcher(message);
        fadeIn = matcher.find() ? Integer.parseInt(matcher.group(1).trim()) : 20;
        pattern = Pattern.compile(stayPattern);
        matcher = pattern.matcher(message);
        stay = matcher.find() ? Integer.parseInt(matcher.group(1).trim()) : 40;
        pattern = Pattern.compile(fadeoutPattern);
        matcher = pattern.matcher(message);
        fadeOut = matcher.find() ? Integer.parseInt(matcher.group(1).trim()) : 20;
        player.sendTitle(HexUtils.colorify(title), HexUtils.colorify(subTitle), fadeIn, stay, fadeOut);
    }
}