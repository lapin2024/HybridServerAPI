package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import com.github.yyeerai.hybridserverapi.common.menu.api.MenuUtil;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractRequirementChecker extends MenuUtil implements RequirementChecker {

    public AbstractRequirementChecker(JavaPlugin plugin, String content) {
        super(plugin, content);
    }

    public void initializationParameters(String argument) {
        this.argument = argument;
    }
}