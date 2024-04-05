package com.github.yyeerai.hybridserverapi.common.util;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mc9y.nyeconomy.api.NyEconomyAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class PluginMethods {
    /**
     * 获得经济系统
     *
     * @return 经济系统
     */
    @Nullable
    public static Economy getEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();
    }

    /**
     * 获得玩家点数api
     *
     * @return 玩家点数api
     */
    @Nullable
    public static PlayerPointsAPI getPlayerPointsAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            PlayerPoints playerPoints = (PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints");
            if (playerPoints != null) {
                return playerPoints.getAPI();
            }
        }
        return null;
    }


    /**
     * 获得NyEconomyAPI
     *
     * @return NyEconomyAPI
     */
    @Nullable
    public static NyEconomyAPI getNyEconomyAPI() {
        if (Bukkit.getPluginManager().getPlugin("NyEconomy") == null) {
            return null;
        }
        return NyEconomyAPI.getInstance();
    }

    /**
     * 获得聊天系统
     *
     * @return 聊天系统
     */
    @Nullable
    public static Chat getChat() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Chat.class)).getProvider();
    }

    /**
     * 获得权限系统
     *
     * @return 权限系统
     */
    @Nullable
    public static Permission getPermission() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Permission.class)).getProvider();
    }

    /**
     * 获得PlaceholderAPI
     *
     * @return 是否有PlaceholderAPI
     */
    public static boolean getPlaceholderAPI() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    /**
     * 获得服务器所有在线玩家的名字集合
     *
     * @return 玩家名字集合
     */
    public static List<String> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    /**
     * 将字符串列表转换为单个char的字符串列表
     *
     * @param origin 原字符串列表
     * @return 转换后的字符串列表
     */
    public static List<String> getListString(List<String> origin) {
        List<String> list = new ArrayList<>();
        for (String s : origin) {
            for (char c : s.toCharArray()) {
                list.add(String.valueOf(c));
            }
        }
        return list;
    }

    /**
     * 根据输入的字符串列表 list,返回一个按照字符串 str 过滤后的字符串列表
     *
     * @param list 输入的字符串列表
     * @param str  过滤的字符串
     * @return 过滤后的字符串列表
     */
    public static List<String> filter(List<String> list, String str) {
        List<String> newList = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith(str)) {
                newList.add(s);
            }
        }
        return newList;
    }


    /**
     * 发送json消息
     * 消息格式是标准的json格式，例如：
     * {"text":"<r:0.6>json可以五颜六色","clickEvent":{"action":"RUN_COMMAND","value":"/say HybridServerAPI"},"hoverEvent":{"action":"SHOW_TEXT","value":["<r:0.3>你好minecraft","<g:#084CFB:#ADF3FD:#E7185A:#5F85AB>这是指定渐变范围","&6再见!!!"]}}
     *
     * @param player  玩家
     * @param content 原始json消息
     */
    public static void sendJsonMessage(Player player, String content) {
        player.spigot().sendMessage(parseJsonMessage(content));
    }

    /**
     * 广播json消息
     *
     * @param json json消息
     */
    public static void broadcastJsonMessage(String json) {
        Bukkit.spigot().broadcast(parseJsonMessage(json));
    }

    /**
     * 将JSON字符串解析为BaseComponent数组，用于在Minecraft中发送富文本消息。
     *
     * @param json 需要解析的JSON字符串
     * @return 解析后的BaseComponent数组
     */
    public static BaseComponent[] parseJsonMessage(String json) {
        // 创建JsonParser对象
        JsonParser parser = new JsonParser();
        // 将输入的json字符串解析为JsonObject对象
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();

        // 从JsonObject中获取"text"字段的值
        String text = jsonObject.get("text").getAsString();
        String clickEventAction;
        String clickEventValue;
        String hoverEventAction;
        List<String> hoverEventValue;
        // 从JsonObject中获取"clickEvent"对象
        if (!jsonObject.has("clickEvent")) {
            clickEventAction = "";
            clickEventValue = "";
        } else {
            JsonObject clickEventObject = jsonObject.getAsJsonObject("clickEvent");
            // 从clickEventObject中获取"action"和"value"字段的值
            clickEventAction = clickEventObject.get("action").getAsString();
            clickEventValue = clickEventObject.get("value").getAsString();
        }
        // 从JsonObject中获取"hoverEvent"对象
        if (!jsonObject.has("hoverEvent")) {
            hoverEventAction = "";
            hoverEventValue = new ArrayList<>();
        } else {
            JsonObject hoverEventObject = jsonObject.getAsJsonObject("hoverEvent");
            // 从hoverEventObject中获取"action"字段的值
            hoverEventAction = hoverEventObject.get("action").getAsString();
            // 从hoverEventObject中获取"value"字段的值，该字段的值是一个JsonArray，需要遍历获取所有的值
            hoverEventValue = new ArrayList<>();
            for (JsonElement jsonElement : hoverEventObject.get("value").getAsJsonArray()) {
                hoverEventValue.add(jsonElement.getAsString());
            }
        }


        // 创建TextComponent对象，用于在Minecraft中发送富文本消息
        BaseComponent textComponent = new TextComponent();
        List<BaseComponent> extra = new ArrayList<>(Arrays.asList(TextComponent.fromLegacyText(HexUtils.colorify(text))));
        textComponent.setExtra(extra);

        // 如果clickEventAction和clickEventValue不为空，则设置TextComponent的点击事件
        if (!clickEventAction.isEmpty() && !clickEventValue.isEmpty()) {
            textComponent.setClickEvent(new ClickEvent(getClickEventAction(clickEventAction.trim()), HexUtils.colorify(clickEventValue.trim())));
        }
        // 如果hoverEventAction和hoverEventValue不为空，则设置TextComponent的悬停事件
        if (!hoverEventAction.isEmpty() && !hoverEventValue.isEmpty()) {
            TextComponent textComponent1 = new TextComponent();
            for (int i = 0; i < hoverEventValue.size(); i++) {
                Arrays.stream(TextComponent.fromLegacyText(HexUtils.colorify(hoverEventValue.get(i)))).forEach(textComponent1::addExtra);
                if (i != hoverEventValue.size() - 1) {
                    textComponent1.addExtra("\n");
                }
            }
            textComponent.setHoverEvent(new HoverEvent(getHoverEventAction(hoverEventAction.trim()), new BaseComponent[]{textComponent1}));
        }
        return new BaseComponent[]{textComponent};
    }

    /**
     * 将字符串转换为ClickEvent.Action枚举类型。
     *
     * @param action 需要转换的字符串
     * @return 转换后的ClickEvent.Action枚举类型
     */
    private static ClickEvent.Action getClickEventAction(String action) {
        return ClickEvent.Action.valueOf(action);
    }

    /**
     * 将字符串转换为HoverEvent.Action枚举类型。
     *
     * @param action 需要转换的字符串
     * @return 转换后的HoverEvent.Action枚举类型
     */
    private static HoverEvent.Action getHoverEventAction(String action) {
        return HoverEvent.Action.valueOf(action);
    }
}