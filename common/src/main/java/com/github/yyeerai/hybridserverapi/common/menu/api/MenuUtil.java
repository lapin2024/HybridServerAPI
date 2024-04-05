package com.github.yyeerai.hybridserverapi.common.menu.api;

import com.github.yyeerai.hybridserverapi.common.menu.sound.MenuSound;
import com.github.yyeerai.hybridserverapi.common.serializeitem.ItemUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MenuUtil {

    public final JavaPlugin plugin;
    public final String content;
    public String argument;

    public MenuUtil(JavaPlugin plugin, String content) {
        this.plugin = plugin;
        this.content = content;
    }

    /**
     * 从内容字符串中移除尖括号及其中的内容
     *
     * @param str 原始字符串
     * @return 返回移除尖括号中的内容后的字符串
     */
    public static String removeAngleBracketsContent(String str) {
        return str.replaceAll("<(chance|delay)=(\\d+(\\.\\d+)?)>", "");
    }

    // public abstract void initialization();

    /**
     * 从字符串中提取尖括号内的内容
     *
     * @param str 字符串
     * @return 尖括号内的内容
     */
    public List<String> getAngleBracketsContent(String str) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("<(chance|delay)=(\\d+(\\.\\d+)?)>");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            result.add(matcher.group(0));
        }
        return result;
    }

    /**
     * 获取延迟执行的tick
     *
     * @param strings 可能包含延迟的字符串列表
     * @return 返回延迟执行的tick
     */
    public int getDelay(List<String> strings) {
        int delay = 0;
        for (String s : strings) {
            s = s.replace("<", "").replace(">", "").trim();
            String[] split = s.split("=");
            if (split[0].equals("delay") && split.length == 2) {
                delay = Integer.parseInt(split[1]);
                break;
            }
        }
        return delay;
    }

    /**
     * 获取概率执行的概率
     *
     * @param strings 可能包含延迟的字符串列表
     * @return 返回概率
     */
    public double getChance(List<String> strings) {
        double chance = 0.0;
        for (String s : strings) {
            s = s.replace("<", "").replace(">", "").trim();
            String[] split = s.split("=");
            if (split[0].equals("chance") && split.length == 2) {
                chance = Double.parseDouble(split[1]);
                break;
            }
        }
        return chance;
    }

    /**
     * 从字符串中提取数组
     *
     * @param str 字符串
     * @return 返回数组
     */
    public List<String> extractArray(String str) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String[] contents = matcher.group(1).split("\\|");
            for (String content : contents) {
                result.add(content.trim());
            }
        }
        return result;
    }


    /**
     * 从字符串中提取物品
     *
     * @param str 字符串
     * @return 返回物品
     */
    public ItemStack extractItems(String str) {
        Map<String, Object> map = new HashMap<>();
        String typePattern = "type:([^,]*)";
        String damagePattern = "damage:([^,]*)";
        String amountPattern = "amount:([^,]*)";
        String namePattern = "name:\"([^\"]*)\"";
        String lorePattern = "lore:(\\[[^]]*])";
        String nbtPattern = "nbt:(\\{.*})";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(typePattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            map.put("type", matcher.group(1).trim());
        }
        pattern = Pattern.compile(damagePattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            map.put("damage", Integer.parseInt(matcher.group(1).trim()));
        }
        pattern = Pattern.compile(amountPattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            map.put("amount", Integer.parseInt(matcher.group(1).trim()));
        }
        pattern = Pattern.compile(namePattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            map.put("name", matcher.group(1));
        }
        pattern = Pattern.compile(lorePattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            map.put("lore", extractArray(matcher.group(1)));
        }
        pattern = Pattern.compile(nbtPattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            map.put("nbt", matcher.group(1));
        }
        return ItemUtil.deserializeItemStack(map);
    }

    /**
     * 从字符串中提取声音
     *
     * @param str 字符串
     * @return 返回声音 MenuSound
     */
    public MenuSound extractSound(String str) {
        String typePattern = "type:([^,]*)";
        String volumePattern = "volume:([^,]*)";
        String pitchPattern = "pitch:(.*)";

        Pattern pattern;
        Matcher matcher;

        Map<String, String> map = new HashMap<>();

        pattern = Pattern.compile(typePattern);
        matcher = pattern.matcher(str);
        map.put("type", matcher.find() ? matcher.group(1).trim() : "");

        pattern = Pattern.compile(volumePattern);
        matcher = pattern.matcher(str);
        map.put("volume", matcher.find() ? matcher.group(1).trim() : "");

        pattern = Pattern.compile(pitchPattern);
        matcher = pattern.matcher(str);
        map.put("pitch", matcher.find() ? matcher.group(1).trim() : "");

        String type = map.get("type");
        float volume = Float.parseFloat(map.get("volume"));
        float pitch = Float.parseFloat(map.get("pitch"));
        return new MenuSound(type, volume, pitch);
    }

}