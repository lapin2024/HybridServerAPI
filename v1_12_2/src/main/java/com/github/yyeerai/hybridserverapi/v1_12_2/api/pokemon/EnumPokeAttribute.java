package com.github.yyeerai.hybridserverapi.v1_12_2.api.pokemon;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;

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
    CUSTOM_TEXTURE,
    OT_NAME,
    OT_UUID,
    HP,
    ATTACK,
    DEFENSE,
    SPECIAL_ATTACK,
    SPECIAL_DEFENSE,
    SPEED,
    IV_HP,
    IV_ATTACK,
    IV_DEFENSE,
    IV_SPECIAL_ATTACK,
    IV_SPECIAL_DEFENSE,
    IV_SPEED,
    EV_HP,
    EV_ATTACK,
    EV_DEFENSE,
    EV_SPECIAL_ATTACK,
    EV_SPECIAL_DEFENSE,
    EV_SPEED,
    HT_HP,
    HT_ATTACK,
    HT_DEFENSE,
    HT_SPECIAL_ATTACK,
    HT_SPECIAL_DEFENSE,
    HT_SPEED,
    MOVE_1,
    MOVE_2,
    MOVE_3,
    MOVE_4,
    TRADEABLE,
    BREEDABLE,
    CATCHABLE;


    /**
     * 获取属性
     *
     * @param pokemon 宝可梦
     * @return 属性值
     * UUID: UUID 返回String
     * DISPLAY_NAME: 显示名称 返回String
     * NICKNAME: 昵称 返回String
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
     * CUSTOM_TEXTURE: 自定义纹理 返回String
     * OT_NAME: 原始训练师名称 返回String
     * OT_UUID: 原始训练师UUID 返回String
     * HP ~ HT_SPEED : 各项基础值 返回Integer
     * MOVE_1 ~ MOVE_4: 招式 返回String
     * TRADEABLE: 可交易 返回Boolean
     * BREEDABLE: 可繁殖 返回Boolean
     * CATCHABLE: 可捕捉 返回Boolean
     */
    public Object getAttribute(Pokemon pokemon) {
        switch (this) {
            case UUID:
                return pokemon.getUUID().toString();
            case DISPLAY_NAME:
                return pokemon.getDisplayName();
            case NICKNAME:
                return pokemon.getNickname() != null ? pokemon.getNickname() : pokemon.getDisplayName();
            case LEVEL:
                return pokemon.getLevel();
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
                return pokemon.getHeldItem().getDisplayName();
            case POKEBALL:
                return pokemon.getCaughtBall().getLocalizedName();
            case FRIENDSHIP:
                return pokemon.getFriendship();
            case CUSTOM_TEXTURE:
                return pokemon.getCustomTexture() != null ? pokemon.getCustomTexture() : "无";
            case OT_NAME:
                return pokemon.getOriginalTrainer() != null ? pokemon.getOriginalTrainer() : "无";
            case OT_UUID:
                return pokemon.getOriginalTrainerUUID() != null ? pokemon.getOriginalTrainerUUID().toString() : "无";
            case HP:
                return pokemon.getStats().get(StatsType.HP);
            case ATTACK:
                return pokemon.getStats().get(StatsType.Attack);
            case DEFENSE:
                return pokemon.getStats().get(StatsType.Defence);
            case SPECIAL_ATTACK:
                return pokemon.getStats().get(StatsType.SpecialAttack);
            case SPECIAL_DEFENSE:
                return pokemon.getStats().get(StatsType.SpecialDefence);
            case SPEED:
                return pokemon.getStats().get(StatsType.Speed);
            case IV_HP:
                return pokemon.getIVs().getStat(StatsType.HP);
            case IV_ATTACK:
                return pokemon.getIVs().getStat(StatsType.Attack);
            case IV_DEFENSE:
                return pokemon.getIVs().getStat(StatsType.Defence);
            case IV_SPECIAL_ATTACK:
                return pokemon.getIVs().getStat(StatsType.SpecialAttack);
            case IV_SPECIAL_DEFENSE:
                return pokemon.getIVs().getStat(StatsType.SpecialDefence);
            case IV_SPEED:
                return pokemon.getIVs().getStat(StatsType.Speed);
            case EV_HP:
                return pokemon.getEVs().getStat(StatsType.HP);
            case EV_ATTACK:
                return pokemon.getEVs().getStat(StatsType.Attack);
            case EV_DEFENSE:
                return pokemon.getEVs().getStat(StatsType.Defence);
            case EV_SPECIAL_ATTACK:
                return pokemon.getEVs().getStat(StatsType.SpecialAttack);
            case EV_SPECIAL_DEFENSE:
                return pokemon.getEVs().getStat(StatsType.SpecialDefence);
            case EV_SPEED:
                return pokemon.getEVs().getStat(StatsType.Speed);
            case HT_HP:
                return pokemon.getIVs().isHyperTrained(StatsType.HP) ? 31 : 0;
            case HT_ATTACK:
                return pokemon.getIVs().isHyperTrained(StatsType.Attack) ? 31 : 0;
            case HT_DEFENSE:
                return pokemon.getIVs().isHyperTrained(StatsType.Defence) ? 31 : 0;
            case HT_SPECIAL_ATTACK:
                return pokemon.getIVs().isHyperTrained(StatsType.SpecialAttack) ? 31 : 0;
            case HT_SPECIAL_DEFENSE:
                return pokemon.getIVs().isHyperTrained(StatsType.SpecialDefence) ? 31 : 0;
            case HT_SPEED:
                return pokemon.getIVs().isHyperTrained(StatsType.Speed) ? 31 : 0;
            case MOVE_1:
                return (pokemon.getMoveset().get(0) != null ? pokemon.getMoveset().get(0).getMove().getLocalizedName() : "无");
            case MOVE_2:
                return (pokemon.getMoveset().get(1) != null ? pokemon.getMoveset().get(1).getMove().getLocalizedName() : "无");
            case MOVE_3:
                return (pokemon.getMoveset().get(2) != null ? pokemon.getMoveset().get(2).getMove().getLocalizedName() : "无");
            case MOVE_4:
                return (pokemon.getMoveset().get(3) != null ? pokemon.getMoveset().get(3).getMove().getLocalizedName() : "无");
            case TRADEABLE:
                return pokemon.hasSpecFlag("untradeable");
            case BREEDABLE:
                return pokemon.hasSpecFlag("unbreedable");
            case CATCHABLE:
                return pokemon.hasSpecFlag("uncatchable");
            default:
                return null;
        }
    }

}