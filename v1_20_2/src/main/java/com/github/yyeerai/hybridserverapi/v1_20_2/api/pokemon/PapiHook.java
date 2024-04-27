package com.github.yyeerai.hybridserverapi.v1_20_2.api.pokemon;

import com.github.yyeerai.hybridserverapi.v1_20_2.Main;
import com.github.yyeerai.hybridserverapi.v1_20_2.api.BaseApi;
import com.pixelmonmod.api.parsing.ParseAttempt;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.egg.EggGroup;
import com.pixelmonmod.pixelmon.api.pokemon.species.Pokedex;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            return getPartyRust(main.getPokemonApi().getPartyStorage(player), params);
        }
        if (params.startsWith("haspokemon") || params.startsWith("original_haspokemon") || params.startsWith("name") || params.startsWith("localname") || params.startsWith("nickname") || params.startsWith("form") || params.startsWith("original") || params.startsWith("egggroup") || params.startsWith("isegg") || params.startsWith("eggcycles") || params.startsWith("shiny") || params.startsWith("pokeball") || params.startsWith("cantrade") || params.startsWith("canbreed") || params.startsWith("customtexture") || params.startsWith("pokedex") || params.startsWith("move") || params.startsWith("islegendary") || params.startsWith("isultrabeast") || params.startsWith("partysize") || params.startsWith("partyegg") || params.startsWith("pcsize") || params.startsWith("pcegg")) {
            return getPlayerPokemonRust(main.getPokemonApi().getPartyStorage(player), params);
        }
        if (params.startsWith("info")) {
            return getPokemonInfoRust(main.getPokemonApi().getPartyStorage(player), params);
        }
        return "null";
    }

    @SuppressWarnings("all")
    private String getPokemonInfoRust(PlayerPartyStorage playerPartyStorage, String params) {
        String[] split = params.split("_");
        int slot = (Integer.parseInt(split[1]) - 1);
        if (slot < 0 || slot > 6) return "null";
        Pokemon pokemon = playerPartyStorage.get(slot);
        if (pokemon == null) return "null";
        switch (split[2]) {
            case "name":
                return pokemon.getSpecies().getName();
            case "level":
                return String.valueOf(pokemon.getPokemonLevel());
            case "type":
                List<Element> types = pokemon.getSpecies().getFirstForm().getTypes();
                return types.get(0).getLocalizedName() + " " + (types.size() == 1 ? "" : types.get(1).getLocalizedName());
            case "form":
                return pokemon.getForm().getName();
            case "shiny":
                return String.valueOf(pokemon.isShiny());
            case "growth":
                return pokemon.getGrowth().getLocalizedName();
            case "nature":
                return pokemon.getNature().getLocalizedName();
            case "mintnature":
                return pokemon.getMintNature() == null ? "null" : pokemon.getMintNature().getLocalizedName();
            case "ability":
                return pokemon.getAbility().getLocalizedName();
            case "gender":
                return pokemon.getGender().getLocalizedName();
            case "evhp":
                return String.valueOf(pokemon.getEVs().getStat(BattleStatsType.HP));
            case "evattack":
                return String.valueOf(pokemon.getEVs().getStat(BattleStatsType.ATTACK));
            case "evdefence":
                return String.valueOf(pokemon.getEVs().getStat(BattleStatsType.DEFENSE));
            case "evsattack":
                return String.valueOf(pokemon.getEVs().getStat(BattleStatsType.SPECIAL_ATTACK));
            case "evsdefence":
                return String.valueOf(pokemon.getEVs().getStat(BattleStatsType.SPECIAL_DEFENSE));
            case "evspeed":
                return String.valueOf(pokemon.getEVs().getStat(BattleStatsType.SPEED));
            case "evtotal":
                return String.valueOf(pokemon.getEVs().getTotal());
            case "ivhp":
                return String.valueOf(pokemon.getIVs().getStat(BattleStatsType.HP));
            case "ivattack":
                return String.valueOf(pokemon.getIVs().getStat(BattleStatsType.ATTACK));
            case "ivdefence":
                return String.valueOf(pokemon.getIVs().getStat(BattleStatsType.DEFENSE));
            case "ivsattack":
                return String.valueOf(pokemon.getIVs().getStat(BattleStatsType.SPECIAL_ATTACK));
            case "ivsdefence":
                return String.valueOf(pokemon.getIVs().getStat(BattleStatsType.SPECIAL_DEFENSE));
            case "ivspeed":
                return String.valueOf(pokemon.getIVs().getStat(BattleStatsType.SPEED));
            case "ivtotal":
                return String.valueOf(pokemon.getIVs().getTotal());
            case "hthp":
                return String.valueOf((pokemon.getIVs().isHyperTrained(BattleStatsType.HP) ? 31 : 0));
            case "htattack":
                return String.valueOf((pokemon.getIVs().isHyperTrained(BattleStatsType.ATTACK) ? 31 : 0));
            case "htdefence":
                return String.valueOf((pokemon.getIVs().isHyperTrained(BattleStatsType.DEFENSE) ? 31 : 0));
            case "htsattack":
                return String.valueOf((pokemon.getIVs().isHyperTrained(BattleStatsType.SPECIAL_ATTACK) ? 31 : 0));
            case "htsdefence":
                return String.valueOf((pokemon.getIVs().isHyperTrained(BattleStatsType.SPECIAL_DEFENSE) ? 31 : 0));
            case "htspeed":
                return String.valueOf((pokemon.getIVs().isHyperTrained(BattleStatsType.SPEED) ? 31 : 0));
            case "hp":
                return String.valueOf(pokemon.getForm().getBattleStats().getStat(BattleStatsType.HP));
            case "attack":
                return String.valueOf(pokemon.getForm().getBattleStats().getStat(BattleStatsType.ATTACK));
            case "defence":
                return String.valueOf(pokemon.getForm().getBattleStats().getStat(BattleStatsType.DEFENSE));
            case "sattack":
                return String.valueOf(pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPECIAL_ATTACK));
            case "sdefence":
                return String.valueOf(pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPECIAL_DEFENSE));
            case "speed":
                return String.valueOf(pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPEED));
            case "total":
                return String.valueOf(pokemon.getForm().getBattleStats().getStat(BattleStatsType.HP) + pokemon.getForm().getBattleStats().getStat(BattleStatsType.ATTACK) + pokemon.getForm().getBattleStats().getStat(BattleStatsType.DEFENSE) + pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPECIAL_ATTACK) + pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPECIAL_DEFENSE) + pokemon.getForm().getBattleStats().getStat(BattleStatsType.SPEED));
            case "nhp":
                return String.valueOf(pokemon.getStat(BattleStatsType.HP));
            case "nattack":
                return String.valueOf(pokemon.getStat(BattleStatsType.ATTACK));
            case "ndefence":
                return String.valueOf(pokemon.getStat(BattleStatsType.DEFENSE));
            case "nsattack":
                return String.valueOf(pokemon.getStat(BattleStatsType.SPECIAL_ATTACK));
            case "nsdefence":
                return String.valueOf(pokemon.getStat(BattleStatsType.SPECIAL_DEFENSE));
            case "nspeed":
                return String.valueOf(pokemon.getStat(BattleStatsType.SPEED));
            case "ntotal":
                return String.valueOf(pokemon.getStat(BattleStatsType.HP) + pokemon.getStat(BattleStatsType.ATTACK) + pokemon.getStat(BattleStatsType.DEFENSE) + pokemon.getStat(BattleStatsType.SPECIAL_ATTACK) + pokemon.getStat(BattleStatsType.SPECIAL_DEFENSE) + pokemon.getStat(BattleStatsType.SPEED));
            case "friendship":
                return String.valueOf(pokemon.getFriendship());
            case "helditem":
                return BaseApi.getBukkitItemStack(pokemon.getHeldItem()).getType().toString();
            case "palette":
                return String.valueOf(pokemon.getPalette().getName());
            default:
                return "null";
        }
    }


    @SuppressWarnings("all")
    private String getPlayerPokemonRust(PlayerPartyStorage playerPartyStorage, String params) {
        if (params.startsWith("haspokemon")) {
            String[] split = params.split("_");
            ParseAttempt<PokemonSpecification> pokemonSpecificationParseAttempt = PokemonSpecificationProxy.create(split[1]);
            Pokemon pokemon = null;
            if (pokemonSpecificationParseAttempt.wasSuccess()) {
                pokemon = pokemonSpecificationParseAttempt.get().create();
            }
            if (pokemon == null) return "null";
            int slot = 999;
            for (int i = 0; i < playerPartyStorage.getAll().length; i++) {
                if (playerPartyStorage.get(i) != null && playerPartyStorage.get(i).getSpecies().is(pokemon.getSpecies())) {
                    slot = i;
                    break;
                }
            }
            return slot == 999 ? "false" : String.valueOf(slot);
        }
        if (params.startsWith("original_haspokemon")) {
            String[] split = params.split("_");
            ParseAttempt<PokemonSpecification> pokemonSpecificationParseAttempt = PokemonSpecificationProxy.create(split[2]);
            Pokemon pokemon = null;
            if (pokemonSpecificationParseAttempt.wasSuccess()) {
                pokemon = pokemonSpecificationParseAttempt.get().create();
            }
            if (pokemon == null) return "null";
            int slot = 999;
            for (int i = 0; i < playerPartyStorage.getAll().length; i++) {
                if (playerPartyStorage.get(i) != null && playerPartyStorage.get(i).getSpecies().is(pokemon.getSpecies()) && playerPartyStorage.get(i).getOriginalTrainerUUID().equals(playerPartyStorage.getPlayerUUID())) {
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
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getLocalizedName();
        }
        if (params.startsWith("nickname")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getNickname().isEmpty() ? playerPartyStorage.get(slot).getLocalizedName() : playerPartyStorage.get(slot).getNickname();
        }
        if (params.startsWith("form")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getForm().getLocalizedName();
        }
        if (params.startsWith("original")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getOriginalTrainer();
        }
        if (params.startsWith("egggroup")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            Pokemon pokemon = playerPartyStorage.get(slot);
            if (pokemon == null) return "null";
            StringBuilder sb = new StringBuilder();
            for (EggGroup element : pokemon.getForm().getEggGroups()) {
                sb.append(element.getLocalizedName()).append(" ");
            }
            return sb.toString();
        }
        if (params.startsWith("isegg")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).isEgg());
        }
        if (params.startsWith("eggcycles")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).getEggCycles());
        }
        if (params.startsWith("shiny")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).isShiny());
        }
        if (params.startsWith("pokeball")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getBall().getLocalizedName();
        }
        if (params.startsWith("cantrade")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).hasFlag("untradeable"));
        }
        if (params.startsWith("canbreed")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).hasFlag("unbreedable"));
        }
        if (params.startsWith("customtexture")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getPalette().getLocalizedName();
        }
        if (params.startsWith("pokedex")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).getSpecies().getDex());
        }
        if (params.startsWith("islegendary")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).getSpecies().isLegendary());
        }
        if (params.startsWith("isultrabeast")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).getSpecies().isUltraBeast());
        }
        if (params.startsWith("move")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            int index = (Integer.parseInt(split[2]) - 1);
            if (playerPartyStorage.get(slot) == null) return "null";
            Attack attack = playerPartyStorage.get(slot).getMoveset().get(index);
            return attack == null ? "null" : attack.getActualMove().getLocalizedName();
        }
        if (params.startsWith("partysize")) {
            return String.valueOf(playerPartyStorage.getTeam().size());
        }
        if (params.startsWith("partyegg")) {
            int count = 0;
            for (int i = 0; i < playerPartyStorage.getTeam().size(); i++) {
                if (playerPartyStorage.get(i).isEgg()) {
                    count++;
                }
            }
            return String.valueOf(count);
        }
        return "null";
    }

    private String getPartyRust(PlayerPartyStorage playerPartyStorage, String params) {
        return switch (params) {
            case "wins" -> String.valueOf(playerPartyStorage.stats.getWins());
            case "losses" -> String.valueOf(playerPartyStorage.stats.getLosses());
            case "totalbattle" ->
                    String.valueOf(playerPartyStorage.stats.getWins() + playerPartyStorage.stats.getLosses());
            case "totalexp" -> String.valueOf(playerPartyStorage.stats.getTotalExp());
            case "totalkills" -> String.valueOf(playerPartyStorage.stats.getTotalKills());
            case "currentexp" -> String.valueOf(playerPartyStorage.stats.getCurrentExp());
            case "currentkills" -> String.valueOf(playerPartyStorage.stats.getCurrentKills());
            case "totalbred" -> String.valueOf(playerPartyStorage.stats.getTotalBred());
            case "totalhatched" -> String.valueOf(playerPartyStorage.stats.getTotalHatched());
            case "totalevolved" -> String.valueOf(playerPartyStorage.stats.getTotalEvolved());
            case "seen" -> String.valueOf(playerPartyStorage.playerPokedex.countSeen());
            case "caught" -> String.valueOf(playerPartyStorage.playerPokedex.countCaught());
            case "size" -> String.valueOf(Pokedex.pokedexSize);
            case "percent" ->
                    String.format("%.2f", playerPartyStorage.playerPokedex.countCaught() / (double) Pokedex.pokedexSize);
            case "percent_format" ->
                    (Math.round(playerPartyStorage.playerPokedex.countCaught() / (double) Pokedex.pokedexSize * 100)) + "%";
            case "pokemoney" -> playerPartyStorage.getBalance().toString();
            default -> "null";
        };
    }

}