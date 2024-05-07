package com.github.yyeerai.hybridserverapi.v1_20_1.api.pokemon;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yyeerai.hybridserverapi.v1_20_1.api.BaseApi;

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
        return switch (this) {
            case UUID -> pokemon.getUuid();
            case DISPLAY_NAME -> pokemon.getDisplayName().getString();
            case NICKNAME ->
                    pokemon.getNickname() != null ? pokemon.getNickname().getString() : pokemon.getDisplayName().getString();
            case LEVEL -> pokemon.getLevel();
            case SHINY -> pokemon.getShiny() ? "是" : "否";
            case ABILITY -> pokemon.getAbility().getDisplayName();
            case GROWTH, CUSTOM_TEXTURE, NUM_ENCHANTED, NUM_CLONED, HT_SPEED, HT_SPECIAL_DEFENCE, HT_SPECIAL_ATTACK,
                 HT_DEFENCE, HT_ATTACK, HT_HP -> "暂时不支持";
            case NATURE -> pokemon.getNature().getDisplayName();
            case MINT_NATURE -> (pokemon.getMintedNature() != null ? pokemon.getMintedNature().getDisplayName() : "无");
            case GENDER -> pokemon.getGender().getShowdownName();
            case HELD_ITEM -> BaseApi.getBukkitItemStack(pokemon.heldItem()).getType().toString();
            case POKEBALL -> pokemon.getCaughtBall().getName().toString();
            case FRIENDSHIP -> pokemon.getFriendship();
            case OT_NAME -> pokemon.getOwnerPlayer() != null ? pokemon.getOwnerPlayer().getName().getString() : "无";
            case OT_UUID -> pokemon.getOwnerUUID() != null ? pokemon.getOwnerUUID().toString() : "无";
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
            case EV_HP -> pokemon.getEvs().get(Stats.HP);
            case EV_ATTACK -> pokemon.getEvs().get(Stats.ATTACK);
            case EV_DEFENCE -> pokemon.getEvs().get(Stats.DEFENCE);
            case EV_SPECIAL_ATTACK -> pokemon.getEvs().get(Stats.SPECIAL_ATTACK);
            case EV_SPECIAL_DEFENCE -> pokemon.getEvs().get(Stats.SPECIAL_DEFENCE);
            case EV_SPEED -> pokemon.getEvs().get(Stats.SPEED);
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
            case TRADEABLE -> pokemon.hasLabels("untradeable") ? "否" : "是"; //此方法需要验证
            case BREEDABLE -> pokemon.hasLabels("unbreedable") ? "否" : "是";
            case CATCHABLE -> pokemon.hasLabels("uncatchable") ? "否" : "是";
            case FORM -> pokemon.getForm().getName();
        };
    }

}