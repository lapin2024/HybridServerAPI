package com.github.yyeerai.hybridserverapi.common.serializeitem;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class NbtUtil {

    private static final String serverVersion;
    private static final INbt nbt;

    static {
        serverVersion = getServerVersion();
        switch (serverVersion) {
            case "1.12.2":
            case "1.16.5":
            case "1.17.1":
                nbt = new Nbt_1_12_2_to_1_17_1();
                break;
            case "1.18.2":
            case "1.19.4":
                nbt = new Nbt_1_18_2_to_1_19_4();
                break;
            case "1.20.1":
            case "1.20.2":
            case "1.20.4":
            case "1.20.5":
            case "1.20.6":
                nbt = new Nbt_1_20_1_to_1_20_6();
                break;
            default:
                Bukkit.getLogger().warning("未知的服务器版本: " + serverVersion);
                nbt = null;
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
        if(nbt == null) {
            return null;
        }
        return nbt.getNbt(itemStack);
    }

    /**
     * 给物品设置NBT
     *
     * @param itemStack bukkit物品
     * @param nbtSting  NBT字符串
     * @return 物品
     */
    public static ItemStack setNbt(ItemStack itemStack, String nbtSting) {
        if(nbt == null) {
            return itemStack;
        }
        return nbt.setNbt(itemStack, nbtSting);
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
        if (version.contains("1.20.4") || version.contains("1.20.3")) {
            return "1.20.4";
        }
        if (version.contains("1.20.6") || version.contains("1.20.5")) {
            return "1.20.6";
        }
        return "unknown";
    }
}