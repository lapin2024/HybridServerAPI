package com.github.yyeerai.hybridserverapi.v1_16_5.api.pokemon;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.common.enums.EnumPokeAttribute;
import com.github.yyeerai.hybridserverapi.common.yaml.ConfigManager;
import com.github.yyeerai.hybridserverapi.common.yaml.RegisterConfig;
import com.github.yyeerai.hybridserverapi.v1_16_5.api.BaseApi;
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
import com.pixelmonmod.pixelmon.api.util.ITranslatable;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
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
        PokemonSpecification pokemonSpecification = PokemonSpecificationProxy.create(pokemonSpec);
        if (pokemonSpecification == null) {
            return null;
        }
        return pokemonSpecification.create();
    }

    /**
     * 从宝可梦NBT构建宝可梦
     *
     * @param compoundNBT 宝可梦NBT
     * @return 宝可梦
     */
    public Pokemon getPokemon(CompoundNBT compoundNBT) {
        return PokemonFactory.create(compoundNBT);
    }

    /**
     * 获得玩家宝可梦队伍
     *
     * @param player 玩家
     * @return 宝可梦队伍
     */
    public PlayerPartyStorage getPartyStorage(OfflinePlayer player) {
        return StorageProxy.getParty(player.getUniqueId());
    }

    /**
     * 获得玩家宝可梦仓库
     *
     * @param player 玩家
     * @return 宝可梦仓库
     */
    public PCStorage getPCStorage(OfflinePlayer player) {
        return StorageProxy.getPCForPlayer(player.getUniqueId());
    }

    /**
     * 获得宝可梦的照片
     *
     * @param pokemon 宝可梦
     * @return 宝可梦照片
     */
    public ItemStack getPokemonPhoto(Pokemon pokemon) {
        net.minecraft.item.ItemStack photo = SpriteItemHelper.getPhoto(pokemon);
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

    /**
     * 获取宝可梦的属性
     *
     * @param pokemon 宝可梦
     * @return 宝可梦的属性映射
     */
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

    /**
     * 获得带有宝可梦信息的照片，可以自定义名称和描述
     *
     * @param pokemon 宝可梦
     * @param name    自定义名称
     * @param lore    自定义描述
     * @return 带有宝可梦信息的照片的物品堆
     */
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

    /**
     * 通过属性获取宝可梦的属性
     * @param attribute 属性
     * @param pokemon 宝可梦
     * @return 属性值
     */
    public Object getAttribute(EnumPokeAttribute attribute, Pokemon pokemon) {
        switch (attribute) {
            case UUID:
                return pokemon.getUUID();
            case SPECIES:
                return pokemon.getSpecies().getName();
            case DISPLAY_NAME:
                return pokemon.getFormattedDisplayName().getString();
            case NICKNAME:
                return pokemon.getFormattedNickname() != null ? pokemon.getFormattedNickname().getString() : pokemon.getLocalizedName();
            case LOCALIZED_NAME:
                return pokemon.getLocalizedName();
            case DEX_NUMBER:
                return pokemon.getSpecies().getDex();
            case LEVEL:
                return pokemon.getPokemonLevel();
            case SHINY:
                return pokemon.isShiny() ? "是" : "否";
            case ABILITY:
                return pokemon.getAbility().getLocalizedName();
            case GROWTH:
                return pokemon.getGrowth().getLocalizedName();
            case NATURE:
                return pokemon.getNature().getLocalizedName();
            case MINT_NATURE:
                return (pokemon.getMintNature() != null ? pokemon.getMintNature().getLocalizedName() : "无");
            case GENDER:
                return pokemon.getGender().getLocalizedName();
            case HELD_ITEM:
                return pokemon.getHeldItem().getDisplayName().getString();
            case POKEBALL:
                return pokemon.getBall().getLocalizedName();
            case FRIENDSHIP:
                return pokemon.getFriendship();
            case CUSTOM_TEXTURE:
                return pokemon.getPalette() != null ? pokemon.getPalette().getLocalizedName() : "无";
            case EGG:
                return pokemon.isEgg() ? "是" : "否";
            case EGG_GROUP:
                if(pokemon.getForm().getEggGroups() == null){
                    return "无";
                }
                return pokemon.getForm().getEggGroups().stream().map(ITranslatable::getLocalizedName).collect(Collectors.toList());
            case NUM_CLONED:
                return pokemon.getExtraStats() instanceof MewStats ? ((MewStats) pokemon.getExtraStats()).numCloned : "不能克隆";
            case NUM_ENCHANTED:
                return pokemon.getExtraStats() instanceof LakeTrioStats ? ((LakeTrioStats) pokemon.getExtraStats()).numEnchanted : "不能附魔";
            case FORM:
                return pokemon.getForm().getLocalizedName();
            case OT_NAME:
                return pokemon.getOriginalTrainer() != null ? pokemon.getOriginalTrainer() : "无";
            case OT_UUID:
                return pokemon.getOriginalTrainerUUID() != null ? pokemon.getOriginalTrainerUUID().toString() : "无";
            case OWNER_NAME:
                return pokemon.getOwnerName();
            case OWNER_UUID:
                return pokemon.getOwnerPlayerUUID() != null ? pokemon.getOwnerPlayerUUID().toString() : "无";
            case HP:
                return pokemon.getStats().getHP();
            case ATTACK:
                return pokemon.getStats().getAttack();
            case DEFENCE:
                return pokemon.getStats().getDefense();
            case SPECIAL_ATTACK:
                return pokemon.getStats().getSpecialAttack();
            case SPECIAL_DEFENCE:
                return pokemon.getStats().getSpecialDefense();
            case SPEED:
                return pokemon.getStats().getSpeed();
            case IV_HP:
                return pokemon.getIVs().getStat(BattleStatsType.HP);
            case IV_ATTACK:
                return pokemon.getIVs().getStat(BattleStatsType.ATTACK);
            case IV_DEFENCE:
                return pokemon.getIVs().getStat(BattleStatsType.DEFENSE);
            case IV_SPECIAL_ATTACK:
                return pokemon.getIVs().getStat(BattleStatsType.SPECIAL_ATTACK);
            case IV_SPECIAL_DEFENCE:
                return pokemon.getIVs().getStat(BattleStatsType.SPECIAL_DEFENSE);
            case IV_SPEED:
                return pokemon.getIVs().getStat(BattleStatsType.SPEED);
            case IV_TOTAL:
                return pokemon.getIVs().getTotal();
            case IV_PERCENTAGE:
                return String.format("%.2f", pokemon.getIVs().getTotal() / 186.0 * 100.0).replace(".00", "");
            case EV_HP:
                return pokemon.getEVs().getStat(BattleStatsType.HP);
            case EV_ATTACK:
                return pokemon.getEVs().getStat(BattleStatsType.ATTACK);
            case EV_DEFENCE:
                return pokemon.getEVs().getStat(BattleStatsType.DEFENSE);
            case EV_SPECIAL_ATTACK:
                return pokemon.getEVs().getStat(BattleStatsType.SPECIAL_ATTACK);
            case EV_SPECIAL_DEFENCE:
                return pokemon.getEVs().getStat(BattleStatsType.SPECIAL_DEFENSE);
            case EV_SPEED:
                return pokemon.getEVs().getStat(BattleStatsType.SPEED);
            case EV_TOTAL:
                return pokemon.getEVs().getTotal();
            case EV_PERCENTAGE:
                return String.format("%.2f", pokemon.getEVs().getTotal() / 510.0 * 100.0).replace(".00", "");
            case HT_HP:
                return pokemon.getIVs().isHyperTrained(BattleStatsType.HP) ? 31 : 0;
            case HT_ATTACK:
                return pokemon.getIVs().isHyperTrained(BattleStatsType.ATTACK) ? 31 : 0;
            case HT_DEFENCE:
                return pokemon.getIVs().isHyperTrained(BattleStatsType.DEFENSE) ? 31 : 0;
            case HT_SPECIAL_ATTACK:
                return pokemon.getIVs().isHyperTrained(BattleStatsType.SPECIAL_ATTACK) ? 31 : 0;
            case HT_SPECIAL_DEFENCE:
                return pokemon.getIVs().isHyperTrained(BattleStatsType.SPECIAL_DEFENSE) ? 31 : 0;
            case HT_SPEED:
                return pokemon.getIVs().isHyperTrained(BattleStatsType.SPEED) ? 31 : 0;
            case SPEC_HP:
                return pokemon.getForm().getBattleStats().getStat(BattleStatsType.HP);
            case SPEC_ATTACK:
                return pokemon.getForm().getBattleStats().getStat(BattleStatsType.ATTACK);
            case SPEC_DEFENCE:
                return pokemon.getForm().getBattleStats().getStat(BattleStatsType.DEFENSE);
            case SPEC_SPECIAL_ATTACK:
                return pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPECIAL_ATTACK);
            case SPEC_SPECIAL_DEFENCE:
                return pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPECIAL_DEFENSE);
            case SPEC_SPEED:
                return pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPEED);
            case MOVE_1:
                return (pokemon.getMoveset().get(0) != null ? pokemon.getMoveset().get(0).getMove().getLocalizedName() : "无");
            case MOVE_2:
                return (pokemon.getMoveset().get(1) != null ? pokemon.getMoveset().get(1).getMove().getLocalizedName() : "无");
            case MOVE_3:
                return (pokemon.getMoveset().get(2) != null ? pokemon.getMoveset().get(2).getMove().getLocalizedName() : "无");
            case MOVE_4:
                return (pokemon.getMoveset().get(3) != null ? pokemon.getMoveset().get(3).getMove().getLocalizedName() : "无");
            case LEGENDARY:
                return (pokemon.isLegendary() || pokemon.isMythical()) ? "是" : "否";
            case ULTRA_BEAST:
                return pokemon.isUltraBeast() ? "是" : "否";
            case HIDE_ABILITY:
                return pokemon.getAbilitySlot() == 2 ? "是" : "否";
            case TRADEABLE:
                return pokemon.hasFlag("untradeable") ? "否" : "是";
            case BREEDABLE:
                return pokemon.hasFlag("unbreedable") ? "否" : "是";
            case CATCHABLE:
                return pokemon.hasFlag("uncatchable") ? "否" : "是";
            default:
                return null;
        }
    }

}