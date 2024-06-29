package com.github.yyeerai.hybridserverapi.v1_20_2.api;

import com.mc9y.nyeconomy.api.NyEconomyAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>这是一个常用方法API类，提供了一些常用的方法和功能。</p>
 * <p>很多方法已经被移动到</p>
 *
 * @see com.github.yyeerai.hybridserverapi.common.util.PluginMethods
 * <p>类中，这里的方法将在后续版本中被删除。</p>
 */
@SuppressWarnings({"unused"})
public class BaseApi {

    /**
     * 获取经济系统。如果Vault插件不存在，则返回null。
     *
     * @return 经济系统
     */
    @Nullable
    @Deprecated
    public static Economy getEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();
    }

    /**
     * 获取玩家点数API。如果PlayerPoints插件不存在，则返回null。
     *
     * @return 玩家点数API
     */
    @Nullable
    @Deprecated
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
     * 获取NyEconomyAPI。如果NyEconomy插件不存在，则返回null。
     *
     * @return NyEconomyAPI
     */
    @Nullable
    @Deprecated
    public static NyEconomyAPI getNyEconomyAPI() {
        if (Bukkit.getPluginManager().getPlugin("NyEconomy") == null) {
            return null;
        }
        return NyEconomyAPI.getInstance();
    }

    /**
     * 获取聊天系统。如果Vault插件不存在，则返回null。
     *
     * @return 聊天系统
     */
    @Nullable
    @Deprecated
    public static Chat getChat() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Chat.class)).getProvider();
    }

    /**
     * 获取权限系统。如果Vault插件不存在，则返回null。
     *
     * @return 权限系统
     */
    @Nullable
    @Deprecated
    public static Permission getPermission() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Permission.class)).getProvider();
    }

    /**
     * 检查PlaceholderAPI插件是否存在。
     *
     * @return 如果PlaceholderAPI插件存在，则返回true，否则返回false。
     */
    public static boolean getPlaceholderAPI() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    /**
     * 将bukkit的ItemStack转换为minecraft的ItemStack。
     *
     * @param itemStack bukkit的ItemStack
     * @return minecraft的ItemStack
     */
    @SuppressWarnings("all")
    public static net.minecraft.world.item.ItemStack getMinecraftItemStack(@NotNull ItemStack itemStack) {
        CraftItemStack craftItemStack = CraftItemStack.asCraftCopy(itemStack);
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(craftItemStack);
        return (net.minecraft.world.item.ItemStack) ((Object) nmsItemStack);
    }

    /**
     * 将minecraft的ItemStack转换为bukkit的ItemStack。
     *
     * @param itemStack minecraft的ItemStack
     * @return bukkit的ItemStack
     */
    @SuppressWarnings("all")
    public static ItemStack getBukkitItemStack(@NotNull net.minecraft.world.item.ItemStack itemStack) {
        return CraftItemStack.asBukkitCopy(itemStack);
    }

    /**
     * 获取CraftServer实例。
     *
     * @return CraftServer实例
     */
    public static CraftServer getCraftServer() {
        return (CraftServer) Bukkit.getServer();
    }

    /**
     * 获取MinecraftServer实例。
     *
     * @return MinecraftServer实例
     */
    @SuppressWarnings("UnstableApiUsage")
    public static MinecraftServer getMinecraftServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    /**
     * 根据世界名获取Minecraft的世界。如果世界不存在，则返回null。
     *
     * @param name 世界名
     * @return Minecraft的世界
     */
    @Nullable
    public static ServerLevel getMinecraftWorld(String name) {
        org.bukkit.World world = Bukkit.getWorld(name);
        if (world == null) {
            return null;
        }
        return getMinecraftWorld(world);
    }

    /**
     * 将bukkit的世界转换为minecraft的世界。
     *
     * @param world bukkit的世界
     * @return minecraft的世界
     */
    @SuppressWarnings("all")
    public static ServerLevel getMinecraftWorld(org.bukkit.World world) {
        CraftWorld craftWorld = (CraftWorld) world;
        return (ServerLevel) (Object) craftWorld.getHandle();
    }

    /**
     * 将bukkit的实体转换为minecraft的实体。
     *
     * @param entity bukkit的实体
     * @return minecraft的实体
     */
    @SuppressWarnings("all")
    public static net.minecraft.world.entity.Entity getMinecraftEntity(Entity entity) {
        CraftEntity craftEntity = (CraftEntity) entity;
        return craftEntity.getHandle();
    }

    /**
     * 将minecraft的实体转换为bukkit的实体。
     *
     * @param entity minecraft的实体
     * @param world  世界名
     * @return bukkit的实体
     */
    @SuppressWarnings("all")
    @Deprecated
    public static Entity getBukkitEntity(net.minecraft.world.entity.Entity entity, String world) {
        return CraftEntity.getEntity(getCraftServer(), entity);
    }

    /**
     * 将minecraft的实体转换为bukkit的实体。
     * @param entity minecraft的实体
     * @return bukkit的实体
     */
    public static Entity getBukkitEntity(net.minecraft.world.entity.Entity entity) {
        return CraftEntity.getEntity(getCraftServer(), entity);
    }

    /**
     * 根据UUID获取minecraft的玩家。如果玩家不存在，则返回null。
     *
     * @param uuid 玩家的UUID
     * @return minecraft的玩家
     */
    @SuppressWarnings("all")
    public static @Nullable ServerPlayer getMinecraftPlayer(UUID uuid) {
        return (ServerPlayer) (Object) getMinecraftServer().ac().a(uuid);
    }

    /**
     * 将bukkit的玩家转换为minecraft的玩家。
     *
     * @param player bukkit的玩家
     * @return minecraft的玩家
     */
    public static @Nullable ServerPlayer getMinecraftPlayer(Player player) {
        return getMinecraftPlayer(player.getUniqueId());
    }

    /**
     * 根据玩家名获取在线的bukkit玩家。如果玩家不在线，则返回null。
     *
     * @param name 玩家名
     * @return 在线的bukkit玩家
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
     * 获取在线玩家的名称列表。
     *
     * @return 在线玩家的名称列表
     */
    public static List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }

    /**
     * 根据minecraft的世界获取世界名。
     *
     * @param serverLevel minecraft的世界
     * @return 世界名
     */
    public static String getWorldName(ServerLevel serverLevel) {
        return serverLevel.toString().replace("ServerLevel[", "").replace("]", "");
    }

    /**
     * 将minecraft的世界转换为bukkit的世界。
     *
     * @param serverLevel minecraft的世界
     * @return bukkit的世界
     */
    public static @Nullable World getBukkitWorld(ServerLevel serverLevel) {
        return Bukkit.getWorld(getWorldName(serverLevel));
    }

    /**
     * 获取在线玩家的名称列表。
     *
     * @return 在线玩家的名称列表
     */
    @Deprecated
    public static List<String> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    /**
     * 将字符串列表转换为单个字符的字符串列表。
     *
     * @param origin 原字符串列表
     * @return 转换后的字符串列表
     */
    @Deprecated
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
     * 根据输入的字符串列表和过滤的字符串，返回一个按照字符串过滤后的字符串列表。
     *
     * @param list 输入的字符串列表
     * @param str  过滤的字符串
     * @return 过滤后的字符串列表
     */
    @Deprecated
    public static List<String> filter(List<String> list, String str) {
        List<String> newList = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith(str)) {
                newList.add(s);
            }
        }
        return newList;
    }

}