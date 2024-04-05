package com.github.yyeerai.hybridserverapi.common.menu.action;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActionExecutorFactory {

    /**
     * -- GETTER --
     * 获取动作执行器构造器映射
     */
    @Getter
    private static final Map<String, Constructor<? extends AbstractActionExecutor>> constructorMap = new HashMap<>();


    /**
     * 注册动作执行器
     *
     * @param name          动作执行器名称
     * @param executorClass 动作执行器类
     */
    public static void registerActionExecutor(String name, Class<? extends AbstractActionExecutor> executorClass) {
        try {
            Constructor<? extends AbstractActionExecutor> constructor = executorClass.getConstructor(JavaPlugin.class, String.class);
            constructorMap.put(name, constructor);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("ActionExecutor class must have a no-args constructor");
        }
    }

    /**
     * 获取动作执行器 通过名称
     *
     * @param plugin       插件实例
     * @param sourceString 动作执行器源字符串
     * @param name         动作执行器名称
     * @return 动作执行器实例
     */
    public static AbstractActionExecutor getActionExecutor(JavaPlugin plugin, String sourceString, String name) {
        if (hasActionExecutor(name)) {
            Constructor<? extends AbstractActionExecutor> constructor = constructorMap.get(name);
            try {
                return constructor.newInstance(plugin, sourceString);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create ActionExecutor instance", e);
            }
        } else {
            return null;
        }
    }

    /**
     * 获取动作执行器 通过源字符串
     *
     * @param plugin       插件实例
     * @param sourceString 动作执行器源字符串
     * @return 动作执行器实例
     */
    public static Optional<AbstractActionExecutor> getActionExecutor(JavaPlugin plugin, String sourceString) {
        //从字符串中提取动作执行器名称
        String name = sourceString.split(" ")[0].replace(":", "");
        return Optional.ofNullable(getActionExecutor(plugin, sourceString, name));
    }

    /**
     * 是否存在动作执行器
     *
     * @param name 动作执行器名称
     * @return 是否存在 true 存在 false 不存在
     */
    public static boolean hasActionExecutor(String name) {
        return constructorMap.containsKey(name);
    }

    /**
     * 注销动作执行器
     *
     * @param name 动作执行器名称
     */
    public static void unregisterActionExecutor(String name) {
        constructorMap.remove(name);
    }

    /**
     * 清空动作执行器
     */
    public static void unregisterAllActionExecutors() {
        constructorMap.clear();
    }


}