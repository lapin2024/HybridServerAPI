package com.github.yyeerai.hybridserverapi.v1_20_1.api.pokemon;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.pokemon.egg.EggGroup;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yyeerai.hybridserverapi.v1_20_1.Main;
import com.github.yyeerai.hybridserverapi.v1_20_1.api.BaseApi;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PapiHook extends PlaceholderExpansion {

    private final Main main;

    public PapiHook(Main main) {
        this.main = main;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "hsa";
    }

    @Override
    public @NotNull String getAuthor() {
        return this.main.getPlugin().getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return this.main.getPlugin().getDescription().getVersion();
    }

    /*
    %hsa_wins%	玩家对战胜场
    %hsa_losses%	玩家对战败场
    %hsa_totalbattle%	玩家对战总计场次
    %hsa_totalexp%	玩家总计获得经验
    %hsa_totalkills%	玩家总计击杀
    %hsa_currentexp%	玩家当前经验
    %hsa_currentkills%	玩家当前击杀
    %hsa_totalbred%	玩家总计繁殖
    %hsa_totalhatched%	玩家总计孵蛋
    %hsa_totalevolved%	玩家总计进化
    %hsa_seen% 玩家看见的宝可梦数量
    %hsa_caught%	返回玩家抓的宝可梦总数
    %hsa_size% 图鉴大小
    %hsa_percent%	图鉴收集完成度
    %hsa_percent_format%	图鉴收集完成度格式化
    %hsa_pokemoney%  - 玩家的宝可梦币

    %hsa_haspokemon_[pokename]%	在队伍中是否有指定name的宝可梦,有返回位置没有返回false
    %hsa_original_haspokemon_[pokename]%	在队伍中或pc中是否有原始训练师是当前玩家的指定name的宝可梦,有返回位置,没有返回false
    %hsa_name_[slot]%	指定队伍位置的宝可梦种类
    %hsa_localname_[slot]%	指定队伍位置的宝可梦本地名称
    %hsa_nickname_[slot]%	指定队伍位置的宝可梦昵称(没有昵称返回本地名称)
    %hsa_form_[slot]%	指定队伍位置的宝可梦形态
    %hsa_original_[slot]%	队伍指定位置的宝可梦的原始训练师
    %hsa_egggroup_[slot]%	队伍指定位置的宝可梦的蛋组
    %hsa_isegg_[slot]%	队伍指定位置的宝可梦是否是蛋
    %hsa_eggcycles_[slot]% 队伍指定位置的蛋的剩余孵化周期
    %hsa_shiny_[slot]%	指定队伍位置宝可梦是否是闪光
    %hsa_pokeball_[slot]%	指定队伍位置宝可梦的精灵球
    %hsa_cantrade_[slot]%	指定队伍位置宝可梦能否用于交易
    %hsa_canbreed_[slot]%	指定队伍位置宝可梦能否繁殖
    %hsa_customtexture_[slot]%	指定队伍位置宝可梦的自定义材质
    %hsa_pokedex_[slot]%	指定队伍位置宝可梦的图鉴值
    %hsa_islegendary_[slot]%	指定位置是否是神兽
    %hsa_isultrabeast_[slot]% 指定位置是否是异兽
    %hsa_move_[slot]_[index]%	指定队伍宝可梦指定栏位的技能名称index范围[1,4],没有技能返回空
    %hsa_partysize%	队伍中宝可梦的数量
    %hsa_partyegg% - 队伍中宝可梦的蛋数量

    //%hsa_info_[slot]_[信息名称]%	指定队伍位置的宝可梦的指定信息
    //信息列表:
    //  name: 宝可梦名称
    //  level,type,growth,nature, mintnature, ability,gender(等级,类型,体型,性格, 薄荷性格,特性,性别)
    //  evhp,evattack,evdefence,evsattack,evsdefence,evspeed,evtotal,(努力值)
    //  ivhp,ivattack,ivdefence, ivsattack,ivsdefence,ivspeed,ivtotal,(个体值)
    //  hthp,htattack,htdefence, htsattack,htsdefence,htspeed,httotal,(王冠个体值)
    //  hp,attack,defence,sattack,sdefence, speed,total(种族值)
   // nhp,nattack,ndefence,nsattack,nsdefence,nspeed,ntotal(当前属性值)
  //  friendship(亲密度),helditem(携带道具),palette(精灵皮肤)

    */

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {

        if (player == null) return "null";

        if (params.startsWith("wins") || params.startsWith("losses") || params.startsWith("totalbattle") || params.startsWith("totalexp") || params.startsWith("totalkills") || params.startsWith("currentexp") || params.startsWith("currentkills") || params.startsWith("totalbred") || params.startsWith("totalhatched") || params.startsWith("totalevolved") || params.startsWith("seen") || params.startsWith("caught") || params.startsWith("size") || params.startsWith("percent") || params.startsWith("percent_format") || params.startsWith("pokemoney")) {
            return getPartyRust(params);
        }
        if (params.startsWith("haspokemon") || params.startsWith("original_haspokemon") || params.startsWith("name") || params.startsWith("localname") || params.startsWith("nickname") || params.startsWith("form") || params.startsWith("original") || params.startsWith("egggroup") || params.startsWith("isegg") || params.startsWith("eggcycles") || params.startsWith("shiny") || params.startsWith("pokeball") || params.startsWith("cantrade") || params.startsWith("canbreed") || params.startsWith("customtexture") || params.startsWith("pokedex") || params.startsWith("move") || params.startsWith("islegendary") || params.startsWith("isultrabeast") || params.startsWith("partysize") || params.startsWith("partyegg") || params.startsWith("pcsize") || params.startsWith("pcegg")) {
            return getPlayerPokemonRust(main.getPokemonApi().getPartyStorage(player), params);
        }
        if (params.startsWith("info")) {
            return getPokemonInfoRust(main.getPokemonApi().getPartyStorage(player), params);
        }
        return "null";
    }


    private String getPokemonInfoRust(PlayerPartyStore playerPartyStorage, String params) {
        String[] split = params.split("_");
        int slot = (Integer.parseInt(split[1]) - 1);
        if (slot < 0 || slot > 6) return "null";
        Pokemon pokemon = playerPartyStorage.get(slot);
        if (pokemon == null) return "null";
        switch (split[2]) {
            case "name":
                return pokemon.getSpecies().getName();
            case "level":
                return String.valueOf(pokemon.getLevel());
            case "type":
                Iterable<ElementalType> types = pokemon.getSpecies().getTypes();
                StringBuilder sb = new StringBuilder();
                for (ElementalType type : types) {
                    sb.append(type.getDisplayName()).append(" ");
                }
                return sb.toString();
            case "form":
                return pokemon.getForm().getName();
            case "shiny":
                return String.valueOf(pokemon.getShiny());
            case "growth", "palette", "htspeed", "htsdefence", "htsattack", "htdefence", "htattack", "hthp":
                return "暂不支持";
            case "nature":
                return pokemon.getNature().getDisplayName();
            case "mintnature":
                return pokemon.getMintedNature() == null ? "null" : pokemon.getMintedNature().getDisplayName();
            case "ability":
                return pokemon.getAbility().getDisplayName();
            case "gender":
                return pokemon.getGender().getShowdownName();
            case "evhp":
                return String.valueOf(pokemon.getEvs().get(Stats.HP));
            case "evattack":
                return String.valueOf(pokemon.getEvs().get(Stats.ATTACK));
            case "evdefence":
                return String.valueOf(pokemon.getEvs().get(Stats.DEFENCE));
            case "evsattack":
                return String.valueOf(pokemon.getEvs().get(Stats.SPECIAL_ATTACK));
            case "evsdefence":
                return String.valueOf(pokemon.getEvs().get(Stats.SPECIAL_DEFENCE));
            case "evspeed":
                return String.valueOf(pokemon.getEvs().get(Stats.SPEED));
            case "evtotal":
                return String.valueOf(pokemon.getEvs().get(Stats.HP) + pokemon.getEvs().get(Stats.ATTACK) + pokemon.getEvs().get(Stats.DEFENCE) + pokemon.getEvs().get(Stats.SPECIAL_ATTACK) + pokemon.getEvs().get(Stats.SPECIAL_DEFENCE) + pokemon.getEvs().get(Stats.SPEED));
            case "ivhp":
                return String.valueOf(pokemon.getIvs().get(Stats.HP));
            case "ivattack":
                return String.valueOf(pokemon.getIvs().get(Stats.ATTACK));
            case "ivdefence":
                return String.valueOf(pokemon.getIvs().get(Stats.DEFENCE));
            case "ivsattack":
                return String.valueOf(pokemon.getIvs().get(Stats.SPECIAL_ATTACK));
            case "ivsdefence":
                return String.valueOf(pokemon.getIvs().get(Stats.SPECIAL_DEFENCE));
            case "ivspeed":
                return String.valueOf(pokemon.getIvs().get(Stats.SPEED));
            case "ivtotal":
                return String.valueOf(pokemon.getIvs().get(Stats.HP) + pokemon.getIvs().get(Stats.ATTACK) + pokemon.getIvs().get(Stats.DEFENCE) + pokemon.getIvs().get(Stats.SPECIAL_ATTACK) + pokemon.getIvs().get(Stats.SPECIAL_DEFENCE) + pokemon.getIvs().get(Stats.SPEED));
            case "hp":
                return String.valueOf(pokemon.getStat(Stats.HP));
            case "attack":
                return String.valueOf(pokemon.getStat(Stats.ATTACK));
            case "defence":
                return String.valueOf(pokemon.getStat(Stats.DEFENCE));
            case "sattack":
                return String.valueOf(pokemon.getStat(Stats.SPECIAL_ATTACK));
            case "sdefence":
                return String.valueOf(pokemon.getStat(Stats.SPECIAL_DEFENCE));
            case "speed":
                return String.valueOf(pokemon.getStat(Stats.SPEED));
            case "total":
                return String.valueOf(pokemon.getStat(Stats.HP) + pokemon.getStat(Stats.ATTACK) + pokemon.getStat(Stats.DEFENCE) + pokemon.getStat(Stats.SPECIAL_ATTACK) + pokemon.getStat(Stats.SPECIAL_DEFENCE) + pokemon.getStat(Stats.SPEED));
            case "nhp":
                return String.valueOf(pokemon.getHp());
            case "nattack":
                return String.valueOf(pokemon.getAttack());
            case "ndefence":
                return String.valueOf(pokemon.getDefence());
            case "nsattack":
                return String.valueOf(pokemon.getSpecialAttack());
            case "nsdefence":
                return String.valueOf(pokemon.getSpecialDefence());
            case "nspeed":
                return String.valueOf(pokemon.getSpeed());
            case "ntotal":
                return String.valueOf(pokemon.getHp() + pokemon.getAttack() + pokemon.getDefence() + pokemon.getSpecialAttack() + pokemon.getSpecialDefence() + pokemon.getSpeed());
            case "friendship":
                return String.valueOf(pokemon.getFriendship());
            case "helditem":
                return BaseApi.getBukkitItemStack(pokemon.heldItem()).getType().toString();
            default:
                return "null";
        }
    }


    private String getPlayerPokemonRust(PlayerPartyStore playerPartyStorage, String params) {
        if (params.startsWith("haspokemon")) {
            String[] split = params.split("_");
            Pokemon pokemon = PokemonProperties.Companion.parse(split[1], " ", "=").create();
            int slot = 999;
            for (int i = 0; i < playerPartyStorage.size(); i++) {
                if (playerPartyStorage.get(i) != null && playerPartyStorage.get(i).getSpecies().equals(pokemon.getSpecies())) {
                    slot = i;
                    break;
                }
            }
            return slot == 999 ? "false" : String.valueOf(slot);
        }
        if (params.startsWith("original_haspokemon")) {
            String[] split = params.split("_");
            Pokemon pokemon = PokemonProperties.Companion.parse(split[1], " ", "=").create();
            int slot = 999;
            for (int i = 0; i < playerPartyStorage.size(); i++) {
                if (playerPartyStorage.get(i) != null && playerPartyStorage.get(i).getSpecies().equals(pokemon.getSpecies()) && playerPartyStorage.get(i).getOwnerUUID().equals(playerPartyStorage.getPlayerUUID())) {
                    slot = i;
                    break;
                }
            }
            return slot == 999 ? "false" : String.valueOf(slot);
        }
        if (params.startsWith("name")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getSpecies().getName();
        }
        if (params.startsWith("localname")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getDisplayName().getString();
        }
        if (params.startsWith("nickname")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getNickname() == null ? playerPartyStorage.get(slot).getDisplayName().getString() : playerPartyStorage.get(slot).getNickname().getString();
        }
        if (params.startsWith("form")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getForm().getName();
        }
        if (params.startsWith("original")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getOwnerPlayer().getName().getString();
        }
        if (params.startsWith("egggroup")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            Pokemon pokemon = playerPartyStorage.get(slot);
            if (pokemon == null) return "null";
            StringBuilder sb = new StringBuilder();
            for (EggGroup element : pokemon.getForm().getEggGroups()) {
                sb.append(element.name()).append(" ");
            }
            return sb.toString();
        }
        if (params.startsWith("isegg")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return "暂不支持";
        }
        if (params.startsWith("eggcycles")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return "暂不支持";
        }
        if (params.startsWith("shiny")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).getShiny());
        }
        if (params.startsWith("pokeball")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getCaughtBall().getName().toString();
        }
        if (params.startsWith("cantrade")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).hasLabels("untradeable"));
        }
        if (params.startsWith("canbreed")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).hasLabels("unbreedable"));
        }
        if (params.startsWith("customtexture")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return "暂不支持";
        }
        if (params.startsWith("pokedex")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).getSpecies().getNationalPokedexNumber());
        }
        if (params.startsWith("islegendary")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf((playerPartyStorage.get(slot).isLegendary() || playerPartyStorage.get(slot).isMythical()) ? "true" : "false");
        }
        if (params.startsWith("isultrabeast")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).isUltraBeast());
        }
        if (params.startsWith("move")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            int index = (Integer.parseInt(split[2]) - 1);
            if (playerPartyStorage.get(slot) == null) return "null";
            Move attack = playerPartyStorage.get(slot).getMoveSet().get(index);
            return attack == null ? "null" : attack.getDisplayName().getString();
        }
        if (params.startsWith("partysize")) {
            return String.valueOf(playerPartyStorage.size());
        }
        if (params.startsWith("partyegg")) {
            return "暂不支持";
        }
        return "null";
    }

    private String getPartyRust(String params) {
        return switch (params) {
            case "wins", "caught", "seen", "totalevolved", "totalhatched", "totalbred", "currentkills", "totalkills",
                 "totalbattle", "losses", "totalexp", "currentexp", "pokemoney", "percent_format", "percent" ->
                    "暂不支持";
            case "size" -> String.valueOf(PokemonSpecies.INSTANCE.count());
            default -> "null";
        };
    }

}