package com.github.yyeerai.hybridserverapi.common.menu.api;

import com.github.yyeerai.hybridserverapi.common.menu.action.JavaScript;
import com.github.yyeerai.hybridserverapi.common.menu.action.*;
import com.github.yyeerai.hybridserverapi.common.menu.listen.MenuListener;
import com.github.yyeerai.hybridserverapi.common.menu.requirement.*;
import org.bukkit.plugin.java.JavaPlugin;

public class RegisterService {

    public static void registerEventSubscriber(JavaPlugin plugin) {
        MenuListener.eventSubscriber(plugin);
    }

    public static void registerActionExecutor() {
        ActionExecutorFactory.registerActionExecutor("broadcast", Broadcast.class);
        ActionExecutorFactory.registerActionExecutor("catchExecutor", CatchExecutor.class);
        ActionExecutorFactory.registerActionExecutor("closeMenu", CloseMenu.class);
        ActionExecutorFactory.registerActionExecutor("consoleCommands", ConsoleCommands.class);
        ActionExecutorFactory.registerActionExecutor("giveItem", GiveItem.class);
        ActionExecutorFactory.registerActionExecutor("giveMoney", GiveMoney.class);
        ActionExecutorFactory.registerActionExecutor("giveNyEconomy", GiveNyEconomy.class);
        ActionExecutorFactory.registerActionExecutor("givePoints", GivePoints.class);
        ActionExecutorFactory.registerActionExecutor("javaScript", JavaScript.class);
        ActionExecutorFactory.registerActionExecutor("message", Message.class);
        ActionExecutorFactory.registerActionExecutor("openMenu", OpenMenu.class);
        ActionExecutorFactory.registerActionExecutor("playerCommands", PlayerCommands.class);
        ActionExecutorFactory.registerActionExecutor("playSound", PlaySound.class);
        ActionExecutorFactory.registerActionExecutor("removeItem", RemoveItem.class);
        ActionExecutorFactory.registerActionExecutor("server", Server.class);
        ActionExecutorFactory.registerActionExecutor("takeMoney", TakeMoney.class);
        ActionExecutorFactory.registerActionExecutor("takeNyEconomy", TakeNyEconomy.class);
        ActionExecutorFactory.registerActionExecutor("takePoints", TakePoints.class);
        ActionExecutorFactory.registerActionExecutor("sendTitle", SendTitle.class);
        ActionExecutorFactory.registerActionExecutor("sendJson", SendJson.class);
        ActionExecutorFactory.registerActionExecutor("broadcastJson", BroadcastJson.class);
    }

    public static void registerRequirementChecker() {
        RequirementCheckerFactory.registerRequirementChecker("hasItem", HasItem.class);
        RequirementCheckerFactory.registerRequirementChecker("javaScript", com.github.yyeerai.hybridserverapi.common.menu.requirement.JavaScript.class);
        RequirementCheckerFactory.registerRequirementChecker("hasMoney", HasMoney.class);
        RequirementCheckerFactory.registerRequirementChecker("hasNyEconomy", HasNyEconomy.class);
        RequirementCheckerFactory.registerRequirementChecker("hasPoints", HasPoints.class);
        RequirementCheckerFactory.registerRequirementChecker("hasPermission", HasPermission.class);
        RequirementCheckerFactory.registerRequirementChecker("stringEquals", StringEquals.class );
        RequirementCheckerFactory.registerRequirementChecker("stringContains", StringContains.class);
        RequirementCheckerFactory.registerRequirementChecker("numberEquals", NumberEquals.class);
        RequirementCheckerFactory.registerRequirementChecker("hasEmptySlot", HasEmptySlot.class);
    }

}