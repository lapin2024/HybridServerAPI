package com.github.yyeerai.hybridserverapi.v1_20_2.api.pokemon;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.common.enums.EnumPokeAttribute;
import com.github.yyeerai.hybridserverapi.common.yaml.ConfigManager;
import com.github.yyeerai.hybridserverapi.common.yaml.RegisterConfig;
import com.github.yyeerai.hybridserverapi.v1_20_2.api.BaseApi;
import com.pixelmonmod.api.parsing.ParseAttempt;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.pokemon.stats.extraStats.LakeTrioStats;
import com.pixelmonmod.pixelmon.api.pokemon.stats.extraStats.MewStats;
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
        configManager = RegisterConfig.registerConfig((JavaPlugin) Bukkit.getPluginManager().getPlugin("HybridServerAPI"), "api/config.yml", ConfigManager.CreateType.RELEASE);
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
        ParseAttempt<PokemonSpecification> pokemonSpecificationParseAttempt = PokemonSpecificationProxy.create(pokemonSpec);
        if (pokemonSpecificationParseAttempt.wasSuccess()) {
            return pokemonSpecificationParseAttempt.get().create();
        } else {
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
        assert itemMeta != null;
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
     * 删除玩家宝可梦队伍中的宝可梦
     * 这个方法接受一个玩家对象和一个宝可梦对象，然后从玩家的宝可梦队伍中删除这个宝可梦。
     *
     * @param player  玩家
     * @param pokemon 宝可梦
     * @return 如果成功删除，返回true，否则返回false
     */
    public boolean removePokemonFromParty(OfflinePlayer player, Pokemon pokemon) {
        PlayerPartyStorage partyStorage = getPartyStorage(player);
        for (int i = 0; i < partyStorage.getAll().length; i++) {
            Pokemon pokemon1 = partyStorage.get(i);
            if (pokemon1 != null && pokemon1.getUUID().equals(pokemon.getUUID())) {
                partyStorage.set(i, null);
                return true;
            }
        }
        return false;
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
        PCStorage pcStorage = getPCStorage(player);
        for (int i = 0; i < pcStorage.getBoxCount(); i++) {
            for (int j = 0; j < 30; j++) {
                Pokemon pokemon1 = pcStorage.getBox(i).get(j);
                if (pokemon1 != null && pokemon1.getUUID().equals(pokemon.getUUID())) {
                    pcStorage.getBox(i).set(j, null);
                    return true;
                }
            }
        }
        return false;
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
            case UUID -> pokemon.getUUID();
            case SPECIES -> pokemon.getSpecies().getName();
            case DISPLAY_NAME -> pokemon.getFormattedDisplayName().getString();
            case NICKNAME ->
                    pokemon.getFormattedNickname() != null ? pokemon.getFormattedNickname().getString() : pokemon.getFormattedDisplayName().getString();
            case LOCALIZED_NAME -> pokemon.getLocalizedName();
            case DEX_NUMBER -> pokemon.getSpecies().getDex();
            case LEVEL -> pokemon.getPokemonLevel();
            case SHINY -> pokemon.isShiny() ? "是" : "否";
            case ABILITY -> pokemon.getAbility().getTranslatedName().getString();
            case GROWTH -> pokemon.getGrowth().getTranslatedName().getString();
            case NATURE -> pokemon.getNature().getTranslatedName().getString();
            case MINT_NATURE ->
                    (pokemon.getMintNature() != null ? pokemon.getMintNature().getTranslatedName().getString() : "无");
            case GENDER -> pokemon.getGender().getTranslatedName().getString();
            case HELD_ITEM -> BaseApi.getBukkitItemStack(pokemon.getHeldItem()).getType().toString();
            case POKEBALL -> pokemon.getBall().getTranslatedLidName().getString();
            case FRIENDSHIP -> pokemon.getFriendship();
            case OT_NAME -> pokemon.getOriginalTrainer() != null ? pokemon.getOriginalTrainer() : "无";
            case OT_UUID ->
                    pokemon.getOriginalTrainerUUID() != null ? pokemon.getOriginalTrainerUUID().toString() : "无";
            case OWNER_NAME -> pokemon.getOwnerName();
            case OWNER_UUID -> pokemon.getOwnerPlayerUUID() != null ? pokemon.getOwnerPlayerUUID().toString() : "无";
            case CUSTOM_TEXTURE ->
                    pokemon.getPalette() != null ? pokemon.getPalette().getTranslatedName().getString() : "无";
            case EGG -> pokemon.isEgg() ? "是" : "否";
            case EGG_GROUP ->
                    (pokemon.getForm().getEggGroups() != null || !pokemon.getForm().getEggGroups().isEmpty()) ? pokemon.getForm().getEggGroups().stream().map(e -> e.getTranslatedName().getString()).collect(Collectors.joining(", ")) : "无";
            case NUM_CLONED ->
                    pokemon.getExtraStats() instanceof MewStats ? ((MewStats) pokemon.getExtraStats()).numCloned : "不能克隆";
            case NUM_ENCHANTED ->
                    pokemon.getExtraStats() instanceof LakeTrioStats ? ((LakeTrioStats) pokemon.getExtraStats()).numEnchanted : "不能附魔";
            case HP -> pokemon.getStats().getHP();
            case ATTACK -> pokemon.getStats().getAttack();
            case DEFENCE -> pokemon.getStats().getDefense();
            case SPECIAL_ATTACK -> pokemon.getStats().getSpecialAttack();
            case SPECIAL_DEFENCE -> pokemon.getStats().getSpecialDefense();
            case SPEED -> pokemon.getStats().getSpeed();
            case IV_HP -> pokemon.getIVs().getStat(BattleStatsType.HP);
            case IV_ATTACK -> pokemon.getIVs().getStat(BattleStatsType.ATTACK);
            case IV_DEFENCE -> pokemon.getIVs().getStat(BattleStatsType.DEFENSE);
            case IV_SPECIAL_ATTACK -> pokemon.getIVs().getStat(BattleStatsType.SPECIAL_ATTACK);
            case IV_SPECIAL_DEFENCE -> pokemon.getIVs().getStat(BattleStatsType.SPECIAL_DEFENSE);
            case IV_SPEED -> pokemon.getIVs().getStat(BattleStatsType.SPEED);
            case IV_TOTAL -> pokemon.getIVs().getTotal();
            case IV_PERCENTAGE -> String.format("%.2f", pokemon.getIVs().getTotal() / 186.0 * 100.0).replace(".00", "");
            case EV_HP -> pokemon.getEVs().getStat(BattleStatsType.HP);
            case EV_ATTACK -> pokemon.getEVs().getStat(BattleStatsType.ATTACK);
            case EV_DEFENCE -> pokemon.getEVs().getStat(BattleStatsType.DEFENSE);
            case EV_SPECIAL_ATTACK -> pokemon.getEVs().getStat(BattleStatsType.SPECIAL_ATTACK);
            case EV_SPECIAL_DEFENCE -> pokemon.getEVs().getStat(BattleStatsType.SPECIAL_DEFENSE);
            case EV_SPEED -> pokemon.getEVs().getStat(BattleStatsType.SPEED);
            case EV_TOTAL -> pokemon.getEVs().getTotal();
            case EV_PERCENTAGE -> String.format("%.2f", pokemon.getEVs().getTotal() / 510.0 * 100.0).replace(".00", "");
            case HT_HP -> pokemon.getIVs().isHyperTrained(BattleStatsType.HP) ? 31 : 0;
            case HT_ATTACK -> pokemon.getIVs().isHyperTrained(BattleStatsType.ATTACK) ? 31 : 0;
            case HT_DEFENCE -> pokemon.getIVs().isHyperTrained(BattleStatsType.DEFENSE) ? 31 : 0;
            case HT_SPECIAL_ATTACK -> pokemon.getIVs().isHyperTrained(BattleStatsType.SPECIAL_ATTACK) ? 31 : 0;
            case HT_SPECIAL_DEFENCE -> pokemon.getIVs().isHyperTrained(BattleStatsType.SPECIAL_DEFENSE) ? 31 : 0;
            case HT_SPEED -> pokemon.getIVs().isHyperTrained(BattleStatsType.SPEED) ? 31 : 0;
            case SPEC_HP -> pokemon.getForm().getBattleStats().getStat(BattleStatsType.HP);
            case SPEC_ATTACK -> pokemon.getForm().getBattleStats().getStat(BattleStatsType.ATTACK);
            case SPEC_DEFENCE -> pokemon.getForm().getBattleStats().getStat(BattleStatsType.DEFENSE);
            case SPEC_SPECIAL_ATTACK -> pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPECIAL_ATTACK);
            case SPEC_SPECIAL_DEFENCE -> pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPECIAL_DEFENSE);
            case SPEC_SPEED -> pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPEED);
            case MOVE_1 ->
                    (pokemon.getMoveset().get(0) != null ? pokemon.getMoveset().get(0).getMove().getTranslatedName().getString() : "无");
            case MOVE_2 ->
                    (pokemon.getMoveset().get(1) != null ? pokemon.getMoveset().get(1).getMove().getTranslatedName().getString() : "无");
            case MOVE_3 ->
                    (pokemon.getMoveset().get(2) != null ? pokemon.getMoveset().get(2).getMove().getTranslatedName().getString() : "无");
            case MOVE_4 ->
                    (pokemon.getMoveset().get(3) != null ? pokemon.getMoveset().get(3).getMove().getTranslatedName().getString() : "无");
            case TERASTAL_TYPE, TERASTAL_INDEX -> "未知";
            case LEGENDARY -> (pokemon.isLegendary() || pokemon.isMythical()) ? "是" : "否";
            case ULTRA_BEAST -> pokemon.isUltraBeast() ? "是" : "否";
            case HIDE_ABILITY -> pokemon.getAbilitySlot() == 2 ? "是" : "否";
            case TRADEABLE -> pokemon.hasFlag("untradeable") ? "否" : "是";
            case BREEDABLE -> pokemon.hasFlag("unbreedable") ? "否" : "是";
            case CATCHABLE -> pokemon.hasFlag("uncatchable") ? "否" : "是";
            case FORM -> pokemon.getForm().getLocalizedName();
        };
    }


}