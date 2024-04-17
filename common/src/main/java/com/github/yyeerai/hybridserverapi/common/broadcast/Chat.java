package com.github.yyeerai.hybridserverapi.common.broadcast;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Chat extends AbstractBroadcast{
    /**
     * 构造一个新的 AbstractBroadcast 实例。
     *
     * @param message 广播的消息内容
     */
    public Chat(String message) {
        super(message);
    }

    @Override
    public void broadcast() {
        Bukkit.broadcastMessage(HexUtils.colorify(message));
    }

    @Override
    public void sendMessage(Player player) {
        player.sendMessage(HexUtils.colorify(message));
    }
}