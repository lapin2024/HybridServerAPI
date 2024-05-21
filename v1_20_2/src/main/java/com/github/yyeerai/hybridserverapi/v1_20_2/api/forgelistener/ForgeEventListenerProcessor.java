package com.github.yyeerai.hybridserverapi.v1_20_2.api.forgelistener;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({"unused", "unchecked"})
public class ForgeEventListenerProcessor {
    private static final List<ForgeListener<?>> registeredListeners = Collections.synchronizedList(new ArrayList<>());
    private static final Map<Plugin, List<ForgeListener<?>>> HandlerMap = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(ForgeEventListenerProcessor.class.getName());

    /**
     * 注册监听器
     *
     * @param listener 带有 @ForgeEventListener 注解的监听器对象
     * @param eventBus 事件总线（例如 MinecraftForge.EVENT_BUS ）
     * @param <E>      事件类型，必须继承自 Event
     * @deprecated 这个方法已过时，请使用带 Plugin 参数的方法
     */
    @Deprecated
    public static <E extends Event> void register(Object listener, IEventBus eventBus) {
        register(null, listener, eventBus);
    }

    /**
     * 注册监听器，并关联到特定的插件
     *
     * @param plugin   插件
     * @param listener 带有 @ForgeEventListener 注解的监听器对象
     * @param eventBus 事件总线（例如 MinecraftForge.EVENT_BUS ）
     * @param <E>      事件类型，必须继承自 Event
     */
    public static <E extends Event> void register(Plugin plugin, Object listener, IEventBus eventBus) {
        List<Method> listenerMethods = getAnnotatedMethods(listener.getClass());
        List<ForgeListener<?>> forgeListeners = new ArrayList<>();

        for (Method method : listenerMethods) {
            ForgeListener<E> forgeListener = createForgeListener(eventBus, listener, method);
            forgeListener.register();
            forgeListeners.add(forgeListener);
        }

        if (plugin != null && !forgeListeners.isEmpty()) {
            HandlerMap.computeIfAbsent(plugin, k -> new ArrayList<>()).addAll(forgeListeners);
        }
    }

    /**
     * 获取带有 @ForgeEventListener 注解的方法
     *
     * @param listenerClass 监听器类
     * @return 方法列表
     */
    private static List<Method> getAnnotatedMethods(Class<?> listenerClass) {
        List<Method> methods = new ArrayList<>();
        for (Method method : listenerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ForgeEventListener.class)) {
                methods.add(method);
            }
        }
        return methods;
    }

    /**
     * 创建并返回一个 ForgeListener 对象
     *
     * @param eventBus 事件总线
     * @param listener 监听器对象
     * @param method   带有 @ForgeEventListener 注解的方法
     * @param <E>      事件类型，必须继承自 Event
     * @return ForgeListener 对象
     */
    @NotNull
    private static <E extends Event> ForgeListener<E> createForgeListener(IEventBus eventBus, Object listener, Method method) {
        ForgeEventListener annotation = method.getAnnotation(ForgeEventListener.class);
        Class<E> eventClass = (Class<E>) annotation.value();
        EventPriority priority = annotation.priority();

        return new ForgeListener<>(eventBus, eventClass, event -> {
            try {
                method.invoke(listener, event);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error invoking event listener method", e);
                throw new RuntimeException(e);
            }
        }, priority);
    }

    /**
     * 取消注册指定的监听器
     *
     * @param listener 监听器对象
     */
    public static void unregister(Object listener) {
        synchronized (registeredListeners) {
            registeredListeners.removeIf(forgeListener -> {
                if (forgeListener.getConsumer() == listener) {
                    forgeListener.unregister();
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * 取消注册所有已注册的监听器
     */
    public static void unregisterAll() {
        synchronized (registeredListeners) {
            for (ForgeListener<?> listener : registeredListeners) {
                listener.unregister();
            }
            registeredListeners.clear();
        }
    }

    /**
     * 取消注册指定插件的所有监听器
     *
     * @param plugin 插件
     */
    public static void unregisterAll(Plugin plugin) {
        List<ForgeListener<?>> listeners = HandlerMap.remove(plugin);
        if (listeners != null) {
            for (ForgeListener<?> listener : listeners) {
                listener.unregister();
            }
        }
    }
}