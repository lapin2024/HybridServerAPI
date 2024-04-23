package com.github.yyeerai.hybridserverapi.common.broadcast;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Chat extends AbstractBroadcast {
    /**
     * 构造一个新的 AbstractBroadcast 实例。
     *
     * @param message 广播的消息内容
     */
    public Chat(String message) {
        super(message);
    }

    /**
     * 广播方法，用于发送广播消息。
     */
    @Override
    public void broadcast() {
        Bukkit.broadcastMessage(HexUtils.colorify(message));
    }

    /**
     * 发送消息给玩家。
     *
     * @param player 要发送消息的玩家
     */
    @Override
    public void sendMessage(Player player) {
        player.sendMessage(HexUtils.colorify(message));
    }

    @Override
    public void broadcast(Player player) {
        broadcast();
    }
}