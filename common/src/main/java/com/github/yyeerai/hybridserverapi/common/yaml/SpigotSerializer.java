package com.github.yyeerai.hybridserverapi.common.yaml;

import dev.dejvokep.boostedyaml.utils.supplier.MapSupplier;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 支持 {@link ConfigurationSerialization} 的自定义序列化为spigot对象。
 */
public class SpigotSerializer extends AbstractCustomSerializer {

    /**
     * Serializer实例。
     */
    private static final SpigotSerializer INSTANCE = new SpigotSerializer();

    /**
     * 所有支持的抽象类。
     */
    private static final Set<Class<?>> SUPPORTED_ABSTRACT_CLASSES = new HashSet<Class<?>>() {{
        add(ConfigurationSerializable.class);
    }};

    /**
     * 所有支持的类。
     */
    private static final Set<Class<?>> SUPPORTED_CLASSES = new HashSet<Class<?>>() {
        {
            add(BukkitItemStack.class);
        }
    };

    /**
     * 返回实例。
     *
     * @return 实例
     */
    public static SpigotSerializer getInstance() {
        return INSTANCE;
    }

    /**
     * 反序列化方法，将Map转换为对象。
     *
     * @param map 包含对象数据的Map
     * @return 反序列化后的对象，如果无法反序列化则返回null
     */
    @Override
    @Nullable
    public Object deserialize(@NotNull Map<Object, Object> map) {
        //如果Map中不包含序列化类型的键
        if (!map.containsKey(ConfigurationSerialization.SERIALIZED_TYPE_KEY) && !map.containsKey("hybridItem")) {
            return null;
        }

        //如果不是有效的类
        if (map.containsKey("hybridItem")) {
            Map<String, Object> itemMap = new HashMap<>();
            map.forEach((key, value) -> itemMap.put(key.toString(), value));
            return new BukkitItemStack(itemMap);
        } else {
            if (ConfigurationSerialization.getClassByAlias(map.get(ConfigurationSerialization.SERIALIZED_TYPE_KEY).toString()) == null) {
                return null;
            }
            //创建一个Map
            Map<String, Object> converted = new HashMap<>();
            //遍历所有条目
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                converted.put(entry.getKey().toString(), entry.getValue());
            }
            return ConfigurationSerialization.deserializeObject(converted);
        }

    }

    /**
     * 序列化方法，将对象转换为Map。
     *
     * @param object   要序列化的对象
     * @param supplier 提供Map的供应商
     * @return 包含序列化对象数据的Map
     */
    @Nullable
    @Override
    public <T> Map<Object, Object> serialize(@NotNull T object, @NotNull MapSupplier supplier) {
        Map<Object, Object> serialized = supplier.supply(1);
        if (object instanceof BukkitItemStack) {
            BukkitItemStack bukkitItemStack = (BukkitItemStack) object;
            serialized.put("hybridItem", true);
            Map<String, Object> stringObjectMap = bukkitItemStack.serialize();
            serialized.putAll(stringObjectMap);
        } else {
            ConfigurationSerializable cast = (ConfigurationSerializable) object;
            serialized.putAll((cast).serialize());
            serialized.computeIfAbsent(ConfigurationSerialization.SERIALIZED_TYPE_KEY, k -> ConfigurationSerialization.getAlias(cast.getClass()));
        }
        return serialized;
    }

    /**
     * 获取支持的类的集合。
     *
     * @return 支持的类的集合
     */
    @NotNull
    @Override
    public Set<Class<?>> getSupportedClasses() {
        return SUPPORTED_CLASSES;
    }

    /**
     * 获取支持的父类的集合。
     *
     * @return 支持的父类的集合
     */
    @NotNull
    @Override
    public Set<Class<?>> getSupportedParentClasses() {
        return SUPPORTED_ABSTRACT_CLASSES;
    }
}