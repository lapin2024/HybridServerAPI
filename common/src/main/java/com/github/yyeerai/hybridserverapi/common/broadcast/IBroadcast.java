package com.github.yyeerai.hybridserverapi.common.broadcast;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/**
 * IBroadcast 是一个接口，定义了广播的基本行为。
 * 所有的广播类都应该实现这个接口，并提供自己的广播逻辑。
 */
public interface IBroadcast {

    /**
     * 广播方法，用于发送广播消息。
     * 具体的广播逻辑应由实现类提供。
     */
    void broadcast(@Nullable Player player);
}