package com.github.yyeerai.hybridserverapi.common.serializeitem;

import org.bukkit.inventory.ItemStack;

public interface INbt {
    Object getNbt(ItemStack itemStack);
    ItemStack setNbt(ItemStack itemStack, String nbtSting);
}