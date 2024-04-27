package com.github.yyeerai.hybridserverapi.v1_12_2.api.pokemon;

import com.github.yyeerai.hybridserverapi.v1_12_2.Main;
import com.github.yyeerai.hybridserverapi.v1_12_2.api.BaseApi;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.pokedex.Pokedex;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
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
    %hsa_percent% 	图鉴收集完成度
    %hsa_percent_format% 	图鉴收集完成度格式化
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
    %hsa_info_[slot]_[信息名称]%	指定队伍位置的宝可梦的指定信息

    信息列表:
    name: 宝可梦名称
   level,type,growth,nature, mintnature, ability,gender(等级,类型,体型,性格, 薄荷性格,特性,性别)
   evhp,evattack,evdefence,evsattack,evsdefence,evspeed,evtotal,(努力值)
   ivhp,ivattack,ivdefence, ivsattack,ivsdefence,ivspeed,ivtotal,(个体值)
   hthp,htattack,htdefence, htsattack,htsdefence,htspeed,httotal,(王冠个体值)
   hp,attack,defence,sattack,sdefence, speed,total(种族值)
  hp,nattack,ndefence,nsattack,nsdefence,nspeed,ntotal(当前属性值)
  friendship(亲密度),helditem(携带道具),palette(精灵皮肤)

    */

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {

        if (player == null) return "null";

        if (params.startsWith("wins") || params.startsWith("losses") || params.startsWith("totalbattle") || params.startsWith("totalexp") || params.startsWith("totalkills") || params.startsWith("currentexp") || params.startsWith("currentkills") || params.startsWith("totalbred") || params.startsWith("totalhatched") || params.startsWith("totalevolved") || params.startsWith("seen") || params.startsWith("caught") || params.startsWith("size") || params.startsWith("percent") || params.startsWith("percent_format") || params.startsWith("pokemoney")) {
            return getPartyRust(main.getPokemonApi().getPlayerPartyStorage(player), params);
        }
        if (params.startsWith("haspokemon") || params.startsWith("original_haspokemon") || params.startsWith("name") || params.startsWith("localname") || params.startsWith("nickname") || params.startsWith("form") || params.startsWith("original") || params.startsWith("egggroup") || params.startsWith("isegg") || params.startsWith("eggcycles") || params.startsWith("shiny") || params.startsWith("pokeball") || params.startsWith("cantrade") || params.startsWith("canbreed") || params.startsWith("customtexture") || params.startsWith("pokedex") || params.startsWith("move") || params.startsWith("islegendary") || params.startsWith("isultrabeast") || params.startsWith("partysize") || params.startsWith("partyegg") || params.startsWith("pcsize") || params.startsWith("pcegg")) {
            return getPlayerPokemonRust(main.getPokemonApi().getPlayerPartyStorage(player), params);
        }
        if (params.startsWith("info")) {
            return getPokemonInfoRust(main.getPokemonApi().getPlayerPartyStorage(player), params);
        }
        return "null";
    }

    private String getPokemonInfoRust(PlayerPartyStorage playerPartyStorage, String params) {
        String[] split = params.split("_");
        int slot = (Integer.parseInt(split[1]) - 1);
        if (slot < 0 || slot > 6) return "null";
        Pokemon pokemon = playerPartyStorage.get(slot);
        if (pokemon == null) return "null";
        switch (split[2]) {
            case "name":
                return pokemon.getSpecies().getPokemonName();
            case "level":
                return String.valueOf(pokemon.getLevel());
            case "type":
                return (pokemon.getSpecies().getBaseStats().getType1() == null ? "" : pokemon.getSpecies().getBaseStats().getType1().getLocalizedName()) + " " + (pokemon.getSpecies().getBaseStats().getType2() == null ? "" : pokemon.getSpecies().getBaseStats().getType2().getLocalizedName());
            case "form":
                return String.valueOf(pokemon.getForm());
            case "shiny":
                return String.valueOf(pokemon.isShiny());
            case "growth":
                return pokemon.getGrowth().getLocalizedName();
            case "nature":
                return pokemon.getNature().getLocalizedName();
            case "mintnature":
                return pokemon.getMintNature() == null ? "" : pokemon.getMintNature().getLocalizedName();
            case "ability":
                return pokemon.getAbility().getLocalizedName();
            case "gender":
                return pokemon.getGender().getLocalizedName();
            case "evhp":
                return String.valueOf(pokemon.getEVs().getStat(StatsType.HP));
            case "evattack":
                return String.valueOf(pokemon.getEVs().getStat(StatsType.Attack));
            case "evdefence":
                return String.valueOf(pokemon.getEVs().getStat(StatsType.Defence));
            case "evsattack":
                return String.valueOf(pokemon.getEVs().getStat(StatsType.SpecialAttack));
            case "evsdefence":
                return String.valueOf(pokemon.getEVs().getStat(StatsType.SpecialDefence));
            case "evspeed":
                return String.valueOf(pokemon.getEVs().getStat(StatsType.Speed));
            case "evtotal":
                return String.valueOf(pokemon.getEVs().getTotal());
            case "ivhp":
                return String.valueOf(pokemon.getIVs().getStat(StatsType.HP));
            case "ivattack":
                return String.valueOf(pokemon.getIVs().getStat(StatsType.Attack));
            case "ivdefence":
                return String.valueOf(pokemon.getIVs().getStat(StatsType.Defence));
            case "ivsattack":
                return String.valueOf(pokemon.getIVs().getStat(StatsType.SpecialAttack));
            case "ivsdefence":
                return String.valueOf(pokemon.getIVs().getStat(StatsType.SpecialDefence));
            case "ivspeed":
                return String.valueOf(pokemon.getIVs().getStat(StatsType.Speed));
            case "ivtotal":
                return String.valueOf(pokemon.getIVs().getTotal());
            case "hthp":
                return String.valueOf((pokemon.getIVs().isHyperTrained(StatsType.HP) ? 31 : 0));
            case "htattack":
                return String.valueOf((pokemon.getIVs().isHyperTrained(StatsType.Attack) ? 31 : 0));
            case "htdefence":
                return String.valueOf((pokemon.getIVs().isHyperTrained(StatsType.Defence) ? 31 : 0));
            case "htsattack":
                return String.valueOf((pokemon.getIVs().isHyperTrained(StatsType.SpecialAttack) ? 31 : 0));
            case "htsdefence":
                return String.valueOf((pokemon.getIVs().isHyperTrained(StatsType.SpecialDefence) ? 31 : 0));
            case "htspeed":
                return String.valueOf((pokemon.getIVs().isHyperTrained(StatsType.Speed) ? 31 : 0));
            case "hp":
                return String.valueOf(pokemon.getBaseStats().getStat(StatsType.HP));
            case "attack":
                return String.valueOf(pokemon.getBaseStats().getStat(StatsType.Attack));
            case "defence":
                return String.valueOf(pokemon.getBaseStats().getStat(StatsType.Defence));
            case "sattack":
                return String.valueOf(pokemon.getBaseStats().getStat(StatsType.SpecialAttack));
            case "sdefence":
                return String.valueOf(pokemon.getBaseStats().getStat(StatsType.SpecialDefence));
            case "speed":
                return String.valueOf(pokemon.getBaseStats().getStat(StatsType.Speed));
            case "total":
                return String.valueOf(pokemon.getBaseStats().getStat(StatsType.HP) + pokemon.getBaseStats().getStat(StatsType.Attack) + pokemon.getBaseStats().getStat(StatsType.Defence) + pokemon.getBaseStats().getStat(StatsType.SpecialAttack) + pokemon.getBaseStats().getStat(StatsType.SpecialDefence) + pokemon.getBaseStats().getStat(StatsType.Speed));
            case "nhp":
                return String.valueOf(pokemon.getStat(StatsType.HP));
            case "nattack":
                return String.valueOf(pokemon.getStat(StatsType.Attack));
            case "ndefence":
                return String.valueOf(pokemon.getStat(StatsType.Defence));
            case "nsattack":
                return String.valueOf(pokemon.getStat(StatsType.SpecialAttack));
            case "nsdefence":
                return String.valueOf(pokemon.getStat(StatsType.SpecialDefence));
            case "nspeed":
                return String.valueOf(pokemon.getStat(StatsType.Speed));
            case "ntotal":
                return String.valueOf(pokemon.getStat(StatsType.HP) + pokemon.getStat(StatsType.Attack) + pokemon.getStat(StatsType.Defence) + pokemon.getStat(StatsType.SpecialAttack) + pokemon.getStat(StatsType.SpecialDefence) + pokemon.getStat(StatsType.Speed));
            case "friendship":
                return String.valueOf(pokemon.getFriendship());
            case "helditem":
                return BaseApi.getBukkitItemStack(pokemon.getHeldItem()).getType().toString();
            case "palette":
                return String.valueOf(pokemon.getCustomTexture());
            default:
                return "null";
        }
    }

    @SuppressWarnings("all")
    private String getPlayerPokemonRust(PlayerPartyStorage playerPartyStorage, String params) {
        if (params.startsWith("haspokemon")) {
            String[] split = params.split("_");
            EnumSpecies species = EnumSpecies.getFromNameAnyCase(split[1]);
            if (species == null) return "null";
            int slot = 999;
            for (int i = 0; i < playerPartyStorage.getAll().length; i++) {
                if (playerPartyStorage.get(i) != null && playerPartyStorage.get(i).getSpecies() == species) {
                    slot = i;
                    break;
                }
            }
            return slot == 999 ? "false" : String.valueOf(slot);
        }
        if (params.startsWith("original_haspokemon")) {
            String[] split = params.split("_");
            EnumSpecies species = EnumSpecies.getFromNameAnyCase(split[2]);
            if (species == null) return "null";
            int slot = 999;
            for (int i = 0; i < playerPartyStorage.getAll().length; i++) {
                if (playerPartyStorage.get(i) != null && playerPartyStorage.get(i).getSpecies() == species && playerPartyStorage.get(i).getOriginalTrainerUUID().equals(playerPartyStorage.getPlayerUUID())) {
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
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getSpecies().getPokemonName();
        }
        if (params.startsWith("localname")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
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
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getFormEnum().getLocalizedName();
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
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getSpecies().getBaseStats().getEggGroupsArray().toString();
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
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getCaughtBall().getLocalizedName();
        }
        if (params.startsWith("cantrade")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).hasSpecFlag("untradeable"));
        }
        if (params.startsWith("canbreed")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : String.valueOf(playerPartyStorage.get(slot).hasSpecFlag("unbreedable"));
        }
        if (params.startsWith("customtexture")) {
            String[] split = params.split("_");
            int slot = (Integer.parseInt(split[1]) - 1);
            if (slot < 0 || slot > 6) return "null";
            return playerPartyStorage.get(slot) == null ? "null" : playerPartyStorage.get(slot).getCustomTexture();
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
        switch (params) {
            case "wins":
                return String.valueOf(playerPartyStorage.stats.getWins());
            case "losses":
                return String.valueOf(playerPartyStorage.stats.getLosses());
            case "totalbattle":
                return String.valueOf(playerPartyStorage.stats.getWins() + playerPartyStorage.stats.getLosses());
            case "totalexp":
                return String.valueOf(playerPartyStorage.stats.getTotalExp());
            case "totalkills":
                return String.valueOf(playerPartyStorage.stats.getTotalKills());
            case "currentexp":
                return String.valueOf(playerPartyStorage.stats.getCurrentExp());
            case "currentkills":
                return String.valueOf(playerPartyStorage.stats.getCurrentKills());
            case "totalbred":
                return String.valueOf(playerPartyStorage.stats.getTotalBred());
            case "totalhatched":
                return String.valueOf(playerPartyStorage.stats.getTotalHatched());
            case "totalevolved":
                return String.valueOf(playerPartyStorage.stats.getTotalEvolved());
            case "seen":
                return String.valueOf(playerPartyStorage.pokedex.countSeen());
            case "caught":
                return String.valueOf(playerPartyStorage.pokedex.countCaught());
            case "size":
                return String.valueOf(Pokedex.size());
            case "percent":
                return String.format("%.4f", playerPartyStorage.pokedex.countCaught() / (double) Pokedex.size());
            case "percent_format":
                return (Math.round(playerPartyStorage.pokedex.countCaught() / (double) Pokedex.size() * 100)) + "%";
            case "pokemoney":
                return String.valueOf(playerPartyStorage.getMoney());
            default:
                return "null";
        }
    }

}