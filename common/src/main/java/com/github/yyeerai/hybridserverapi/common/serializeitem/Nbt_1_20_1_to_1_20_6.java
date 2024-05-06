package com.github.yyeerai.hybridserverapi.common.serializeitem;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class Nbt_1_20_1_to_1_20_6 implements INbt {
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
        if (isArclightServer()) { //判断是否为Arclight服务器
            Method save = nmsItemStackClass.getMethod("m_41739_", nbtTagCompoundClass); //获取save方法
            save.invoke(nmsCopy, nbt); //调用save方法
            return nbt; //返回NBTTagCompound对象
        } else {
            Method save = nmsItemStackClass.getMethod("b", nbtTagCompoundClass); //获取save方法
            save.invoke(nmsCopy, nbt); //调用save方法
            return nbt; //返回NBTTagCompound对象
        }
    }

    @SneakyThrows
    @Override
    public ItemStack setNbt(ItemStack itemStack, String nbtSting) {
        if (isArclightServer()) {
            Object nbt = MojangsonParserClass.getMethod("m_129359_", String.class).invoke(null, nbtSting); //将json字符串转换为NBT对象
            Object originalNbt = getNbt(itemStack); //获取原NBT对象
            Method a = nbtTagCompoundClass.getMethod("m_128391_", nbtTagCompoundClass); //合并NBT方法
            a.invoke(originalNbt, nbt); //调用合并NBT方法
            Method load = nmsItemStackClass.getDeclaredMethod("m_41712_", nbtTagCompoundClass); //设置NBT方法
            Object invoke = load.invoke(null, originalNbt);//调用设置NBT方法
            Method asBukkitCopy = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStackClass); //将NMS ItemStack对象转换为ItemStack对象
            return (ItemStack) asBukkitCopy.invoke(null, invoke); //调用将NMS ItemStack对象转换为ItemStack对象的方法
        } else {
            Object nbt = MojangsonParserClass.getMethod("a", String.class).invoke(null, nbtSting); //将json字符串转换为NBT对象
            Object asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack); //将ItemStack对象转换为NMS ItemStack对象
            Object originalNbt = getNbt(itemStack); //获取原NBT对象
            Method a = nbtTagCompoundClass.getMethod("a", nbtTagCompoundClass); //合并NBT方法
            a.invoke(originalNbt, nbt); //调用合并NBT方法
            Method load = nmsItemStackClass.getDeclaredMethod("load", nbtTagCompoundClass); //设置NBT方法
            load.setAccessible(true); //设置为可访问
            load.invoke(asNMSCopy, originalNbt); //调用设置NBT方法
            Method asBukkitCopy = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStackClass); //将NMS ItemStack对象转换为ItemStack对象
            return (ItemStack) asBukkitCopy.invoke(null, asNMSCopy); //调用将NMS ItemStack对象转换为ItemStack对象的方法
        }

    }

    static {
        serverVersion = NbtUtil.getServerVersion();
        try {
            switch (serverVersion) {
                case "1.20.1":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack");
                    if (isArclightServer()) {
                        nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                        nbtTagCompoundClass = Class.forName("net.minecraft.nbt.CompoundTag");
                        MojangsonParserClass = Class.forName("net.minecraft.nbt.TagParser");
                    } else {
                        nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                        nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                        MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    }
                    break;
                case "1.20.2":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack");
                    if (isArclightServer()) {
                        nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                        nbtTagCompoundClass = Class.forName("net.minecraft.nbt.CompoundTag");
                        MojangsonParserClass = Class.forName("net.minecraft.nbt.TagParser");
                    } else {
                        nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                        nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                        MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    }
                    break;
                case "1.20.3":
                case "1.20.4":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack");
                    if (isArclightServer()) {
                        nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                        nbtTagCompoundClass = Class.forName("net.minecraft.nbt.CompoundTag");
                        MojangsonParserClass = Class.forName("net.minecraft.nbt.TagParser");
                    } else {
                        nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                        nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                        MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    }
                    break;
                case "1.20.5":
                case "1.20.6":
                    craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_20_R4.inventory.CraftItemStack");
                    if (isArclightServer()) {
                        nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                        nbtTagCompoundClass = Class.forName("net.minecraft.nbt.CompoundTag");
                        MojangsonParserClass = Class.forName("net.minecraft.nbt.TagParser");
                    } else {
                        nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
                        nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
                        MojangsonParserClass = Class.forName("net.minecraft.nbt.MojangsonParser");
                    }
                    break;
                default:
                    Bukkit.getLogger().warning("未知的服务器版本: " + serverVersion);
                    break;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isArclightServer() {
        return Bukkit.getName().contains("Arclight");
    }
}