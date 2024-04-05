package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.menu.api.MenuUtil;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractActionExecutor extends MenuUtil implements ActionExecutor {


    public int delay;
    public double chance;
    public String catchArgument;

    public AbstractActionExecutor(JavaPlugin plugin, String content) {
        super(plugin, content);
    }

    /**
     * 初始化参数
     *
     * @param argument      菜单参数
     * @param catchArgument 捕获参数
     */
    public void initializationParameters(String argument, String catchArgument) {
        this.argument = argument;
        this.catchArgument = catchArgument;
    }


}