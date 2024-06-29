package com.github.yyeerai.hybridserverapi.v1_16_5.api;

import com.mc9y.nyeconomy.api.NyEconomyAPI;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.v1_16_R3.WorldServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 常用方法api
 */
@SuppressWarnings({"unused"})
public class BaseApi {

    /**
     * 获得经济系统
     *
     * @return 经济系统
     */
    @Nullable
    public static Economy getEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();
    }

    /**
     * 获得玩家点数api
     *
     * @return 玩家点数api
     */
    @Nullable
    public static PlayerPointsAPI getPlayerPointsAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlayerPoints") == null) {
            return null;
        }
        PlayerPoints playerPoints = (PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints");
        if (playerPoints != null) {
            return playerPoints.getAPI();
        }
        return null;
    }


    /**
     * 获得NyEconomyAPI
     *
     * @return NyEconomyAPI
     */
    @Nullable
    public static NyEconomyAPI getNyEconomyAPI() {
        if (Bukkit.getPluginManager().getPlugin("NyEconomy") == null) {
            return null;
        }
        return NyEconomyAPI.getInstance();
    }

    /**
     * 获得聊天系统
     *
     * @return 聊天系统
     */
    @Nullable
    public static Chat getChat() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Chat.class)).getProvider();
    }

    /**
     * 获得权限系统
     *
     * @return 权限系统
     */
    @Nullable
    public static Permission getPermission() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Permission.class)).getProvider();
    }

    public static boolean getPlaceholderAPI() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    /**
     * 获得minecraft itemstack
     *
     * @param itemStack bukkit itemstack
     * @return minecraft itemstack
     */
    @SuppressWarnings("all")
    public static net.minecraft.item.ItemStack getMinecraftItemStack(@NotNull ItemStack itemStack) {
        CraftItemStack craftItemStack = CraftItemStack.asCraftCopy(itemStack);
        net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(craftItemStack);
        return (net.minecraft.item.ItemStack) ((Object) nmsItemStack);
    }

    /**
     * 获得bukkit itemstack
     *
     * @param itemStack minecraft itemstack
     * @return bukkit itemstack
     */
    @SuppressWarnings("all")
    public static ItemStack getBukkitItemStack(@NotNull net.minecraft.item.ItemStack itemStack) {
        net.minecraft.server.v1_16_R3.ItemStack craftItemStack = (net.minecraft.server.v1_16_R3.ItemStack) ((Object) itemStack);
        return CraftItemStack.asBukkitCopy(craftItemStack);
    }

    /**
     * 获得CraftServer
     *
     * @return CraftServer
     */
    public static CraftServer getCraftServer() {
        return (CraftServer) Bukkit.getServer();
    }

    /**
     * 获得MinecraftServer
     *
     * @return MinecraftServer
     */
    public static MinecraftServer getMinecraftServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    /**
     * 获得Minecraft world
     *
     * @param name 世界名
     * @return Minecraft world
     */
    @Nullable
    public static World getMinecraftWorld(String name) {
        org.bukkit.World world = Bukkit.getWorld(name);
        if (world == null) {
            return null;
        }
        return getMinecraftWorld(world);
    }

    /**
     * 获得MinecraftWorld
     *
     * @param world bukkit world
     * @return MinecraftWorld
     */
    @SuppressWarnings("all")
    public static World getMinecraftWorld(org.bukkit.World world) {
        CraftWorld craftWorld = (CraftWorld) world;
        WorldServer handle = craftWorld.getHandle();
        return (World) ((Object) handle);
    }

    /**
     * 获得minecraft entity
     *
     * @param entity bukkit entity
     * @param world  世界名
     * @return minecraft entity
     */
    @SuppressWarnings("all")
    public static net.minecraft.entity.Entity getMinecraftEntity(Entity entity) {
        CraftEntity craftEntity = (CraftEntity) entity;
        net.minecraft.server.v1_16_R3.Entity handle = craftEntity.getHandle();
        return (net.minecraft.entity.Entity) ((Object) handle);
    }

    /**
     * 获得bukkit entity
     *
     * @param entity minecraft entity
     * @param world  世界名
     * @return bukkit entity
     */
    @SuppressWarnings("all")
    @Deprecated
    public static Entity getBukkitEntity(net.minecraft.entity.Entity entity, String world) {
        return CraftEntity.getEntity(getCraftServer(), (net.minecraft.server.v1_16_R3.Entity) ((Object) entity));
    }

    /**
     * 获得bukkit entity
     * @param entity minecraft entity
     * @return bukkit entity
     */
    @SuppressWarnings("all")
    public static Entity getBukkitEntity(net.minecraft.entity.Entity entity) {
        return CraftEntity.getEntity(getCraftServer(), (net.minecraft.server.v1_16_R3.Entity) ((Object) entity));
    }

    /**
     * 获得serverPlayerEntity
     *
     * @param uuid 玩家uuid
     * @return serverPlayerEntity
     */
    public static ServerPlayerEntity getMinecraftPlayer(UUID uuid) {
        return getMinecraftServer().getPlayerList().getPlayer(uuid);
    }

    /**
     * 获得EntityPlayer实体
     *
     * @param player 玩家
     * @return 玩家实体
     */
    public static ServerPlayerEntity getMinecraftPlayer(Player player) {
        return getMinecraftPlayer(player.getUniqueId());
    }

    /**
     * 获得bukkit player(在线的)
     *
     * @param name 玩家名
     * @return bukkit player
     */
    public static Player getPlayer(String name) {
        Player player = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(name)) {
                player = p;
                break;
            }
        }
        return player;
    }

    /**
     * 通过net.minecraft.world 获得 世界名称
     *
     * @param world net.minecraft.world
     * @return 世界名称
     */
    public static String getWorldName(World world) {
        return ((ServerWorldInfo) world.getLevelData()).getLevelName();
    }


    /**
     * 获取在线玩家名称列表
     *
     * @return 在线玩家mc列表
     */
    public static List<String> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    /**
     * 将字符串列表转换为单个char的字符串列表
     *
     * @param origin 原字符串列表
     * @return 转换后的字符串列表
     */
    public static List<String> getListString(List<String> origin) {
        List<String> list = new ArrayList<>();
        for (String s : origin) {
            for (char c : s.toCharArray()) {
                list.add(String.valueOf(c));
            }
        }
        return list;
    }

    /**
     * 根据输入的字符串列表 list,返回一个按照字符串 str 过滤后的字符串列表
     *
     * @param list 输入的字符串列表
     * @param str  过滤的字符串
     * @return 过滤后的字符串列表
     */
    public static List<String> filter(List<String> list, String str) {
        List<String> newList = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith(str)) {
                newList.add(s);
            }
        }
        return newList;
    }

    /**
     * 将物品序列化为Map
     *
     * @param itemStack 物品
     * @return Map
     */
    public static Map<String, Object> serializeItemStack(ItemStack itemStack) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", itemStack.getType().name());
        map.put("amount", itemStack.getAmount());
        ItemMeta meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        assert meta != null;
        if (meta.hasDisplayName()) {
            map.put("name", meta.getDisplayName());
        }
        if (meta.hasLore()) {
            map.put("lore", meta.getLore());
        }
        CompoundNBT nbt = getNbt(itemStack);
        CompoundNBT tag = nbt.getCompound("tag");
        tag.remove("display");
        if (tag.isEmpty()) {
            nbt.remove("tag");
        }
        nbt.remove("Damage");
        nbt.remove("Count");
        nbt.remove("id");
        nbt.isEmpty();
        return map;
    }


    /**
     * 反序列化物品
     *
     * @param args 物品序列化后的Map
     * @return 物品
     */
    public static ItemStack deserializeItemStack(Map<String, Object> args) {
        String type = (String) args.get("type");
        if (type == null || type.isEmpty()) {
            return getDebugItem();
        }
        Material material = Material.getMaterial(type.toUpperCase());
        if (material == null) {
            return getDebugItem();
        }
        int amount = 1;
        if (args.containsKey("amount")) {
            amount = (int) args.get("amount");
        }
        ItemStack itemStack = new ItemStack(material, amount);
        if (args.containsKey("nbt")) {
            Object nbtObject = args.get("nbt");
            try {
                CompoundNBT tagFromJson = JsonToNBT.parseTag((String) nbtObject);
                itemStack = setNbt(itemStack, tagFromJson);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }

        }
        ItemMeta meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        assert meta != null;
        if (args.containsKey("name")) {
            meta.setDisplayName((String) args.get("name"));
        }
        if (args.containsKey("lore")) {
            Object raw = args.get("lore");
            if (raw instanceof List) {
                List<?> lore = (List<?>) raw;
                List<String> newLore = new ArrayList<>();
                for (Object o : lore) {
                    if (o instanceof String) {
                        newLore.add((String) o);
                    }
                }
                meta.setLore(newLore);
            }
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }


    /**
     * 给物品设置NBT
     *
     * @param itemStack 物品
     * @param nbt       NBT
     * @return 物品
     */
    private static ItemStack setNbt(ItemStack itemStack, CompoundNBT nbt) {
        net.minecraft.item.ItemStack minecraftItemStack = getMinecraftItemStack(itemStack);
        minecraftItemStack.setTag(nbt);
        return getBukkitItemStack(minecraftItemStack);
    }

    /**
     * 获取物品的NBT
     *
     * @param itemStack 物品
     * @return NBT
     */
    public static CompoundNBT getNbt(@NotNull ItemStack itemStack) {
        net.minecraft.item.ItemStack minecraftItemStack = getMinecraftItemStack(itemStack);
        return minecraftItemStack.hasTag() ? minecraftItemStack.getTag() : new CompoundNBT();
    }


    private static ItemStack getDebugItem() {
        ItemStack itemStack = new ItemStack(Material.STONE);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7物品出错,请检查设置"));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}