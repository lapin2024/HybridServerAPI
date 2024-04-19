package com.github.yyeerai.hybridserverapi.v1_20_2.api.pokemon;

import com.github.yyeerai.hybridserverapi.v1_20_2.api.BaseApi;
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
        return switch (this) {
            case DISPLAY_NAME -> pokemon.getDisplayName();
            case NICKNAME -> pokemon.getNickname();
            case UUID -> pokemon.getUUID();
            case LEVEL -> pokemon.getPokemonLevel();
            case SHINY -> pokemon.isShiny();
            case ABILITY -> pokemon.getAbility().getLocalizedName();
            case GROWTH -> pokemon.getGrowth().getLocalizedName();
            case NATURE -> pokemon.getNature().getLocalizedName();
            case MINT_NATURE -> (pokemon.getMintNature() != null ? pokemon.getMintNature().getLocalizedName() : "无");
            case GENDER -> pokemon.getGender().getLocalizedName();
            case HELD_ITEM -> BaseApi.getBukkitItemStack(pokemon.getHeldItem()).getType().toString();
            case POKEBALL -> pokemon.getBall().getLocalizedName();
            case FRIENDSHIP -> pokemon.getFriendship();
            case OT_NAME -> pokemon.getOriginalTrainer() != null ? pokemon.getOriginalTrainer() : "无";
            case OT_UUID ->
                    pokemon.getOriginalTrainerUUID() != null ? pokemon.getOriginalTrainerUUID().toString() : "无";
            case CUSTOM_TEXTURE -> pokemon.getPalette() != null ? pokemon.getPalette().getLocalizedName() : "无";
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
            case EV_HP -> pokemon.getEVs().getStat(BattleStatsType.HP);
            case EV_ATTACK -> pokemon.getEVs().getStat(BattleStatsType.ATTACK);
            case EV_DEFENCE -> pokemon.getEVs().getStat(BattleStatsType.DEFENSE);
            case EV_SPECIAL_ATTACK -> pokemon.getEVs().getStat(BattleStatsType.SPECIAL_ATTACK);
            case EV_SPECIAL_DEFENCE -> pokemon.getEVs().getStat(BattleStatsType.SPECIAL_DEFENSE);
            case EV_SPEED -> pokemon.getEVs().getStat(BattleStatsType.SPEED);
            case HT_HP -> pokemon.getIVs().isHyperTrained(BattleStatsType.HP) ? 31 : 0;
            case HT_ATTACK -> pokemon.getIVs().isHyperTrained(BattleStatsType.ATTACK) ? 31 : 0;
            case HT_DEFENCE -> pokemon.getIVs().isHyperTrained(BattleStatsType.DEFENSE) ? 31 : 0;
            case HT_SPECIAL_ATTACK -> pokemon.getIVs().isHyperTrained(BattleStatsType.SPECIAL_ATTACK) ? 31 : 0;
            case HT_SPECIAL_DEFENCE -> pokemon.getIVs().isHyperTrained(BattleStatsType.SPECIAL_DEFENSE) ? 31 : 0;
            case HT_SPEED -> pokemon.getIVs().isHyperTrained(BattleStatsType.SPEED) ? 31 : 0;
            case MOVE_1 ->
                    (pokemon.getMoveset().get(0) != null ? pokemon.getMoveset().get(0).getMove().getLocalizedName() : "无");
            case MOVE_2 ->
                    (pokemon.getMoveset().get(1) != null ? pokemon.getMoveset().get(1).getMove().getLocalizedName() : "无");
            case MOVE_3 ->
                    (pokemon.getMoveset().get(2) != null ? pokemon.getMoveset().get(2).getMove().getLocalizedName() : "无");
            case MOVE_4 ->
                    (pokemon.getMoveset().get(3) != null ? pokemon.getMoveset().get(3).getMove().getLocalizedName() : "无");
            case TRADEABLE -> pokemon.hasFlag("untradeable");
            case BREEDABLE -> pokemon.hasFlag("unbreedable");
            case CATCHABLE -> pokemon.hasFlag("uncatchable");
            case FORM -> pokemon.getForm().getLocalizedName();
            default -> null;
        };
    }

}