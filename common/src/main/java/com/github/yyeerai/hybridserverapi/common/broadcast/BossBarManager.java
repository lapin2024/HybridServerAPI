package com.github.yyeerai.hybridserverapi.common.broadcast;

import org.bukkit.Bukkit;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * BossBarManager 是一个管理 BossBar 的工具类。
 * 这个类可以用来添加 BossBar 并在指定时间后移除。
 * 当一个 BossBar 移除后，会自动播放队列中的下一个 BossBar。
 * 这个类是线程安全的。
 * 可以实现一个排队播放 BossBar 的功能。
 */
public class BossBarManager {

    private static BossBarManager instance;
    private final Queue<BossBar> bossBarQueue = new LinkedList<>();
    private BossBar currentBossBar = null;

    private BossBarManager() {
    }

    public static BossBarManager getInstance() {
        if (instance == null) {
            instance = new BossBarManager();
        }
        return instance;
    }

    /**
     * 添加一个 BossBar 到队列中。
     *
     * @param bossBar BossBar 实例
     */
    public synchronized void addBossBar(BossBar bossBar) {
        bossBarQueue.add(bossBar);
        playNextBossBar();
    }

    /**
     * 播放队列中的下一个 BossBar。
     * 如果当前有 BossBar 正在显示，则不会播放下一个 BossBar。
     */
    private synchronized void playNextBossBar() {
        if (currentBossBar == null || !currentBossBar.getBossBar().isVisible()) {
            currentBossBar = bossBarQueue.poll();
            if (currentBossBar == null) {
                return;
            }
            int time = currentBossBar.getTime();
            currentBossBar.getBossBar().setVisible(true);
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HybridServerAPI")), () -> {
                currentBossBar.getBossBar().setVisible(false);
                playNextBossBar();
            }, time);
        }
    }
}