package com.github.yyeerai.hybridserverapi.v1_20_2.api.pokemon;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.common.yaml.ConfigManager;
import com.github.yyeerai.hybridserverapi.common.yaml.RegisterConfig;
import com.github.yyeerai.hybridserverapi.v1_20_2.api.BaseApi;
import com.pixelmonmod.api.parsing.ParseAttempt;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 宝可梦API
 */
@Getter
@SuppressWarnings("unused")
public class PokemonApi {

    private final ConfigManager configManager;
    public static PokemonApi POKEMON_API;

    private PokemonApi() {
        configManager = RegisterConfig.registerConfig((JavaPlugin) Bukkit.getPluginManager().getPlugin("HybridServerAPI"), "api/config.yml", false);
    }

    public static PokemonApi getInstance() {
        if(POKEMON_API == null){
            POKEMON_API = new PokemonApi();
        }
        return POKEMON_API;
    }

    /**
     * 从宝可梦字符串构建宝可梦,可以支持pokemonspec
     *
     * @param pokemonSpec 宝可梦字符串
     * @return 宝可梦
     */
    @Nullable
    public Pokemon getPokemon(String pokemonSpec) {
        ParseAttempt<PokemonSpecification> pokemonSpecificationParseAttempt = PokemonSpecificationProxy.create(pokemonSpec);
        if(pokemonSpecificationParseAttempt.wasSuccess()) {
            return pokemonSpecificationParseAttempt.get().create();
        }else {
            return null;
        }
    }

    /**
     * 从宝可梦NBT构建宝可梦
     *
     * @param compoundNBT 宝可梦NBT
     * @return 宝可梦
     */
    @Nullable
    public Pokemon getPokemon(CompoundTag compoundNBT) {
        return PokemonFactory.create(compoundNBT);
    }

    /**
     * 获得玩家宝可梦队伍
     *
     * @param player 玩家
     * @return 宝可梦队伍
     */
    public PlayerPartyStorage getPartyStorage(OfflinePlayer player) {
        try {
            return StorageProxy.getParty(player.getUniqueId()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得玩家宝可梦仓库
     *
     * @param player 玩家
     * @return 宝可梦仓库
     */
    public PCStorage getPCStorage(OfflinePlayer player) {
        try {
            return StorageProxy.getPCForPlayer(player.getUniqueId()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得宝可梦的照片
     *
     * @param pokemon 宝可梦
     * @return 宝可梦照片
     */
    public ItemStack getPokemonPhoto(Pokemon pokemon) {
        net.minecraft.world.item.ItemStack photo = SpriteItemHelper.getPhoto(pokemon);
        ItemStack itemStack = BaseApi.getBukkitItemStack(photo);
        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        itemMeta.setDisplayName(ChatColor.GREEN + pokemon.getFormattedDisplayName().getString());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * 获得宝可梦的照片(发光)
     *
     * @param pokemon 宝可梦
     * @return 宝可梦照片
     */
    public ItemStack getPokemonGlowPhoto(Pokemon pokemon) {
        ItemStack bukkitItemStack = getPokemonPhoto(pokemon);
        ItemMeta itemMeta = bukkitItemStack.hasItemMeta() ? bukkitItemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(bukkitItemStack.getType());
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GREEN + pokemon.getFormattedDisplayName().getString());
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bukkitItemStack.setItemMeta(itemMeta);
        return bukkitItemStack;
    }

    /**
     * 获得宝可梦是几v的
     *
     * @param pokemon 宝可梦
     * @return 宝可梦是几v的
     */
    public int getMaxIv(Pokemon pokemon) {
        int maxIv = 0;
        for (int iv : pokemon.getIVs().getArray()) {
            if (iv >= 31) {
                maxIv++;
            }
        }
        return maxIv;
    }

    private Map<String, Object> getAttributes(Pokemon pokemon) {
        Map<String, Object> map = new HashMap<>();
        for (EnumPokeAttribute value : EnumPokeAttribute.values()) {
            Object attribute = value.getAttribute(pokemon);
            if (attribute != null) {
                map.put(value.name(), attribute);
            }
        }
        return map;
    }

    /**
     * 获得带有宝可梦信息的照片
     * 配置文件: api/config.yml
     * @param pokemon 宝可梦
     * @return 带有宝可梦信息的照片的物品堆
     */
    public ItemStack getPokemonPhotoInfo(Pokemon pokemon) {
        ItemStack photo = getPokemonPhoto(pokemon);
        ItemMeta itemMeta = photo.hasItemMeta() ? photo.getItemMeta() : Bukkit.getItemFactory().getItemMeta(photo.getType());
        assert itemMeta != null;
        String name = configManager.getConfig().getString("sprite.name", "&f&l{DISPLAY_NAME} &7Lv.&e{LEVEL}");
        List<String> lore = configManager.getConfig().getStringList("sprite.lore");
        Map<String, Object> attributes = getAttributes(pokemon);
        for (String key : attributes.keySet()) {
            String value = attributes.get(key).toString();
            name = HexUtils.colorify(name.replace("{" + key + "}", value).replace("%" + key + "%", value));
            lore.replaceAll(s -> HexUtils.colorify(s.replace("{" + key + "}", value).replace("%" + key + "%", value)));
        }
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemMeta.setLore(lore);
        photo.setItemMeta(itemMeta);
        return photo;
    }

    public ItemStack getPokemonPhotoInfo(Pokemon pokemon, String name, List<String> lore) {
        ItemStack photo = getPokemonPhoto(pokemon);
        ItemMeta itemMeta = photo.hasItemMeta() ? photo.getItemMeta() : Bukkit.getItemFactory().getItemMeta(photo.getType());
        assert itemMeta != null;
        Map<String, Object> attributes = getAttributes(pokemon);
        for (String key : attributes.keySet()) {
            String value = attributes.get(key).toString();
            name = HexUtils.colorify(name.replace("{" + key + "}", value).replace("%" + key + "%", value));
            lore.replaceAll(s -> HexUtils.colorify(s.replace("{" + key + "}", value).replace("%" + key + "%", value)));
        }
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemMeta.setLore(lore);
        photo.setItemMeta(itemMeta);
        return photo;
    }


}