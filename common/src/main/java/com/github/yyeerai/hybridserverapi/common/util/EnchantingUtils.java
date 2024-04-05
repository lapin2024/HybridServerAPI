package com.github.yyeerai.hybridserverapi.common.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class EnchantingUtils {

    private static Object randomSource;
    private static Method method_EnchantmentManager_enchantItem;
    private static Method method_CraftItemStack_asNMSCopy;
    private static Method method_CraftItemStack_asBukkitCopy;
    static {
        try {
            if (NMSUtil.getVersionNumber() < 17) { // 1.16.5
                Class<?> class_EnchantmentManager = Class.forName("net.minecraft.server." + NMSUtil.getVersion() + ".EnchantmentManager");
                Class<?> class_ItemStack = Class.forName("net.minecraft.server." + NMSUtil.getVersion() + ".ItemStack");
                Class<?> class_CraftItemStack = Class.forName("org.bukkit.craftbukkit." + NMSUtil.getVersion() + ".inventory.CraftItemStack");
                randomSource = new Random();
                method_EnchantmentManager_enchantItem = ReflectionUtils.getMethodByName(class_EnchantmentManager, "a", Random.class, class_ItemStack, int.class, boolean.class);
                method_CraftItemStack_asNMSCopy = ReflectionUtils.getMethodByName(class_CraftItemStack, "asNMSCopy", ItemStack.class);
                method_CraftItemStack_asBukkitCopy = ReflectionUtils.getMethodByName(class_CraftItemStack, "asBukkitCopy", class_ItemStack);
            } else if (NMSUtil.getVersionNumber() < 19) { // 1.17+
                Class<?> class_EnchantmentManager = Class.forName("net.minecraft.world.item.enchantment.EnchantmentManager");
                Class<?> class_ItemStack = Class.forName("net.minecraft.world.item.ItemStack");
                Class<?> class_CraftItemStack = Class.forName("org.bukkit.craftbukkit." + NMSUtil.getVersion() + ".inventory.CraftItemStack");
                randomSource = new Random();
                method_EnchantmentManager_enchantItem = ReflectionUtils.getMethodByName(class_EnchantmentManager, "a", Random.class, class_ItemStack, int.class, boolean.class);
                method_CraftItemStack_asNMSCopy = ReflectionUtils.getMethodByName(class_CraftItemStack, "asNMSCopy", ItemStack.class);
                method_CraftItemStack_asBukkitCopy = ReflectionUtils.getMethodByName(class_CraftItemStack, "asBukkitCopy", class_ItemStack);
            } else { // 1.19+
                Class<?> class_RandomSource = Class.forName("net.minecraft.util.RandomSource");
                Class<?> class_EnchantmentManager = Class.forName("net.minecraft.world.item.enchantment.EnchantmentManager");
                Class<?> class_ItemStack = Class.forName("net.minecraft.world.item.ItemStack");
                Class<?> class_CraftItemStack = Class.forName("org.bukkit.craftbukkit." + NMSUtil.getVersion() + ".inventory.CraftItemStack");
                randomSource = ReflectionUtils.getMethodByName(class_RandomSource, "a").invoke(null);
                method_EnchantmentManager_enchantItem = ReflectionUtils.getMethodByName(class_EnchantmentManager, "a", class_RandomSource, class_ItemStack, int.class, boolean.class);
                method_CraftItemStack_asNMSCopy = ReflectionUtils.getMethodByName(class_CraftItemStack, "asNMSCopy", ItemStack.class);
                method_CraftItemStack_asBukkitCopy = ReflectionUtils.getMethodByName(class_CraftItemStack, "asBukkitCopy", class_ItemStack);
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private EnchantingUtils() {

    }

    /**
     * 使用原版逻辑随机附魔一个物品
     *
     * @param itemStack 要附魔的 ItemStack
     * @param level 附魔的等级（相当于附魔表的等级）
     * @param treasure 是否是宝藏附魔
     * @return 相同的ItemStack
     */
    public static ItemStack randomlyEnchant(ItemStack itemStack, int level, boolean treasure) {
        try {
            // Ensures the enchantments get added as the right material
            if (itemStack.getType() == Material.ENCHANTED_BOOK)
                itemStack.setType(Material.BOOK);

            Object nmsItemStack = method_CraftItemStack_asNMSCopy.invoke(null, itemStack);
            nmsItemStack = method_EnchantmentManager_enchantItem.invoke(null, randomSource, nmsItemStack, level, treasure);
            return (ItemStack) method_CraftItemStack_asBukkitCopy.invoke(null, nmsItemStack);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return itemStack;
        }
    }

   /**
     * 通过其注册键获取附魔
     *
     * @param name 注册的名称
     * @return 魔咒，如果没有找到则返回 null
     */
    public static Enchantment getEnchantmentByName(String name) {
        Enchantment byKey = Enchantment.getByKey(NamespacedKey.fromString(name.toLowerCase()));
        if (byKey != null)
            return byKey;

        return Arrays.stream(Enchantment.values())
                .filter(x -> x.getKey().getKey().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

}