package com.github.yyeerai.hybridserverapi.common.yaml;

import com.github.yyeerai.hybridserverapi.common.serializeitem.ItemUtil;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Data
public class BukkitItemStack {

    private final String type;
    private final int amount;
    private final short damage;
    private final String nbt;

    public BukkitItemStack(String type, int amount, short damage, String nbt) {
        this.type = type;
        this.amount = amount;
        this.damage = damage;
        this.nbt = nbt;
    }

    public BukkitItemStack(Map<String, Object> args) {
        this.type = (String) args.get("type");
        this.amount = (int) args.getOrDefault("amount", 1);
        this.damage = (short) args.getOrDefault("damage", 0);
        this.nbt = (String) args.get("nbt");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("amount", amount);
        map.put("damage", damage);
        map.put("nbt", nbt);
        return map;
    }

    public ItemStack toItemStack() {
        return ItemUtil.translateColor(ItemUtil.deserializeItemStack(serialize()), false);
    }

    public static BukkitItemStack fromItemStack(ItemStack itemStack) {
        return new BukkitItemStack(ItemUtil.serializeItemStack(itemStack));
    }

}