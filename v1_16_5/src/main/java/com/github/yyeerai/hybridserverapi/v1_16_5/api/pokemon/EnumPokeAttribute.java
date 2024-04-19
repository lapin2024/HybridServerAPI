package com.github.yyeerai.hybridserverapi.v1_16_5.api.pokemon;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;

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
    CUSTOM_TEXTURE,
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
     * DISPLAY_NAME: 显示名称 返回String
     * UUID: UUID 返回String
     * LEVEL: 等级 返回Integer
     * SHINY: 闪光 返回Boolean
     * ABILITY: 特性 返回String
     * GROWTH: 体型 返回String
     * NATURE: 性格 返回String
     * MINT_NATURE: 薄荷性格 返回String
     * GENDER: 性别 返回String
     * HELD_ITEM: 持有物 返回String
     * POKEBALL: 精灵球 返回String
     * FRIENDSHIP: 亲密度 返回Integer
     * HP ~ HT_SPEED : 各项基础值 返回Integer
     * MOVE_1 ~ MOVE_4: 招式 返回String
     * TRADEABLE: 可交易 返回Boolean
     * BREEDABLE: 可繁殖 返回Boolean
     * CATCHABLE: 可捕捉 返回Boolean
     * FORM: 形态 返回String
     */
    public Object getAttribute(Pokemon pokemon) {
        switch (this) {
            case DISPLAY_NAME:
                return pokemon.getFormattedDisplayName().getString();
            case NICKNAME:
                return pokemon.getNickname();
            case UUID:
                return pokemon.getUUID();
            case LEVEL:
                return pokemon.getPokemonLevel();
            case SHINY:
                return pokemon.isShiny();
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
            case OT_NAME:
                return pokemon.getOriginalTrainer() != null ? pokemon.getOriginalTrainer(): "无";
            case OT_UUID:
                return pokemon.getOriginalTrainerUUID() != null ? pokemon.getOriginalTrainerUUID().toString() : "无";
            case CUSTOM_TEXTURE:
                return pokemon.getPalette() != null ? pokemon.getPalette().getLocalizedName() : "无";
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
                return pokemon.hasFlag("untradeable");
            case BREEDABLE:
                return pokemon.hasFlag("unbreedable");
            case CATCHABLE:
                return pokemon.hasFlag("uncatchable");
            case FORM:
                return pokemon.getForm().getLocalizedName();
            default:
                return null;
        }
    }

}