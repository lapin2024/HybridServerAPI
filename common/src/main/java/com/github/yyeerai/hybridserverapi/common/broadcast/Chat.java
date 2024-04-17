package com.github.yyeerai.hybridserverapi.common.broadcast;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
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
    public void broadcast(Player player) {
        String m = player != null ? PlaceholderAPI.setPlaceholders(player, message.replace("%player%", player.getName())) : message;
        Bukkit.broadcastMessage(HexUtils.colorify(m));
    }
}