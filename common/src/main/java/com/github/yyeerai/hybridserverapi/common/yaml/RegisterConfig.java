package com.github.yyeerai.hybridserverapi.common.yaml;

import org.bukkit.plugin.java.JavaPlugin;

public class RegisterConfig {

    /**
     * 注册配置文件，并从指定类中加载默认值
     *
     * @param plugin          插件
     * @param filePath        文件路径
     * @param createOrRelease 如果文件不存在是否创建（如果为否则从插件路径里面释放）
     * @param clazz           默认值类
     * @return 配置文件管理器
     */
    public static ConfigManager registerConfig(JavaPlugin plugin, String filePath, boolean createOrRelease, Class<?> clazz) {
        ConfigManager configManager = new ConfigManager(plugin, filePath, createOrRelease);
        configManager.loadConfig(clazz);
        return configManager;
    }

    /**
     * 注册配置文件
     *
     * @param plugin          插件
     * @param filePath        文件路径
     * @param createOrRelease 如果文件不存在是否创建（如果为否则从插件路径里面释放）
     * @return 配置文件管理器
     */
    public static ConfigManager registerConfig(JavaPlugin plugin, String filePath, boolean createOrRelease) {
        return new ConfigManager(plugin, filePath, createOrRelease);
    }

    /**
     * 注册配置文件
     * 从指定位置加载已经存在的yaml文件
     * 不存在则抛出异常
     * 不推荐使用
     *
     * @param plugin   插件
     * @param filePath 文件路径
     * @return 配置文件管理器
     */
    @Deprecated
    public static ConfigManager registerConfig(JavaPlugin plugin, String filePath) {
        return new ConfigManager(plugin, filePath);
    }

    /**
     * 注册配置文件
     * 从指定位置加载已经存在的yaml文件或者创建新文件
     * 并指定是否使用自动更新
     *
     * @param plugin          插件
     * @param filePath        文件路径
     * @param createOrRelease 如果文件不存在是否创建（如果为否则从插件路径里面释放）
     * @param autoUpdate      是否自动更新
     * @return 配置文件管理器
     */
    public static ConfigManager registerConfig(JavaPlugin plugin, String filePath, boolean createOrRelease, boolean autoUpdate) {
        return new ConfigManager(plugin, filePath, createOrRelease, autoUpdate);
    }


}