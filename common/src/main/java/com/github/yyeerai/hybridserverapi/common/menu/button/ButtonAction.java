package com.github.yyeerai.hybridserverapi.common.menu.button;

import com.github.yyeerai.hybridserverapi.common.menu.action.ActionExecutor;
import com.github.yyeerai.hybridserverapi.common.menu.enums.EnumClickType;
import com.github.yyeerai.hybridserverapi.common.menu.requirement.RequirementChecker;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ButtonAction {
    Map<EnumClickType, List<RequirementChecker>> requirementsMap;
    Map<EnumClickType, List<ActionExecutor>> allowActionExecutorsMap;
    Map<EnumClickType, List<ActionExecutor>> denyActionExecutorsMap;

    public ButtonAction(Map<EnumClickType, List<RequirementChecker>> requirementsMap, Map<EnumClickType, List<ActionExecutor>> allowActionExecutorsMap, Map<EnumClickType, List<ActionExecutor>> denyActionExecutorsMap) {
        this.requirementsMap = requirementsMap;
        this.allowActionExecutorsMap = allowActionExecutorsMap;
        this.denyActionExecutorsMap = denyActionExecutorsMap;
    }

    /**
     * 执行按钮动作
     *
     * @param player        玩家
     * @param clickType     点击类型
     * @param argument      菜单传递的参数
     * @param catchArgument 捕获的参数
     */
    public void execute(Player player, EnumClickType clickType, String argument, String catchArgument) {
        if (!requirementsMap.isEmpty() && requirementsMap.containsKey(clickType) && !meetsRequirements(player, requirementsMap.get(clickType), argument)) {
            if (!denyActionExecutorsMap.isEmpty() && denyActionExecutorsMap.containsKey(clickType)) {
                denyActionExecutorsMap.get(clickType).forEach(actionExecutor -> actionExecutor.execute(player, argument, catchArgument));
            }
        } else {
            if (!allowActionExecutorsMap.isEmpty() && allowActionExecutorsMap.containsKey(clickType)) {
                allowActionExecutorsMap.get(clickType).forEach(actionExecutor -> actionExecutor.execute(player, argument, catchArgument));
            }
        }
    }


    /**
     * 检查玩家是否满足条件
     *
     * @param player       玩家
     * @param requirements 条件
     * @param argument     参数
     * @return 是否满足条件
     */
    private boolean meetsRequirements(Player player, List<RequirementChecker> requirements, String argument) {
        return requirements.stream().allMatch(requirement -> requirement.meetsRequirements(player, argument));
    }

}