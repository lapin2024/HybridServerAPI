package com.github.yyeerai.hybridserverapi.common.broadcast;

import com.github.yyeerai.hybridserverapi.common.util.PluginMethods;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JsonBroadcast extends AbstractBroadcast {
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
        BaseComponent[] baseComponents = PluginMethods.parseJsonMessage(message);
        Bukkit.spigot().broadcast(baseComponents);
    }

    @Override
    public void broadcast(Player player) {
        broadcast();
    }

    @Override
    public void sendMessage(Player player) {
        BaseComponent[] baseComponents = PluginMethods.parseJsonMessage(message);
        player.spigot().sendMessage(baseComponents);
    }
}