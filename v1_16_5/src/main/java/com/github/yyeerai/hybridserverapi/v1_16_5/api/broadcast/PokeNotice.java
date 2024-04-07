package com.github.yyeerai.hybridserverapi.v1_16_5.api.broadcast;

import com.github.yyeerai.hybridserverapi.common.broadcast.AbstractBroadcast;
import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.v1_16_5.api.BaseApi;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.api.overlay.notice.EnumOverlayLayout;
import com.pixelmonmod.pixelmon.api.overlay.notice.NoticeOverlay;
import com.pixelmonmod.pixelmon.api.util.helpers.NetworkHelper;
import com.pixelmonmod.pixelmon.comm.packetHandlers.custom.overlays.CustomNoticePacketPacket;
import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
    public void broadcast(Player player) {
        List<ITextComponent> lines = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String pokemon = matcher.group(1) == null ? "mew" : matcher.group(1).trim();
            String[] split = matcher.group(2) == null ? new String[]{"&a&l你抓到了一个神奇的皮卡丘, &6&l快去抓住它吧！"} : matcher.group(2).split(",");
            for (String s : split) {
                String finalS = player != null ? PlaceholderAPI.setPlaceholders(player, s.trim()) : s.trim();
                lines.add(new StringTextComponent(HexUtils.colorify(finalS)));
            }
            int time = Integer.parseInt(matcher.group(3) == null ? "120" : matcher.group(3).trim());
            CustomNoticePacketPacket noticePacket = NoticeOverlay.builder()
                    .setLines(lines)
                    .setPokemonSprite(PokemonSpecificationProxy.create(pokemon.trim()))
                    .setLayout(EnumOverlayLayout.LEFT_AND_RIGHT)
                    .build();
            Bukkit.getOnlinePlayers().forEach(p -> NetworkHelper.sendPacket(noticePacket, BaseApi.getMinecraftPlayer(p)));
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HybridServerAPI")), () -> Bukkit.getOnlinePlayers().forEach(p -> NoticeOverlay.hide(BaseApi.getMinecraftPlayer(p))), time);
        }
    }
}