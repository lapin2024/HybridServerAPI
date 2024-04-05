package com.github.yyeerai.hybridserverapi.common.serializeitem;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NbtUtil {

    private static final String serverVersion;
    private static Class<?> craftItemStackClass;
    private static Class<?> nmsItemStackClass;
    private static Class<?> nbtTagCompoundClass;
    private static Class<?> MojangsonParserClass;

    static {
        serverVersion = getServerVersion();
        try {
            switch (serverVersion) {
                case "1.12.2":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack");
                    nmsItemStackClass = Class.forName("net.minecraft.server.v1_12_R1.ItemStack");
                    nbtTagCompoundClass = Class.forName("net.minecraft.server.v1_12_R1.NBTTagCompound");
                    MojangsonParserClass = Class.forName("net.minecraft.server.v1_12_R1.MojangsonParser");
                    break;
                case "1.16.5":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack");
                    nmsItemStackClass = Class.forName("net.minecraft.server.v1_16_R3.ItemStack");
                    nbtTagCompoundClass = Class.forName("net.minecraft.server.v1_16_R3.NBTTagCompound");
                    MojangsonParserClass = Class.forName("net.minecraft.server.v1_16_R3.MojangsonParser");
                    break;
                case "1.17.1":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack");
                    nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                    nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                    MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    break;
                case "1.18.2":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack");
                    nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                    nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                    MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    break;
                case "1.19.4":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack");
                    nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                    nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                    MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    break;
                case "1.20.1":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack");
                    nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                    nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                    MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    break;
                case "1.20.2":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack");
                    nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                    nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                    MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    break;
                case "1.20.4":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack");
                    nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                    nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                    MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    break;
                default:
                    Bukkit.getLogger().warning("未知的服务器版本: " + serverVersion);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("初始化NbtUtil错误" + e);
        }
    }

    private NbtUtil() {

    }

    /**
     * 获得物品的NBT
     *
     * @param itemStack bukkit物品
     * @return NBT
     */
    public static Object getNbt(ItemStack itemStack) {
        try {
            switch (serverVersion) {
                case "1.12.2":
                case "1.16.5":
                case "1.17.1": {
                    Method asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class); //获取asNMSCopy方法
                    Object nmsCopy = asNMSCopy.invoke(null, itemStack); //调用asNMSCopy方法
                    Object nbt = nbtTagCompoundClass.newInstance(); //创建一个NBTTagCompound对象
                    Method save = nmsItemStackClass.getMethod("save", nbtTagCompoundClass); //获取save方法
                    save.invoke(nmsCopy, nbt); //调用save方法
                    return nbt; //返回NBTTagCompound对象
                }
                case "1.18.2":
                case "1.19.4":
                case "1.20.1":
                case "1.20.2":
                case "1.20.4": {
                    Method asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class); //获取asNMSCopy方法
                    Object nmsCopy = asNMSCopy.invoke(null, itemStack); //调用asNMSCopy方法
                    Object nbt = nbtTagCompoundClass.newInstance();
                    Method save = nmsItemStackClass.getMethod("b", nbtTagCompoundClass);
                    save.invoke(nmsCopy, nbt);
                    return nbt;
                }
                default:
                    return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("获得nbt错误" + e);
        }
    }

    /**
     * 给物品设置NBT
     *
     * @param itemStack bukkit物品
     * @param nbtSting  NBT字符串
     * @return 物品
     */
    public static ItemStack setNbt(ItemStack itemStack, String nbtSting) {
        try {
            switch (serverVersion) {
                case "1.12.2":
                case "1.16.5":
                case "1.17.1": {
                    Object nbt = MojangsonParserClass.getMethod("parse", String.class).invoke(null, nbtSting);
                    Object asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
                    Object originalNbt = getNbt(itemStack);
                    Method a = nbtTagCompoundClass.getMethod("a", nbtTagCompoundClass);
                    a.invoke(originalNbt, nbt);
                    Method load = nmsItemStackClass.getDeclaredMethod("load", nbtTagCompoundClass);
                    load.setAccessible(true);
                    load.invoke(asNMSCopy, originalNbt);
                    Method asBukkitCopy = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStackClass);
                    return (ItemStack) asBukkitCopy.invoke(null, asNMSCopy);
                }
                case "1.18.2":
                case "1.19.4":
                case "1.20.1":
                case "1.20.2":
                case "1.20.4": {
                    Object nbt = MojangsonParserClass.getMethod("a", String.class).invoke(null, nbtSting);
                    Object asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
                    Object originalNbt = getNbt(itemStack);
                    Method a = nbtTagCompoundClass.getMethod("a", nbtTagCompoundClass);
                    a.invoke(originalNbt, nbt);
                    Method load = nmsItemStackClass.getDeclaredMethod("load", nbtTagCompoundClass);
                    load.setAccessible(true);
                    load.invoke(asNMSCopy, originalNbt);
                    Method asBukkitCopy = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStackClass);
                    return (ItemStack) asBukkitCopy.invoke(null, asNMSCopy);
                }
                default:
                    return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getServerVersion() {
        String version = Bukkit.getServer().getVersion();
        if (version.contains("1.12")) {
            return "1.12.2";
        }
        if (version.contains("1.16")) {
            return "1.16.5";
        }
        if (version.contains("1.17")) {
            return "1.17.1";
        }
        if (version.contains("1.18")) {
            return "1.18.2";
        }
        if (version.contains("1.19")) {
            return "1.19.4";
        }
        if (version.contains("1.20.1")) {
            return "1.20.1";
        }
        if (version.contains("1.20.2")) {
            return "1.20.2";
        }
        if (version.contains("1.20.4")) {
            return "1.20.4";
        }
        return "unknown";
    }
}