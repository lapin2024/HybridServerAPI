package com.github.yyeerai.hybridserverapi.v1_16_5.api.broadcast;

import com.github.yyeerai.hybridserverapi.common.broadcast.AbstractBroadcast;
import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.v1_16_5.api.BaseApi;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.api.overlay.notice.EnumOverlayLayout;
import com.pixelmonmod.pixelmon.api.overlay.notice.NoticeOverlay;
import com.pixelmonmod.pixelmon.api.util.helpers.NetworkHelper;
import com.pixelmonmod.pixelmon.comm.packetHandlers.custom.overlays.CustomNoticePacketPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PokeNotice extends AbstractBroadcast {

    private final PokeNoticeManger pokeNoticeManger = new PokeNoticeManger();
    private final List<Player> players = new ArrayList<>();
    private CustomNoticePacketPacket packet;

    private static final String pokemonRegex = "pokemon:([^,]*)";
    private static final String messageRegex = "message:(\\[.*?])";
    private static final String timeRegex = "time:(\\d*)";

    private int time = 120;

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
        players.clear();
        players.addAll(Bukkit.getOnlinePlayers());
        packet = createNoticePacket();
        pokeNoticeManger.addPokeNotice(this, time);
    }

    @Override
    public void sendMessage(Player player) {
        players.clear();
        players.add(player);
        packet = createNoticePacket();
        pokeNoticeManger.addPokeNotice(this, time);
    }

    @Override
    public void broadcast(Player player) {
        broadcast();
    }

    private CustomNoticePacketPacket createNoticePacket() {
        List<ITextComponent> lines = new ArrayList<>();

        Pattern pokemonPattern = Pattern.compile(pokemonRegex);
        Matcher pokemonMatcher = pokemonPattern.matcher(message);
        String pokemon = pokemonMatcher.find() ? pokemonMatcher.group(1).trim() : "mew";

        Pattern messagePattern = Pattern.compile(messageRegex);
        Matcher messageMatcher = messagePattern.matcher(message);
        String[] split = messageMatcher.find() ? messageMatcher.group(1).replace("[", "").replace("]", "").split(",") : new String[]{"&a&l你抓到了一个神奇的皮卡丘", " &6&l快去抓住它吧！"};
        for (String s : split) {
            lines.add(new StringTextComponent(HexUtils.colorify(s)));
        }

        Pattern timePattern = Pattern.compile(timeRegex);
        Matcher timeMatcher = timePattern.matcher(message);
        time = timeMatcher.find() ? Integer.parseInt(timeMatcher.group(1).trim()) : 120;

        return NoticeOverlay.builder()
                .setLines(lines)
                .setPokemonSprite(PokemonSpecificationProxy.create(pokemon))
                .setLayout(EnumOverlayLayout.LEFT_AND_RIGHT)
                .build();
    }

    public void playPokemonNotice() {
        if (players.isEmpty()) {
            return;
        }
        players.forEach(p -> NetworkHelper.sendPacket(packet, BaseApi.getMinecraftPlayer(p)));
    }

    public void hidePokemonNotice() {
        if (players.isEmpty()) {
            return;
        }
        players.forEach(p -> NoticeOverlay.hide(BaseApi.getMinecraftPlayer(p)));
    }
}