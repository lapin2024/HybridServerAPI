package com.github.yyeerai.hybridserverapi.v1_16_5.api.broadcast;

import com.github.yyeerai.hybridserverapi.v1_16_5.api.BaseApi;
import com.pixelmonmod.pixelmon.api.overlay.notice.NoticeOverlay;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PokeNoticeManger {

    private final Map<PokeNotice, Integer> pokeNoticeMap = Collections.synchronizedMap(new LinkedHashMap<>());
    private PokeNotice currentPokeNotice = null;

    public synchronized void addPokeNotice(PokeNotice pokeNotice, int time) {
        pokeNoticeMap.put(pokeNotice, time);
        playNextPokeNotice();
    }

    private synchronized void playNextPokeNotice() {
        if (currentPokeNotice == null && !pokeNoticeMap.isEmpty()) {
            Map<PokeNotice, Integer> copy = new LinkedHashMap<>(pokeNoticeMap);
            for (Map.Entry<PokeNotice, Integer> pokeNoticeIntegerEntry : copy.entrySet()) {
                currentPokeNotice = pokeNoticeIntegerEntry.getKey();
                int time = pokeNoticeIntegerEntry.getValue();
                currentPokeNotice.playPokemonNotice();
                Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HybridServerAPI")), () -> {
                    currentPokeNotice.hidePokemonNotice();
                    pokeNoticeMap.remove(currentPokeNotice);
                    currentPokeNotice = null;
                    playNextPokeNotice();
                }, time);
            }
        }
    }
}