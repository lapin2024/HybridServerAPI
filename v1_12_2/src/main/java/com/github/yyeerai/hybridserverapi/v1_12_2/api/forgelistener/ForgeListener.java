package com.github.yyeerai.hybridserverapi.v1_12_2.api.forgelistener;


import lombok.Getter;
import net.minecraftforge.fml.common.eventhandler.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

/**
 * @param <E>
 * @author lapin_boy
 * 实现统一的forge事件注册监听,不依赖具体服务端
 * 可以做到任何混合端(指用了bukkit+forge的服务端)都可以使用本插件
 */
public class ForgeListener<E extends Event> implements IEventListener {
    private static Field busId;
    private final EventBus bus;
    @Getter
    private final Class<E> eventClass;
    @Getter
    private final Consumer<E> consumer;
    private final EventPriority priority;

    /**
     * 注册forge事件构造器
     *
     * @param eventClass 事件类
     * @param consumer   事件处理器
     * @param priority   事件优先级
     */
    protected ForgeListener(EventBus bus, Class<E> eventClass, Consumer<E> consumer, EventPriority priority) {
        this.bus = bus;
        this.eventClass = eventClass;
        this.consumer = consumer;
        this.priority = priority;
    }

    /**
     * 获得事件总线id
     *
     * @param bus 事件总线
     * @return 事件总线id
     */
    private static int getBusId(EventBus bus) {
        try {
            if (busId == null) {
                busId = EventBus.class.getDeclaredField("busID");
                busId.setAccessible(true);
            }
            return busId.getInt(bus);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得事件优先级
     *
     * @return 事件优先级
     */
    protected EventPriority getPriority() {
        return this.priority;
    }

    /**
     * 注册事件
     */

    protected void register() {
        try {
            Constructor<E> ctr = getEventClass().getConstructor();
            ctr.setAccessible(true);
            Event event = ctr.newInstance();
            event.getListenerList().register(ForgeListener.getBusId(this.bus), this.getPriority(), this);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取消注册事件
     */
    protected void unregister() {
        ListenerList.unregisterAll(ForgeListener.getBusId(this.bus), this);
    }

    /**
     * 事件处理器
     *
     * @param event 事件
     */
    @Override
    public void invoke(Event event) {
        if (this.eventClass.isInstance(event)) {
            this.on(this.eventClass.cast(event));
        }
    }

    /**
     * 事件处理器
     *
     * @param event 事件
     */
    private void on(E event) {

        this.consumer.accept(event);
    }


}