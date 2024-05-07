package com.github.yyeerai.hybridserverapi.v1_16_5.api.pokemon;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.pokemon.stats.extraStats.LakeTrioStats;
import com.pixelmonmod.pixelmon.api.pokemon.stats.extraStats.MewStats;

@SuppressWarnings("unused")
public enum EnumPokeAttribute {
    UUID,
    DISPLAY_NAME,
    NICKNAME,
    LEVEL,
    SHINY,
    ABILITY,
    GROWTH,
    NATURE,
    MINT_NATURE,
    GENDER,
    HELD_ITEM,
    POKEBALL,
    FRIENDSHIP,
    OT_NAME,
    OT_UUID,
    OWNER_NAME,
    OWNER_UUID,
    CUSTOM_TEXTURE,
    NUM_CLONED,
    NUM_ENCHANTED,
    HP,
    ATTACK,
    DEFENCE,
    SPECIAL_ATTACK,
    SPECIAL_DEFENCE,
    SPEED,
    IV_HP,
    IV_ATTACK,
    IV_DEFENCE,
    IV_SPECIAL_ATTACK,
    IV_SPECIAL_DEFENCE,
    IV_SPEED,
    EV_HP,
    EV_ATTACK,
    EV_DEFENCE,
    EV_SPECIAL_ATTACK,
    EV_SPECIAL_DEFENCE,
    EV_SPEED,
    HT_HP,
    HT_ATTACK,
    HT_DEFENCE,
    HT_SPECIAL_ATTACK,
    HT_SPECIAL_DEFENCE,
    HT_SPEED,
    MOVE_1,
    MOVE_2,
    MOVE_3,
    MOVE_4,
    TRADEABLE,
    BREEDABLE,
    CATCHABLE,
    FORM;


    /**
     * 获取属性
     *
     * @param pokemon 宝可梦
     * @return 属性值
     * UUID: 宝可梦的UUID (String)
     * DISPLAY_NAME: 宝可梦的显示名称 (String)
     * NICKNAME: 宝可梦的昵称 (String)
     * LEVEL: 宝可梦的等级 (int)
     * SHINY: 宝可梦是否闪光 (boolean)
     * ABILITY: 宝可梦的特性 (String)
     * GROWTH: 宝可梦的大小 (String)
     * NATURE: 宝可梦的性格 (String)
     * MINT_NATURE: 宝可梦的薄荷性格 (String)
     * GENDER: 宝可梦的性别 (String)
     * HELD_ITEM: 宝可梦的携带物品 (String)
     * POKEBALL: 宝可梦的精灵球 (String)
     * FRIENDSHIP: 宝可梦的亲密度 (int)
     * CUSTOM_TEXTURE: 宝可梦的自定义皮肤 (String)
     * NUM_CLONED: 宝可梦的克隆次数 (int)
     * NUM_ENCHANTED: 宝可梦的附魔次数 (int)
     * OT_NAME: 宝可梦的原始训练师 (String)
     * OT_UUID: 宝可梦的原始训练师UUID (String)
     * OWNER_NAME: 宝可梦的拥有者 (String)
     * OWNER_UUID: 宝可梦的拥有者UUID (String)
     * HP: 宝可梦的HP (int)
     * ATTACK: 宝可梦的攻击 (int)
     * DEFENCE: 宝可梦的防御 (int)
     * SPECIAL_ATTACK: 宝可梦的特攻 (int)
     * SPECIAL_DEFENCE: 宝可梦的特防 (int)
     * SPEED: 宝可梦的速度 (int)
     * IV_HP: 宝可梦的个体值HP (int)
     * IV_ATTACK: 宝可梦的个体值攻击 (int)
     * IV_DEFENCE: 宝可梦的个体值防御 (int)
     * IV_SPECIAL_ATTACK: 宝可梦的个体值特攻 (int)
     * IV_SPECIAL_DEFENCE: 宝可梦的个体值特防 (int)
     * IV_SPEED: 宝可梦的个体值速度 (int)
     * EV_HP: 宝可梦的努力值HP (int)
     * EV_ATTACK: 宝可梦的努力值攻击 (int)
     * EV_DEFENCE: 宝可梦的努力值防御 (int)
     * EV_SPECIAL_ATTACK: 宝可梦的努力值特攻 (int)
     * EV_SPECIAL_DEFENCE: 宝可梦的努力值特防 (int)
     * EV_SPEED: 宝可梦的努力值速度 (int)
     * HT_HP: 宝可梦的Hyper HP (int)
     * HT_ATTACK: 宝可梦的Hyper 攻击 (int)
     * HT_DEFENCE: 宝可梦的Hyper 防御 (int)
     * HT_SPECIAL_ATTACK: 宝可梦的Hyper 特攻 (int)
     * HT_SPECIAL_DEFENCE: 宝可梦的Hyper 特防 (int)
     * HT_SPEED: 宝可梦的Hyper 速度 (int)
     * MOVE_1: 宝可梦的技能1 (String)
     * MOVE_2: 宝可梦的技能2 (String)
     * MOVE_3: 宝可梦的技能3 (String)
     * MOVE_4: 宝可梦的技能4 (String)
     * TRADEABLE: 宝可梦是否可交易 (boolean)
     * BREEDABLE: 宝可梦是否可繁殖 (boolean)
     * CATCHABLE: 宝可梦是否可捕捉 (boolean)
     * FORM: 宝可梦的形态 (String)
     * @see Pokemon
     */
    public Object getAttribute(Pokemon pokemon) {
        switch (this) {
            case UUID:
                return pokemon.getUUID();
            case DISPLAY_NAME:
                return pokemon.getFormattedDisplayName().getString();
            case NICKNAME:
                return pokemon.getFormattedNickname() != null ? pokemon.getFormattedNickname().getString() : pokemon.getLocalizedName();
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
                return pokemon.getOwnerPlayerUUID().toString();
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
            case MOVE_1:
                return (pokemon.getMoveset().get(0) != null ? pokemon.getMoveset().get(0).getMove().getLocalizedName() : "无");
            case MOVE_2:
                return (pokemon.getMoveset().get(1) != null ? pokemon.getMoveset().get(1).getMove().getLocalizedName() : "无");
            case MOVE_3:
                return (pokemon.getMoveset().get(2) != null ? pokemon.getMoveset().get(2).getMove().getLocalizedName() : "无");
            case MOVE_4:
                return (pokemon.getMoveset().get(3) != null ? pokemon.getMoveset().get(3).getMove().getLocalizedName() : "无");
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