package com.github.yyeerai.hybridserverapi.common.menu.sound;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class MenuSound {

    private final String type;
    private final float volume;
    private final float pitch;

    public MenuSound(String type, float volume, float pitch) {
        this.type = type;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void play(Player player) {
        if (type == null || type.isEmpty()) return;
        try {
            player.playSound(player.getLocation(), type, SoundCategory.AMBIENT, volume, pitch);
        } catch (Exception e) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.AMBIENT, volume, pitch);
        }
    }
}