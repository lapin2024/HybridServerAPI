package com.github.yyeerai.hybridserverapi.common.menu.button;

import com.github.yyeerai.hybridserverapi.common.menu.requirement.RequirementChecker;

import java.util.List;
import java.util.Map;

public class ButtonBuilder {
    private List<Integer> slots;
    private Map<String, Object> icon; //按钮图标
    private List<RequirementChecker> viewRequirements;
    private String argument;
    private String catchArgument;
    private int refreshDelay; //刷新延迟
    private int priority; //优先级
    private ButtonAction buttonAction; //按钮动作

    public ButtonBuilder setSlots(List<Integer> slots) {
        this.slots = slots;
        return this;
    }

    public ButtonBuilder setIcon(Map<String, Object> icon) {
        this.icon = icon;
        return this;
    }

    public ButtonBuilder setViewRequirements(List<RequirementChecker> viewRequirements) {
        this.viewRequirements = viewRequirements;
        return this;
    }


    public ButtonBuilder setArgument(String argument) {
        this.argument = argument;
        return this;
    }

    public ButtonBuilder setCatchArgument(String catchArgument) {
        this.catchArgument = catchArgument;
        return this;
    }

    public ButtonBuilder setRefreshDelay(int refreshDelay) {
        this.refreshDelay = refreshDelay;
        return this;
    }

    public ButtonBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public ButtonBuilder setButtonAction(ButtonAction buttonAction) {
        this.buttonAction = buttonAction;
        return this;
    }

    public Button build() {
        Button button = new Button();
        button.setSlots(slots);
        button.setIcon(icon);
        button.setViewRequirements(viewRequirements);
        button.setArgument(argument);
        button.setCatchArgument(catchArgument);
        button.setRefreshDelay(refreshDelay);
        button.setPriority(priority);
        button.setButtonAction(buttonAction);
        return button;
    }
}