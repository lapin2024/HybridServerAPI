package com.github.yyeerai.hybridserverapi.common.enums;

/**
 * <li>UUID: 宝可梦的UUID (String)<li/>
 * <li>SPECIES: 宝可梦的物种 (String)<li/>
 * <li>DISPLAY_NAME: 宝可梦的显示名称 (String)<li/>
 * <li>NICKNAME: 宝可梦的昵称 (String)<li/>
 * <li>LOCALIZED_NAME: 宝可梦的本地化名称 (String)<li/>
 * <li>DEX_NUMBER: 宝可梦的图鉴编号 (int)<li/>
 * <li>LEVEL: 宝可梦的等级 (int)<li/>
 * <li>SHINY: 宝可梦是否闪光 (boolean)<li/>
 * <li>ABILITY: 宝可梦的特性 (String)<li/>
 * <li>GROWTH: 宝可梦的大小 (String)<li/>
 * <li>NATURE: 宝可梦的性格 (String)<li/>
 * <li>MINT_NATURE: 宝可梦的薄荷性格 (String)<li/>
 * <li>GENDER: 宝可梦的性别 (String)<li/>
 * <li>HELD_ITEM: 宝可梦的携带物品 (String)<li/>
 * <li>POKEBALL: 宝可梦的精灵球 (String)<li/>
 * <li>FRIENDSHIP: 宝可梦的亲密度 (int)<li/>
 * <li>CUSTOM_TEXTURE: 宝可梦的自定义皮肤 (String)<li/>
 * <li>EGG: 宝可梦是否为蛋 (boolean)<li/>
 * <li>EGG_GROUP: 宝可梦的蛋组 (String)<li/>
 * <li>NUM_CLONED: 宝可梦的克隆次数 (int)<li/>
 * <li>NUM_ENCHANTED: 宝可梦的附魔次数 (int)<li/>
 * <li>OT_NAME: 宝可梦的原始训练师 (String)<li/>
 * <li>OT_UUID: 宝可梦的原始训练师UUID (String)<li/>
 * <li>OWNER_NAME: 宝可梦的拥有者 (String)<li/>
 * <li>OWNER_UUID: 宝可梦的拥有者UUID (String)<li/>
 * <li>HP: 宝可梦的HP (int)<li/>
 * <li>ATTACK: 宝可梦的攻击 (int)<li/>
 * <li>DEFENCE: 宝可梦的防御 (int)<li/>
 * <li>SPECIAL_ATTACK: 宝可梦的特攻 (int)<li/>
 * <li>SPECIAL_DEFENCE: 宝可梦的特防 (int)<li/>
 * <li>SPEED: 宝可梦的速度 (int)<li/>
 * <li>IV_HP: 宝可梦的个体值HP (int)<li/>
 * <li>IV_ATTACK: 宝可梦的个体值攻击 (int)<li/>
 * <li>IV_DEFENCE: 宝可梦的个体值防御 (int)<li/>
 * <li>IV_SPECIAL_ATTACK: 宝可梦的个体值特攻 (int)<li/>
 * <li>IV_SPECIAL_DEFENCE: 宝可梦的个体值特防 (int)<li/>
 * <li>IV_SPEED: 宝可梦的个体值速度 (int)<li/>
 * <li>IV_TOTAL: 宝可梦的个体值总和 (int)<li/>
 * <li>IV_PERCENTAGE: 宝可梦的个体值百分比 (String)<li/>
 * <li>EV_HP: 宝可梦的努力值HP (int)<li/>
 * <li>EV_ATTACK: 宝可梦的努力值攻击 (int)<li/>
 * <li>EV_DEFENCE: 宝可梦的努力值防御 (int)<li/>
 * <li>EV_SPECIAL_ATTACK: 宝可梦的努力值特攻 (int)<li/>
 * <li>EV_SPECIAL_DEFENCE: 宝可梦的努力值特防 (int)<li/>
 * <li>EV_SPEED: 宝可梦的努力值速度 (int)<li/>
 * <li>EV_TOTAL: 宝可梦的努力值总和 (int)<li/>
 * <li>EV_PERCENTAGE: 宝可梦的努力值百分比 (String)<li/>
 * <li>HT_HP: 宝可梦的Hyper HP (int)<li/>
 * <li>HT_ATTACK: 宝可梦的Hyper 攻击 (int)<li/>
 * <li>HT_DEFENCE: 宝可梦的Hyper 防御 (int)<li/>
 * <li>HT_SPECIAL_ATTACK: 宝可梦的Hyper 特攻 (int)<li/>
 * <li>HT_SPECIAL_DEFENCE: 宝可梦的Hyper 特防 (int)<li/>
 * <li>HT_SPEED: 宝可梦的Hyper 速度 (int)<li/>
 * <li>SPEC_HP: 宝可梦的种族值HP (int)<li/>
 * <li>SPEC_ATTACK: 宝可梦的种族值攻击 (int)<li/>
 * <li>SPEC_DEFENCE: 宝可梦的种族值防御 (int)<li/>
 * <li>SPEC_SPECIAL_ATTACK: 宝可梦的种族值特攻 (int)<li/>
 * <li>SPEC_SPECIAL_DEFENCE: 宝可梦的种族值特防 (int)<li/>
 * <li>SPEC_SPEED: 宝可梦的种族值速度 (int)<li/>
 * <li>MOVE_1: 宝可梦的技能1 (String)<li/>
 * <li>MOVE_2: 宝可梦的技能2 (String)<li/>
 * <li>MOVE_3: 宝可梦的技能3 (String)<li/>
 * <li>MOVE_4: 宝可梦的技能4 (String)<li/>
 * <li>LEGENDARY: 宝可梦是否传说 (boolean)<li/>
 * <li>ULTRA_BEAST: 宝可梦是否究极异兽 (boolean)<li/>
 * <li>HIDE_ABILITY: 宝可梦是否隐藏特性 (boolean)<li/>
 * <li>TRADEABLE: 宝可梦是否可交易 (boolean)<li/>
 * <li>BREEDABLE: 宝可梦是否可繁殖 (boolean)<li/>
 * <li>CATCHABLE: 宝可梦是否可捕捉 (boolean)<li/>
 * <li>FORM: 宝可梦的形态 (String)<li/>
 */
public enum EnumPokeAttribute {
    UUID,
    SPECIES,
    DISPLAY_NAME,
    NICKNAME,
    LOCALIZED_NAME,
    DEX_NUMBER,
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
    EGG,
    EGG_GROUP,
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
    IV_TOTAL,
    IV_PERCENTAGE,
    EV_HP,
    EV_ATTACK,
    EV_DEFENCE,
    EV_SPECIAL_ATTACK,
    EV_SPECIAL_DEFENCE,
    EV_SPEED,
    EV_TOTAL,
    EV_PERCENTAGE,
    HT_HP,
    HT_ATTACK,
    HT_DEFENCE,
    HT_SPECIAL_ATTACK,
    HT_SPECIAL_DEFENCE,
    HT_SPEED,
    SPEC_HP,
    SPEC_ATTACK,
    SPEC_DEFENCE,
    SPEC_SPECIAL_ATTACK,
    SPEC_SPECIAL_DEFENCE,
    SPEC_SPEED,
    MOVE_1,
    MOVE_2,
    MOVE_3,
    MOVE_4,
    TERASTAL_TYPE,
    TERASTAL_INDEX,
    LEGENDARY,
    ULTRA_BEAST,
    HIDE_ABILITY,
    TRADEABLE,
    BREEDABLE,
    CATCHABLE,
    FORM
}