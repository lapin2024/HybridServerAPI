package com.github.yyeerai.hybridserverapi.common.broadcast;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionBar extends AbstractBroadcast {
    /**
     * 构造一个新的 AbstractBroadcast 实例。
     *
     * @param message 广播的消息内容
     */
    public ActionBar(String message) {
        super(message);
    }

    @Override
    public void broadcast() {
        Bukkit.getOnlinePlayers().forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(HexUtils.colorify(message))));
    }

    @Override
    public void sendMessage(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(HexUtils.colorify(message)));
    }

    @Override
    public void broadcast(Player player) {
        broadcast();
    }
}