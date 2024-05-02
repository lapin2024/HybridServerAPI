package com.github.yyeerai.hybridserverapi.common.serializeitem;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemUtil {
    private static final String DEBUG_ITEM_NAME = "&7物品出错,请检查设置";
    private static final String MINECRAFT = "MINECRAFT";


    public static ItemStack deserializeItemStack(Map<String, Object> args) {
        return Optional.ofNullable((String) args.get("type"))
                .filter(type -> !type.isEmpty())
                .map(type -> {
                    type = type.toUpperCase();
                    String[] split = type.split(":");
                    if (split.length == 2 && !split[0].equals(MINECRAFT)) {
                        type = split[0] + "_" + split[1];
                    } else if (split.length == 2) {
                        type = split[1];
                    } else {
                        type = split[0];
                    }
                    return Material.getMaterial(type);
                })
                .map(material -> {
                    Object damageObj = args.getOrDefault("damage", 0);
                    int damage = damageObj instanceof Short ? ((Short) damageObj).intValue() : (int) damageObj;
                    int amount = (int) args.getOrDefault("amount", 1);
                    ItemStack itemStack = new ItemStack(material, amount, (short) damage);
                    if (args.containsKey("nbt")) {
                        String nbt = (String) args.get("nbt");
                        if(nbt != null && !nbt.isEmpty()){
                            itemStack = NbtUtil.setNbt(itemStack, nbt);
                        }
                    }
                    return itemStack;
                })
                .map(itemStack -> {
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
                })
                .orElseGet(ItemUtil::getDebugItem);
    }

    public static Map<String, Object> serializeItemStack(ItemStack itemStack) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", itemStack.getType().name());
        map.put("damage", itemStack.getDurability());
        map.put("amount", itemStack.getAmount());
        ItemMeta meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        assert meta != null;
        if (meta.hasDisplayName()) {
            map.put("name", meta.getDisplayName());
        }
        if (meta.hasLore()) {
            map.put("lore", meta.getLore());
        }
        Optional.ofNullable(NbtUtil.getNbt(itemStack))
                .ifPresent(nbt -> map.put("nbt", nbt.toString()));
        return map;
    }

    private static ItemStack getDebugItem() {
        ItemStack itemStack = new ItemStack(Material.STONE);
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
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        if (meta.hasDisplayName()) {
            meta.setDisplayName(HexUtils.colorify(meta.getDisplayName()));
        }
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            assert lore != null;
            List<String> newLore = new ArrayList<>();
            lore.forEach(s -> newLore.add(HexUtils.colorify(s)));
            meta.setLore(newLore);
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