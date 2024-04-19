package com.github.yyeerai.hybridserverapi.v1_20_2.api.broadcast;

import com.github.yyeerai.hybridserverapi.v1_20_2.api.BaseApi;
import com.pixelmonmod.pixelmon.api.overlay.notice.NoticeOverlay;
import org.bukkit.Bukkit;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class PokeNoticeManger {

    private final Map<PokeNotice, Integer> pokeNoticeMap = new ConcurrentHashMap<>();
    private PokeNotice currentPokeNotice = null;

    public synchronized void addPokeNotice(PokeNotice pokeNotice, int time) {
        pokeNoticeMap.put(pokeNotice, time);
        playNextPokeNotice();
    }

    private synchronized void playNextPokeNotice() {
        if (currentPokeNotice == null && !pokeNoticeMap.isEmpty()) {
            Iterator<Map.Entry<PokeNotice, Integer>> iterator = pokeNoticeMap.entrySet().iterator();
            Map.Entry<PokeNotice, Integer> entry = iterator.next();
            currentPokeNotice = entry.getKey();
            int time = entry.getValue();
            iterator.remove();
            currentPokeNotice.playPokemonNotice();
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HybridServerAPI")), () -> {
                Bukkit.getOnlinePlayers().forEach(p -> NoticeOverlay.hide( BaseApi.getMinecraftPlayer(p)));
                currentPokeNotice = null;
                playNextPokeNotice();
            }, time);
        }
    }
}