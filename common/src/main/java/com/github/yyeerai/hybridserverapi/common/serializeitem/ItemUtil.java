package com.github.yyeerai.hybridserverapi.common.serializeitem;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class ItemUtil {
    private static final String DEBUG_ITEM_NAME = "&7物品出错,请检查设置";
    private static final String MINECRAFT = "MINECRAFT";


    public static ItemStack deserializeItemStack(Map<String, Object> args) {
        String type = (String) args.getOrDefault("type", "AIR");
        String[] split = type.toUpperCase().split(":");
        if (split.length == 2 && !split[0].equals(MINECRAFT)) {
            type = split[0] + "_" + split[1];
        } else if (split.length == 2) {
            type = split[1];
        } else {
            type = split[0];
        }
        if (type.equals("AIR")) {
            return new ItemStack(Material.AIR);
        }
        Material material = Material.getMaterial(type);
        if (material == null) {
            return getDebugItem();
        }
        Object damageObj = args.getOrDefault("damage", 0);
        int damage = damageObj instanceof Short ? ((Short) damageObj).intValue() : (int) damageObj;
        int amount = (int) args.getOrDefault("amount", 1);
        ItemStack itemStack = new ItemStack(material, amount, (short) damage);
        if (args.containsKey("nbt")) {
            String nbt = (String) args.get("nbt");
            if (nbt != null && !nbt.isEmpty()) {
                itemStack = NbtUtil.setNbt(itemStack, nbt);
            }
        }
        ItemMeta meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        assert meta != null;
        if (args.containsKey("name")) {
            meta.setDisplayName((String) args.get("name"));
        }
        if (args.containsKey("lore")) {
            List<String> newLore = new ArrayList<>();
            ((List<?>) args.get("lore")).stream()
                    .filter(o -> o instanceof String)
                    .forEach(o -> newLore.add((String) o));
            meta.setLore(newLore);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static Map<String, Object> serializeItemStack(ItemStack itemStack) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", itemStack.getType().name());
        map.put("damage", itemStack.getDurability());
        map.put("amount", itemStack.getAmount());
        ItemMeta meta = Optional.ofNullable(itemStack.getItemMeta())
                .orElse(Bukkit.getItemFactory().getItemMeta(itemStack.getType()));
        Optional.ofNullable(meta.getDisplayName()).ifPresent(name -> map.put("name", name));
        Optional.ofNullable(meta.getLore()).ifPresent(lore -> map.put("lore", lore));
        Optional.ofNullable(NbtUtil.getNbt(itemStack))
                .ifPresent(nbt -> map.put("nbt", nbt.toString()));
        return map;
    }

    private static ItemStack getDebugItem() {
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', DEBUG_ITEM_NAME));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * 转换物品的颜色
     *
     * @param itemStack 物品
     * @return 转换后的物品, 返回原物品
     */
    private static ItemStack translateColor(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return itemStack;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if (meta.hasDisplayName()) {
            meta.setDisplayName(HexUtils.colorify(meta.getDisplayName()));
        }
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            meta.setLore(lore.stream().map(HexUtils::colorify).collect(Collectors.toList()));
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * 转换物品的颜色
     *
     * @param itemStack 物品
     * @param copy      是否复制
     * @return 转换后的物品, 如果copy为true则返回新物品 否则返回原物品
     */
    public static ItemStack translateColor(ItemStack itemStack, boolean copy) {
        if (copy) {
            itemStack = itemStack.clone();
        }
        return translateColor(itemStack);
    }

}