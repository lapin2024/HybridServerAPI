package com.github.yyeerai.hybridserverapi.v1_20_1.api.forgelistener;



import net.minecraftforge.eventbus.api.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解监听器方法
 * 注解value为监听的事件类型的class
 * 优先级默认为NORMAL(注意是Forge的优先级,不是Bukkit的)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ForgeEventListener {
    Class<?> value();
    EventPriority priority() default EventPriority.NORMAL;
    boolean receiveCanceled() default false;
}