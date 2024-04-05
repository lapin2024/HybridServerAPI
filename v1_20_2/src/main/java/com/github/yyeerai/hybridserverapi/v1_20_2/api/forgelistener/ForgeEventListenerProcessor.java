package com.github.yyeerai.hybridserverapi.v1_20_2.api.forgelistener;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"unused", "unchecked"})
public class ForgeEventListenerProcessor {
    private static final List<ForgeListener<?>> registeredListeners = new ArrayList<>();

    /**
     * 注册监听器
     * @param listener 带有@ForgeEventListener注解的监听器
     * @param eventBus 事件总线（forge自己的事件用MinecraftForge.EVENT_BUS，
     *                 其他模组，请使用模组的事件总线）
     * @param <E> 事件类型,必须继承自Event
     */
    public static <E extends Event> void register(Object listener, IEventBus eventBus) {
        Class<?> listenerClass = listener.getClass();
        Method[] methods = listenerClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(ForgeEventListener.class)) {
                ForgeListener<E> forgeListener = geteForgeListener(eventBus,listener, method);
                forgeListener.register();
                registeredListeners.add(forgeListener); // 记录已注册的监听器
            }
        }
    }

    /**
     * 获取监听器
     * @param eventBus 事件总线
     * @param listener 监听器
     * @param method 方法
     * @return 监听器
     * @param <E> 事件类型,必须继承自Event
     */
    @NotNull
    private static <E extends Event> ForgeListener<E> geteForgeListener(IEventBus eventBus, Object listener, Method method) {
        ForgeEventListener annotation = method.getAnnotation(ForgeEventListener.class);
        Class<E> eventClass = (Class<E>) annotation.value();
        EventPriority priority = annotation.priority();
        return new ForgeListener<>(eventBus, eventClass, event -> {
            try {
                method.invoke(listener, event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, priority);
    }

    /**
     * 取消注册监听器
     * @param listener 监听器
     */
    public static void unregister(Object listener) {
        Iterator<ForgeListener<?>> iterator = registeredListeners.iterator();
        while (iterator.hasNext()) {
            ForgeListener<?> forgeListener = iterator.next();
            if (forgeListener.getConsumer() == listener) {
                forgeListener.unregister();
                iterator.remove();
            }
        }
    }

    /**
     * 取消注册所有监听器（这个代表此插件注册的所有forge事件）
     */
    public static void unregisterAll() {
        for (ForgeListener<?> listener : registeredListeners) {
            listener.unregister();
        }
        registeredListeners.clear();
    }
}