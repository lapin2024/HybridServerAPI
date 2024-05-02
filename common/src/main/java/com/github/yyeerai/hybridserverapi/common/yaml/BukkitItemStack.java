package com.github.yyeerai.hybridserverapi.common.yaml;

import com.github.yyeerai.hybridserverapi.common.serializeitem.ItemUtil;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BukkitItemStack {

    private final String type;
    private final String name;
    private final List<String> lore;
    private final int amount;
    private final short damage;
    private final String nbt;


    public BukkitItemStack(String type, String name, List<String> lore, int amount, short damage, String nbt) {
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.amount = amount;
        this.damage = damage;
        this.nbt = nbt;
    }

    public BukkitItemStack(Map<String, Object> args) {
        this.type = (String) args.get("type");
        this.name = (String) args.getOrDefault("name", "");
        this.lore = (List<String>) args.getOrDefault("lore", new ArrayList<>());
        this.amount = (int) args.getOrDefault("amount", 1);
        Object damageObj = args.getOrDefault("damage", 0);
        this.damage = damageObj == null ? 0 : ((Number) damageObj).shortValue();
        this.nbt = (String) args.get("nbt");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("name", name);
        map.put("lore", lore);
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