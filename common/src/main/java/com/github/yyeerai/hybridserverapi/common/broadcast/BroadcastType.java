package com.github.yyeerai.hybridserverapi.common.broadcast;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * BroadcastType 是一个枚举类，用于定义不同的广播类型。
 * 它还包含一个静态的映射，用于将广播类型映射到相应的广播类。
 */
public enum BroadcastType {
    TITLE,
    ACTIONBAR,
    CHAT,
    BOSSBAR,
    POKENOTICE,
    JSON;

    // 映射广播类型到相应的广播类
    public static final Map<BroadcastType, Class<? extends AbstractBroadcast>> BROADCAST_TYPE_CLASS_MAP = new HashMap<>();

    /**
     * 从字符串中获取对应的广播类型。
     * 如果找不到对应的类型，返回一个空的 Optional。
     */
    public static Optional<BroadcastType> fromString(String type) {
        for (BroadcastType value : BroadcastType.values()) {
            if (value.name().equalsIgnoreCase(type)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * 注册一个新的广播类型到对应的广播类。
     */
    public static void registerBroadcast(BroadcastType broadcastType, Class<? extends AbstractBroadcast> clazz) {
        BROADCAST_TYPE_CLASS_MAP.put(broadcastType, clazz);
    }

    /**
     * 从映射中移除一个广播类型。
     */
    public static void unregisterBroadcast(BroadcastType type) {
        BROADCAST_TYPE_CLASS_MAP.remove(type);
    }

    /**
     * 创建一个新的广播实例。
     * 如果对应的广播类没有被注册，将会抛出一个运行时异常。
     */
    public AbstractBroadcast createBroadcast(String message) {
        try {
            return getBroadcastClass().orElseThrow(() -> new RuntimeException("广播类型未注册"))
                    .getDeclaredConstructor(String.class).newInstance(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前广播类型对应的广播类。
     * 如果没有找到对应的类，返回一个空的 Optional。
     */
    private Optional<Class<? extends AbstractBroadcast>> getBroadcastClass() {
        return Optional.ofNullable(BROADCAST_TYPE_CLASS_MAP.get(this));
    }

}