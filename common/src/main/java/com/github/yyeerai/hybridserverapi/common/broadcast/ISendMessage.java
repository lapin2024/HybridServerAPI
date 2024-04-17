package com.github.yyeerai.hybridserverapi.common.broadcast;

import org.bukkit.entity.Player;

public interface ISendMessage {
    /**
     * 发送消息给玩家。
     * @param player 要发送消息的玩家
     */
    void sendMessage(Player player);
}