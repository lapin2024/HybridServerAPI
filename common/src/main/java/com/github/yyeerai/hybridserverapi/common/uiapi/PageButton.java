package com.github.yyeerai.hybridserverapi.common.uiapi;

import lombok.Getter;

import java.util.Map;

/**
 * 页数按钮类
 * 用于处理页数按钮相关的操作
 */
@Getter
public class PageButton {

    private final Map<Integer, Button> buttonMap; // 按钮映射, key为按钮的位置 slot , value为按钮对象 Button

    public PageButton(Map<Integer, Button> buttonMap) {
        this.buttonMap = buttonMap;
    }
}