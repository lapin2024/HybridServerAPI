package com.github.yyeerai.sponge.hybridserverapi;

import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public class PokemonAPI {

    private static PokemonAPI INSTANCE;

    private PokemonAPI() {
    }

    public static PokemonAPI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PokemonAPI();
        }
        return INSTANCE;
    }

    /**
     * 获得玩家宝可梦队伍
     *
     * @param player 玩家
     * @return 宝可梦队伍
     */
    public PlayerPartyStorage getPartyStorage(ServerPlayer player) {
        return StorageProxy.getParty(player.uniqueId());
    }

    /**
     * 获得玩家宝可梦仓库
     *
     * @param player 玩家
     * @return 宝可梦仓库~
     */
    public PCStorage getPCStorage(ServerPlayer player) {
        return StorageProxy.getPCForPlayer(player.uniqueId());
    }

    public static void registerPokemonPlaceHolder() {
        Expansion.Builder builder = Expansion.builder("lps");
        builder.filter(ServerPlayer.class)
                .audiencePlaceholder("partySize",(audience, queue, ctx) -> {
                    ServerPlayer player = (ServerPlayer) audience;
                    return Tag.inserting(Component.text(getInstance().getPartyStorage(player).uuid.toString()));
                });
    }


}