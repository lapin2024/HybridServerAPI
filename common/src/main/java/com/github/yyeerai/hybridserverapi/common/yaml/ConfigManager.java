package com.github.yyeerai.hybridserverapi.common.yaml;

import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.dvs.versioning.BasicVersioning;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.dumper.DumperSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.general.GeneralSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.loader.LoaderSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.updater.UpdaterSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.common.FlowStyle;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置管理器类，用于处理配置文件的加载、保存和更新。
 */
@SuppressWarnings("unused")
public class ConfigManager {

    private final JavaPlugin plugin;
    @Getter
    private final YamlDocument config;
    private final File file;

    /**
     * 构造函数，用于初始化配置管理器。
     *
     * @param plugin          插件实例
     * @param filePath        配置文件路径
     * @param createOrRelease 如果配置文件不存在，是否创建新文件
     */
    public ConfigManager(JavaPlugin plugin, String filePath, boolean createOrRelease) {
        file = new File(plugin.getDataFolder(), filePath);
        this.plugin = plugin;
        if (!file.exists() && createOrRelease) {
            try {
                if (file.createNewFile()) {
                    plugin.getLogger().info("File " + filePath + " has been created");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (!file.exists() && !createOrRelease) {
            plugin.saveResource(filePath, false);
        }
        try {
            config = YamlDocument.create(file, GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).setSerializer(ItemSerializer.getInstance()).build(), LoaderSettings.DEFAULT, DumperSettings.builder().setFlowStyle(FlowStyle.BLOCK).build(), UpdaterSettings.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public ConfigManager(JavaPlugin plugin, String filePath, boolean createOrRelease, boolean autoUpdate) {
        file = new File(plugin.getDataFolder(), filePath);
        this.plugin = plugin;
        if (!file.exists()) {
            if (createOrRelease) {
                if (file.createNewFile()) {
                    plugin.getLogger().info("File " + filePath + " has been created");
                }
            } else {
                plugin.saveResource(filePath, false);
            }
        }
        if (autoUpdate) {
            config = YamlDocument.create(file, GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).setSerializer(ItemSerializer.getInstance()).build(), LoaderSettings.DEFAULT, DumperSettings.builder().setFlowStyle(FlowStyle.BLOCK).build(), UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } else {
            config = YamlDocument.create(file, GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).setSerializer(ItemSerializer.getInstance()).build(), LoaderSettings.DEFAULT, DumperSettings.builder().setFlowStyle(FlowStyle.BLOCK).build(), UpdaterSettings.DEFAULT);
        }
    }

    /**
     * 构造函数，用于初始化配置管理器。
     *
     * @param plugin   插件实例
     * @param filePath 配置文件路径
     */
    @Deprecated
    public ConfigManager(JavaPlugin plugin, String filePath) {
        file = new File(plugin.getDataFolder(), filePath);
        this.plugin = plugin;
        if (file.exists()) {
            try {
                config = YamlDocument.create(file, GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).setSerializer(ItemSerializer.getInstance()).build(), LoaderSettings.DEFAULT, DumperSettings.builder().setFlowStyle(FlowStyle.BLOCK).build(), UpdaterSettings.DEFAULT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("文件 " + filePath + " 不在列表中,将返回空的config!");
        }
    }

    /**
     * 加载配置文件，并将配置值设置到给定的对象的字段中。
     *
     * @param obj 需要加载配置的对象
     */
    public void loadConfig(Object obj) {
        // 获取对象的类，如果对象是Class类型，则直接使用，否则获取对象的类
        Class<?> clazz = (obj instanceof Class) ? (Class<?>) obj : obj.getClass();
        // 获取类的所有字段
        Field[] fields = clazz.getDeclaredFields();
        // 遍历所有字段
        for (Field field : fields) {
            // 检查字段是否有Config注解
            if (field.isAnnotationPresent(Config.class)) {
                // 获取字段的Config注解
                Config annotation = field.getAnnotation(Config.class);
                // 获取注解的值，如果值为空，则使用字段的名称作为路径
                String path = annotation.value().isEmpty() ? field.getName() : annotation.value();
                // 设置字段为可访问
                field.setAccessible(true);
                // 从配置文件中获取字段对应路径的值
                Object value = config.get(path);
                // 如果值为空
                if (value == null) {
                    // 根据字段的类型获取默认值
                    value = annotation.isList() ? getList(field) : annotation.isItem() ? getItem(field) : getValue(field);
                }
                try {
                    // 将值设置到对象的字段中
                    field.set(obj, value);
                } catch (IllegalAccessException e) {
                    // 如果设置字段值时发生错误，抛出运行时异常
                    throw new RuntimeException("Could not set field " + field.getName() + " in " + clazz.getName());
                }
            }
        }
    }

    /**
     * 重新加载配置文件，并更新给定对象的字段值。
     *
     * @param o 需要更新的对象
     */
    public void reloadConfig(Object o) {
        try {
            if (config.reload()) {
                if (o != null) {
                    loadConfig(o);
                    plugin.getLogger().info("File " + file.getName() + " has been reloaded");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存配置文件。
     */
    public void saveConfig() {
        try {
            config.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取给定字段的默认值。
     *
     * @param field 需要获取默认值的字段
     * @return 字段的默认值
     */
    private Object getValue(Field field) {
        Class<?> type = field.getType();
        Type genericType = field.getGenericType();

        Map<Class<?>, Object> defaultValues = new HashMap<>();
        defaultValues.put(int.class, 0);
        defaultValues.put(Integer.class, 0);
        defaultValues.put(boolean.class, false);
        defaultValues.put(Boolean.class, false);
        defaultValues.put(double.class, 0.0);
        defaultValues.put(Double.class, 0.0);
        defaultValues.put(float.class, 0.0f);
        defaultValues.put(Float.class, 0.0f);
        defaultValues.put(short.class, (short) 0);
        defaultValues.put(Short.class, (short) 0);
        defaultValues.put(long.class, 0L);
        defaultValues.put(Long.class, 0L);
        defaultValues.put(byte.class, (byte) 0);
        defaultValues.put(Byte.class, (byte) 0);
        defaultValues.put(String.class, "");
        if (defaultValues.containsKey(type)) {
            return defaultValues.get(type);
        }
        return null;
    }

    private List<?> getList(Field field) {
        Class<?> type = field.getType();
        Type genericType = field.getGenericType();
        if (type == List.class && genericType instanceof ParameterizedType) {
            Map<Class<?>, List<?>> listValues = new HashMap<>();
            listValues.put(String.class, new ArrayList<String>());
            listValues.put(Integer.class, new ArrayList<Integer>());
            listValues.put(Double.class, new ArrayList<Double>());
            listValues.put(Short.class, new ArrayList<Short>());
            listValues.put(Long.class, new ArrayList<Long>());
            listValues.put(Float.class, new ArrayList<Float>());
            listValues.put(Byte.class, new ArrayList<Byte>());
            listValues.put(Boolean.class, new ArrayList<Boolean>());

            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0 && actualTypeArguments[0] instanceof Class<?>) {
                Class<?> actualTypeArgumentClass = (Class<?>) actualTypeArguments[0];
                if (listValues.containsKey(actualTypeArgumentClass)) {
                    return listValues.get(actualTypeArgumentClass);
                }
            }
            return new ArrayList<>();
        }
        return null;
    }

    private Object getItem(Field field) {
        return new BukkitItemStack("air", " ", new ArrayList<>(),  1, (short) 0, "");
    }

}