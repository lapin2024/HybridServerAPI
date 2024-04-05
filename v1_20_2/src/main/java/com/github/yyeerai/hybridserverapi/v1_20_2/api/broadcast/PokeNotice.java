package com.github.yyeerai.hybridserverapi.v1_20_2.api.broadcast;

import com.github.yyeerai.hybridserverapi.common.broadcast.AbstractBroadcast;
import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.v1_20_2.api.BaseApi;
import com.pixelmonmod.api.parsing.ParseAttempt;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.api.overlay.notice.EnumOverlayLayout;
import com.pixelmonmod.pixelmon.api.overlay.notice.NoticeOverlay;
import com.pixelmonmod.pixelmon.api.util.helpers.NetworkHelper;
import com.pixelmonmod.pixelmon.comm.packetHandlers.custom.overlays.CustomNoticePacketPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PokeNotice extends AbstractBroadcast {
    private static final String regex = "(?:pokemon:([^,]+),\\s*)?(?:message:\\[([^]]*)],\\s*)?(?:time:(\\d+))?";
    /**
     * 构造一个新的 AbstractBroadcast 实例。
     *
     * @param message 广播的消息内容
     */
    public PokeNotice(String message) {
        super(message);
    }

    @Override
    public void broadcast() {
        List<Component> lines = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String pokemon = matcher.group(1) == null ? "mew" : matcher.group(1).trim();
            ParseAttempt<PokemonSpecification> pokemonSpecificationParseAttempt = PokemonSpecificationProxy.create(pokemon.trim());
            String[] split = matcher.group(2) == null ? new String[]{"&a&l你抓到了一个神奇的皮卡丘, &6&l快去抓住它吧！"} : matcher.group(2).split(",");
            for (String s : split) {
                lines.add(MutableComponent.create(new LiteralContents(HexUtils.colorify(s.trim()))));
            }
            int time = Integer.parseInt(matcher.group(3) == null ? "120" : matcher.group(3).trim());
            if (pokemonSpecificationParseAttempt.wasSuccess()) {
                @NotNull PokemonSpecification pokemonSpecification = pokemonSpecificationParseAttempt.get();
                CustomNoticePacketPacket noticePacket = NoticeOverlay.builder()
                        .setLines(lines)
                        .setPokemonSprite(pokemonSpecification)
                        .setLayout(EnumOverlayLayout.LEFT_AND_RIGHT)
                        .build();
                Bukkit.getOnlinePlayers().forEach(player -> NetworkHelper.sendPacket(noticePacket, BaseApi.getMinecraftPlayer(player)));
                Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HybridServerAPI")), () -> Bukkit.getOnlinePlayers().forEach(player -> NoticeOverlay.hide(BaseApi.getMinecraftPlayer(player))), time);
            }
        }

    }
}