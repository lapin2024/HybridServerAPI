package com.github.yyeerai.hybridserverapi.common.serializeitem;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class Nbt_1_12_2_to_1_17_1 implements INbt {
    private static final String serverVersion;
    private static Class<?> craftItemStackClass;
    private static Class<?> nmsItemStackClass;
    private static Class<?> nbtTagCompoundClass;
    private static Class<?> MojangsonParserClass;

    @SneakyThrows
    @Override
    public Object getNbt(ItemStack itemStack) {
        Method asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class); //获取asNMSCopy方法
        Object nmsCopy = asNMSCopy.invoke(null, itemStack); //调用asNMSCopy方法
        Object nbt = nbtTagCompoundClass.newInstance(); //创建一个NBTTagCompound对象
        Method save = nmsItemStackClass.getMethod("save", nbtTagCompoundClass); //获取save方法
        save.invoke(nmsCopy, nbt); //调用save方法
        return nbt; //返回NBTTagCompound对象
    }

    @SneakyThrows
    @Override
    public ItemStack setNbt(ItemStack itemStack, String nbtSting) {
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

    static {
        serverVersion = NbtUtil.getServerVersion();
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
                default:
                    Bukkit.getLogger().warning("未知的服务器版本: " + serverVersion);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to initialize NBT classes", e);
        }
    }
}