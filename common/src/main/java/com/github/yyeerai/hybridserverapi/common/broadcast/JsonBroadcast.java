package com.github.yyeerai.hybridserverapi.common.broadcast;

import org.bukkit.entity.Player;

public class JsonBroadcast extends AbstractBroadcast{
    /**
     * 构造一个新的 AbstractBroadcast 实例。
     *
     * @param message 广播的消息内容
     */
    public JsonBroadcast(String message) {
        super(message);
    }

    @Override
    public void broadcast() {

    }

    @Override
    public void broadcast(Player player) {

    }

    @Override
    public void sendMessage(Player player) {

    }
}