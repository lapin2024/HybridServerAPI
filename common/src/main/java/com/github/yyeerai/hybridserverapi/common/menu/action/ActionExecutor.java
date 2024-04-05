package com.github.yyeerai.hybridserverapi.common.menu.action;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ActionExecutor {
    /**
     * 执行动作
     *
     * @param player        执行动作的玩家
     * @param argument      菜单传递的参数 可能为null
     * @param catchArgument 捕获器传递的参数 可能为null
     */
    void execute(Player player, @Nullable String argument, @Nullable String catchArgument);
}