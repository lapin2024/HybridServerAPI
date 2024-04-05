package com.github.yyeerai.hybridserverapi.common.menu.action;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Server extends AbstractActionExecutor {
    private final String serverName;

    public Server(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(7);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        serverName = trim.split(",")[0].trim();
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> connect(player), delay);
        } else {
            connect(player);
        }
    }

    private void connect(Player player) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(serverName);
            player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}