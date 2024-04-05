package com.github.yyeerai.hybridserverapi.common.broadcast;

import lombok.Getter;

/**
 * AbstractBroadcast 是一个抽象类，它实现了 IBroadcast 接口。
 * 这个类定义了一个广播的基本结构，包含一个受保护的消息字段。
 * 所有的广播类都应该继承这个类，并实现自己的逻辑。
 */
@Getter
public abstract class AbstractBroadcast implements IBroadcast {
    // 广播的消息内容
    protected final String message;

    /**
     * 构造一个新的 AbstractBroadcast 实例。
     * @param message 广播的消息内容
     */
    public AbstractBroadcast(String message) {
        this.message = message;
    }
}