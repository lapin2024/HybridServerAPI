package com.github.yyeerai.hybridserverapi.v1_20_1.api.pokemon;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.pokemon.egg.EggGroup;
import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.storage.NoPokemonStoreException;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.common.enums.EnumPokeAttribute;
import com.github.yyeerai.hybridserverapi.common.yaml.ConfigManager;
import com.github.yyeerai.hybridserverapi.common.yaml.RegisterConfig;
import com.github.yyeerai.hybridserverapi.v1_20_1.api.BaseApi;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

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
        if (POKEMON_API == null) {
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
        return PokemonProperties.Companion.parse(pokemonSpec, " ", "=").create();
    }

    /**
     * 从宝可梦NBT构建宝可梦
     *
     * @param compoundNBT 宝可梦NBT
     * @return 宝可梦
     */
    @Nullable
    public Pokemon getPokemon(CompoundTag compoundNBT) {
        return Pokemon.Companion.loadFromNBT(compoundNBT);
    }

    /**
     * 获得玩家宝可梦队伍
     *
     * @param player 玩家
     * @return 宝可梦队伍
     */
    public PlayerPartyStore getPartyStorage(OfflinePlayer player) {
        try {
            return Cobblemon.INSTANCE.getStorage().getParty(player.getUniqueId());
        } catch (NoPokemonStoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得玩家宝可梦仓库
     *
     * @param player 玩家
     * @return 宝可梦仓库
     */
    public PCStore getPCStorage(OfflinePlayer player) {
        try {
            return Cobblemon.INSTANCE.getStorage().getPC(player.getUniqueId());
        } catch (NoPokemonStoreException e) {
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
        net.minecraft.world.item.ItemStack photo = PokemonItem.from(pokemon);
        ItemStack itemStack = BaseApi.getBukkitItemStack(photo);
        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GREEN + pokemon.getDisplayName().getString());
        itemStack.setItemMeta(itemMeta);
        return BaseApi.getBukkitItemStack(photo);
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
        itemMeta.setDisplayName(ChatColor.GREEN + pokemon.getDisplayName().getString());
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bukkitItemStack.setItemMeta(itemMeta);
        return bukkitItemStack;
    }

    /**
     * 删除玩家宝可梦队伍中的宝可梦
     * 这个方法接受一个玩家对象和一个宝可梦对象，然后从玩家的宝可梦队伍中删除这个宝可梦。
     *
     * @param player  玩家
     * @param pokemon 宝可梦
     * @return 如果成功删除，返回true，否则返回false
     */
    public boolean removePokemonFromParty(OfflinePlayer player, Pokemon pokemon) {
        PlayerPartyStore partyStorage = getPartyStorage(player);
        partyStorage.remove(pokemon);
        return true;
    }

    /**
     * 删除玩家宝可梦仓库中的宝可梦
     * 这个方法接受一个玩家对象和一个宝可梦对象，然后从玩家的宝可梦仓库中删除这个宝可梦。
     *
     * @param player  玩家
     * @param pokemon 宝可梦
     * @return 如果成功删除，返回true，否则返回false
     */
    public boolean removePokemonFromPc(OfflinePlayer player, Pokemon pokemon) {
        PCStore pcStorage = getPCStorage(player);
        pcStorage.remove(pokemon);
        return true;
    }

    /**
     * 获得宝可梦是几v的
     *
     * @param pokemon 宝可梦
     * @return 宝可梦是几v的
     */
    public int getMaxIv(Pokemon pokemon) {
        int maxIv = 0;
        Iterator<Map.Entry<Stat, Integer>> iterator = pokemon.getEvs().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Stat, Integer> entry = iterator.next();
            if (entry.getKey().equals(Stats.ACCURACY) || entry.getKey().equals(Stats.EVASION)) {
                continue;
            }
            if (entry.getValue() >= 31) {
                maxIv++;
            }
        }
        return maxIv;
    }

    public Map<String, Object> getAttributes(Pokemon pokemon) {
        Map<String, Object> map = new HashMap<>();
        for (EnumPokeAttribute value : EnumPokeAttribute.values()) {
            Object attribute = getAttribute(value, pokemon);
            if (attribute != null) {
                map.put(value.name(), attribute);
            }
        }
        return map;
    }

    /**
     * 获得带有宝可梦信息的照片
     * 配置文件: api/config.yml
     *
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

    public Object getAttribute(EnumPokeAttribute attribute, Pokemon pokemon) {
        return switch (attribute) {
            case UUID -> pokemon.getUuid();
            case SPECIES -> pokemon.getSpecies().getName();
            case DISPLAY_NAME, LOCALIZED_NAME -> pokemon.getDisplayName().getString();
            case NICKNAME ->
                    pokemon.getNickname() != null ? pokemon.getNickname().getString() : pokemon.getDisplayName().getString();
            case DEX_NUMBER -> pokemon.getSpecies().getNationalPokedexNumber();
            case LEVEL -> pokemon.getLevel();
            case SHINY -> pokemon.getShiny() ? "是" : "否";
            case ABILITY -> pokemon.getAbility().getDisplayName();
            case EGG_GROUP ->
                    pokemon.getSpecies().getEggGroups().stream().map(EggGroup::getShowdownID$common).collect(Collectors.toList());
            case GROWTH, CUSTOM_TEXTURE, NUM_ENCHANTED, NUM_CLONED, HT_SPEED, HT_SPECIAL_DEFENCE,
                 HT_SPECIAL_ATTACK, HT_DEFENCE, HT_ATTACK, HT_HP, EGG, HIDE_ABILITY -> "暂时不支持";
            case NATURE -> pokemon.getNature().getDisplayName();
            case MINT_NATURE -> (pokemon.getMintedNature() != null ? pokemon.getMintedNature().getDisplayName() : "无");
            case GENDER -> pokemon.getGender().getShowdownName();
            case HELD_ITEM -> BaseApi.getBukkitItemStack(pokemon.heldItem()).getType().toString();
            case POKEBALL -> pokemon.getCaughtBall().getName().toString();
            case FRIENDSHIP -> pokemon.getFriendship();
            case OT_NAME, OWNER_NAME ->
                    pokemon.getOwnerPlayer() != null ? pokemon.getOwnerPlayer().getName().getString() : "无";
            case OT_UUID, OWNER_UUID -> pokemon.getOwnerUUID() != null ? pokemon.getOwnerUUID().toString() : "无";
            case HP -> pokemon.getHp();
            case ATTACK -> pokemon.getAttack();
            case DEFENCE -> pokemon.getDefence();
            case SPECIAL_ATTACK -> pokemon.getSpecialAttack();
            case SPECIAL_DEFENCE -> pokemon.getSpecialDefence();
            case SPEED -> pokemon.getSpeed();
            case IV_HP -> pokemon.getIvs().get(Stats.HP);
            case IV_ATTACK -> pokemon.getIvs().get(Stats.ATTACK);
            case IV_DEFENCE -> pokemon.getIvs().get(Stats.DEFENCE);
            case IV_SPECIAL_ATTACK -> pokemon.getIvs().get(Stats.SPECIAL_ATTACK);
            case IV_SPECIAL_DEFENCE -> pokemon.getIvs().get(Stats.SPECIAL_DEFENCE);
            case IV_SPEED -> pokemon.getIvs().get(Stats.SPEED);
            case IV_TOTAL -> getIvTotal(pokemon);
            case IV_PERCENTAGE -> String.format("%.2f", (getIvTotal(pokemon) / 186.0) * 100).replace(".00", "");
            case EV_HP -> pokemon.getEvs().get(Stats.HP);
            case EV_ATTACK -> pokemon.getEvs().get(Stats.ATTACK);
            case EV_DEFENCE -> pokemon.getEvs().get(Stats.DEFENCE);
            case EV_SPECIAL_ATTACK -> pokemon.getEvs().get(Stats.SPECIAL_ATTACK);
            case EV_SPECIAL_DEFENCE -> pokemon.getEvs().get(Stats.SPECIAL_DEFENCE);
            case EV_SPEED -> pokemon.getEvs().get(Stats.SPEED);
            case EV_TOTAL -> getEvTotal(pokemon);
            case EV_PERCENTAGE -> String.format("%.2f", (getEvTotal(pokemon) / 510.0) * 100).replace(".00", "");
            case SPEC_HP -> pokemon.getForm().getBaseStats().get(Stats.HP);
            case SPEC_ATTACK -> pokemon.getForm().getBaseStats().get(Stats.ATTACK);
            case SPEC_DEFENCE -> pokemon.getForm().getBaseStats().get(Stats.DEFENCE);
            case SPEC_SPECIAL_ATTACK -> pokemon.getForm().getBaseStats().get(Stats.SPECIAL_ATTACK);
            case SPEC_SPECIAL_DEFENCE -> pokemon.getForm().getBaseStats().get(Stats.SPECIAL_DEFENCE);
            case SPEC_SPEED -> pokemon.getForm().getBaseStats().get(Stats.SPEED);
            case MOVE_1 -> {
                Move move = pokemon.getMoveSet().get(0);
                yield move != null ? move.getDisplayName().getString() : "无";
            }
            case MOVE_2 -> {
                Move move = pokemon.getMoveSet().get(1);
                yield move != null ? move.getDisplayName().getString() : "无";
            }
            case MOVE_3 -> {
                Move move = pokemon.getMoveSet().get(2);
                yield move != null ? move.getDisplayName().getString() : "无";
            }
            case MOVE_4 -> {
                Move move = pokemon.getMoveSet().get(3);
                yield move != null ? move.getDisplayName().getString() : "无";
            }
            case LEGENDARY -> (pokemon.isLegendary() || pokemon.isMythical()) ? "是" : "否";
            case ULTRA_BEAST -> pokemon.isUltraBeast() ? "是" : "否";
            case TRADEABLE -> pokemon.hasLabels("untradeable") ? "否" : "是"; //此方法需要验证
            case BREEDABLE -> pokemon.hasLabels("unbreedable") ? "否" : "是";
            case CATCHABLE -> pokemon.hasLabels("uncatchable") ? "否" : "是";
            case FORM -> pokemon.getForm().getName();
        };
    }

    private int getIvTotal(@NotNull Pokemon pokemon) {
        return Objects.requireNonNull(pokemon.getIvs().get(Stats.HP)) + Objects.requireNonNull(pokemon.getIvs().get(Stats.ATTACK)) + Objects.requireNonNull(pokemon.getIvs().get(Stats.DEFENCE)) + Objects.requireNonNull(pokemon.getIvs().get(Stats.SPECIAL_ATTACK)) + Objects.requireNonNull(pokemon.getIvs().get(Stats.SPECIAL_DEFENCE)) + Objects.requireNonNull(pokemon.getIvs().get(Stats.SPEED));
    }

    private int getEvTotal(@NotNull Pokemon pokemon) {
        return Objects.requireNonNull(pokemon.getEvs().get(Stats.HP)) + Objects.requireNonNull(pokemon.getEvs().get(Stats.ATTACK)) + Objects.requireNonNull(pokemon.getEvs().get(Stats.DEFENCE)) + Objects.requireNonNull(pokemon.getEvs().get(Stats.SPECIAL_ATTACK)) + Objects.requireNonNull(pokemon.getEvs().get(Stats.SPECIAL_DEFENCE)) + Objects.requireNonNull(pokemon.getEvs().get(Stats.SPEED));
    }


}