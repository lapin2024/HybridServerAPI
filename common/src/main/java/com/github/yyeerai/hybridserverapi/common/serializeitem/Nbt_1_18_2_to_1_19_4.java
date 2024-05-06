package com.github.yyeerai.hybridserverapi.common.serializeitem;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class Nbt_1_18_2_to_1_19_4 implements INbt {

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
        Object nbt = nbtTagCompoundClass.newInstance();
        Method save = nmsItemStackClass.getMethod("b", nbtTagCompoundClass);
        save.invoke(nmsCopy, nbt);
        return nbt;
    }

    @SneakyThrows
    @Override
    public ItemStack setNbt(ItemStack itemStack, String nbtSting) {
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

    static {
        serverVersion = NbtUtil.getServerVersion();
        try {
            switch (serverVersion) {
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
                default:
                    Bukkit.getLogger().warning("未知的服务器版本: " + serverVersion);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to initialize NBT classes", e);
        }
    }
}