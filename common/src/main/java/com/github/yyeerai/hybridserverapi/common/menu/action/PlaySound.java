package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.menu.sound.MenuSound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlaySound extends AbstractActionExecutor {
    private final MenuSound sound;

    public PlaySound(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(10);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        sound = extractSound(trim);
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> playSound(player), delay);
        } else {
            playSound(player);
        }
    }

    private void playSound(Player player) {
        if (sound != null) {
            sound.play(player);
        }
    }

}