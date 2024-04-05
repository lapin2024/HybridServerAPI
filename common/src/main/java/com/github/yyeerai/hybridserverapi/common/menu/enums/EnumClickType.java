package com.github.yyeerai.hybridserverapi.common.menu.enums;

import java.util.Optional;

public enum EnumClickType {
    DEFAULT("default"),
    LEFT("left"),
    RIGHT("right"),
    MIDDLE("middle"),
    SHIFT_LEFT("shift_left"),
    SHIFT_RIGHT("shift_right");

    private final String name;

    EnumClickType(String name) {
        this.name = name;
    }

    public static Optional<EnumClickType> getClickTypeByName(String name) {
        for (EnumClickType clickType : EnumClickType.values()) {
            if (clickType.name.equals(name)) {
                return Optional.of(clickType);
            }
        }
        return Optional.empty();
    }

    public static EnumClickType getClickType(String name) {
        switch (name) {
            case "LEFT":
                return LEFT;
            case "RIGHT":
                return RIGHT;
            case "MIDDLE":
                return MIDDLE;
            case "SHIFT_LEFT":
                return SHIFT_LEFT;
            case "SHIFT_RIGHT":
                return SHIFT_RIGHT;
            case "DEFAULT":
                return DEFAULT;
            default:
                return null;
        }
    }

}