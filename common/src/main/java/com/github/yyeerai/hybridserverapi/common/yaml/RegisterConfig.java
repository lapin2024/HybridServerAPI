package com.github.yyeerai.hybridserverapi.common.yaml;

import org.bukkit.plugin.java.JavaPlugin;

public class RegisterConfig {

    /**
     * 注册配置文件，并从指定类中加载默认值
     *
     * @param plugin            插件
     * @param filePath          文件路径
     * @param createIfNotExists 如果文件不存在是否创建（如果为否则从插件路径里面释放）
     * @param clazz             默认值类
     * @return 配置文件管理器
     */
    public static ConfigManager registerConfig(JavaPlugin plugin, String filePath, boolean createIfNotExists, Class<?> clazz) {
        ConfigManager configManager = new ConfigManager(plugin, filePath, createIfNotExists);
        configManager.loadConfig(clazz);
        return configManager;
    }

    /**
     * 注册配置文件
     *
     * @param plugin            插件
     * @param filePath          文件路径
     * @param createIfNotExists 如果文件不存在是否创建（如果为否则从插件路径里面释放）
     * @return 配置文件管理器
     */
    public static ConfigManager registerConfig(JavaPlugin plugin, String filePath, boolean createIfNotExists) {
        return new ConfigManager(plugin, filePath, createIfNotExists);
    }

    public static ConfigManager registerConfig(JavaPlugin plugin, String filePath) {
        return new ConfigManager(plugin, filePath);
    }

}