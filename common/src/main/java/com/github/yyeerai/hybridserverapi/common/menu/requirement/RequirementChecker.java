package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface RequirementChecker {
    /**
     * 检查玩家是否满足条件
     *
     * @param player   玩家
     * @param argument 参数
     * @return 是否满足条件
     */
    boolean meetsRequirements(Player player, @Nullable String argument);

}