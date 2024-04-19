package com.github.yyeerai.hybridserverapi.common.broadcast;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;

import java.util.*;

/**
 * BossBarManager 是一个管理 BossBar 的工具类。
 * 这个类可以用来添加 BossBar 并在指定时间后移除。
 * 当一个 BossBar 移除后，会自动播放队列中的下一个 BossBar。
 * 这个类是线程安全的。
 * 可以实现一个排队播放 BossBar 的功能。
 */
public class BossBarManager {

    private final Map<BossBar, Integer> bossBarMap = Collections.synchronizedMap(new LinkedHashMap<>());
    private BossBar currentBossBar = null;

    /**
     * 添加一个 BossBar 到队列中。
     *
     * @param bossBar BossBar 实例
     * @param time    BossBar 显示的时间，单位为 Tick
     */
    public synchronized void addBossBar(BossBar bossBar, int time) {
        bossBarMap.put(bossBar, time);
        playNextBossBar();
    }

    /**
     * 播放队列中的下一个 BossBar。
     * 如果当前有 BossBar 正在显示，则不会播放下一个 BossBar。
     */
    private synchronized void playNextBossBar() {
        if (currentBossBar == null || !currentBossBar.isVisible()) {
            Map<BossBar, Integer> copy = new LinkedHashMap<>(bossBarMap);
            for (Map.Entry<BossBar, Integer> bossBarIntegerEntry :  copy.entrySet()) {
                currentBossBar = bossBarIntegerEntry.getKey();
                int time = bossBarIntegerEntry.getValue();
                currentBossBar.setVisible(true);
                Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HybridServerAPI")), () -> {
                    currentBossBar.setVisible(false);
                    currentBossBar.removeAll();
                    bossBarMap.remove(currentBossBar);
                    currentBossBar = null;
                    playNextBossBar();
                }, time);
            }
        }
    }
}