package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequirementCheckerFactory {

    @Getter
    private static final Map<String, Constructor<? extends AbstractRequirementChecker>> constructorMap = new HashMap<>();

    /**
     * 获取需求检查器 通过名称
     *
     * @param name         需求检查器名称
     * @param checkerClass 需求检查器类
     */
    public static void registerRequirementChecker(String name, Class<? extends AbstractRequirementChecker> checkerClass) {
        try {
            Constructor<? extends AbstractRequirementChecker> constructor = checkerClass.getConstructor(JavaPlugin.class, String.class);
            constructorMap.put(name, constructor);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("RequirementChecker class must have a no-args constructor");
        }
    }

    public static AbstractRequirementChecker getRequirementChecker(JavaPlugin plugin, String sourceString, String name) {
        if (hasRequirementChecker(name)) {
            Constructor<? extends AbstractRequirementChecker> constructor = constructorMap.get(name);
            try {
                return constructor.newInstance(plugin, sourceString);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create RequirementChecker instance", e);
            }
        } else {
            return null;
        }
    }

    public static Optional<AbstractRequirementChecker> getRequirementChecker(JavaPlugin plugin, String sourceString) {
        String name = sourceString.split(" ")[0].replace(":", "");
        return Optional.ofNullable(getRequirementChecker(plugin, sourceString, name));
    }

    public static boolean hasRequirementChecker(String name) {
        return constructorMap.containsKey(name);
    }

    public static void unregisterRequirementChecker(String name) {
        constructorMap.remove(name);
    }

    public static void unregisterAllRequirementCheckers() {
        constructorMap.clear();
    }

}