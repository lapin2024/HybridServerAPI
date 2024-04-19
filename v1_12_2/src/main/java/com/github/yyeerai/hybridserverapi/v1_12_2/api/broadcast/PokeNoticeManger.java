package com.github.yyeerai.hybridserverapi.v1_12_2.api.broadcast;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.Queue;

public class PokeNoticeManger {

    private static PokeNoticeManger instance;

    private final Queue<PokeNotice> pokeNoticeQueue = new LinkedList<>();
    private PokeNotice currentPokeNotice = null;

    private PokeNoticeManger() {}

    public static synchronized PokeNoticeManger getInstance() {
        if (instance == null) {
            instance = new PokeNoticeManger();
        }
        return instance;
    }

    public synchronized void addPokeNotice(PokeNotice pokeNotice) {
        pokeNoticeQueue.add(pokeNotice);
        playNextPokeNotice();
    }

    private synchronized void playNextPokeNotice() {
        if (currentPokeNotice == null || !currentPokeNotice.isVisible()) {
            currentPokeNotice = pokeNoticeQueue.poll();
            if (currentPokeNotice == null) {
                return;
            }
            int time = currentPokeNotice.getTime();
            currentPokeNotice.setVisibility(true);
            Plugin plugin = Bukkit.getPluginManager().getPlugin("HybridServerAPI");
            if (plugin != null) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    currentPokeNotice.setVisibility(false);
                    playNextPokeNotice();
                }, time);
            }
        }
    }
}