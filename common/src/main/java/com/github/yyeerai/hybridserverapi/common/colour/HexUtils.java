package com.github.yyeerai.hybridserverapi.common.colour;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.yyeerai.hybridserverapi.common.util.NMSUtil;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * HexUtils 是一个工具类，提供处理和操作颜色代码的方法。
 * 它包括解析和生成渐变色、彩虹色和十六进制颜色代码的方法。
 * 它还提供了向 CommandSender 对象发送彩色消息的方法。
 * 这个类是 final 类，不能被继承。
 * <p>
 * 颜色代码示例：
 * <rainbow|r>：这是标记，表示要生成彩虹色。可以使用<rainbow>或者<r>。
 * (#(\\d+))?：这是可选的速度参数。如果提供，它应该是一个数字，表示彩虹色的变化速度。
 * :(\\d*\\.?\\d+))?：这是可选的饱和度参数。如果提供，它应该是一个浮点数，表示彩虹色的饱和度。
 * :(\\d*\\.?\\d+))?：这是可选的亮度参数。如果提供，它应该是一个浮点数，表示彩虹色的亮度。
 * :(l|L|loop))?：这是可选的循环参数。如果提供，表示彩虹色应该循环。
 * 渐变色：<gradient|g>(#(\d+))?((:#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})){2,})(:(l|L|loop))?>
 * <gradient|g>：这是标记，表示要生成渐变色。可以使用<gradient>或者<g>。
 * (#(\\d+))?：这是可选的速度参数。如果提供，它应该是一个数字，表示渐变色的变化速度。
 * ((:#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})){2,})：这是必需的颜色参数。它应该是两个或更多的十六进制颜色代码，表示渐变色的起始和结束颜色。
 * :(l|L|loop))?：这是可选的循环参数。如果提供，表示渐变色应该循环。
 * <p>
 * 示例
 * <p> <rainbow>这是一个彩虹色的文本。
 * <p> <rainbow#10>这是一个速度为10的彩虹色的文本。
 * <p> <rainbow:0.5>这是一个饱和度为0.5的彩虹色的文本。
 * <p> <rainbow:1.0:0.5>这是一个饱和度为1.0，亮度为0.5的彩虹色的文本。
 * <p> <rainbow:1.0:1.0:loop>这是一个饱和度为1.0，亮度为1.0的循环彩虹色的文本。
 * <p> <rainbow#10:0.5:0.5:loop>：表示一个彩虹色，其变化速度为10，饱和度为0.5，亮度为0.5，并且是循环的。
 * <p> <gradient:#FF0000:#00FF00>这是一个从红色到绿色的渐变色的文本。
 * <p> <gradient:#FF0000:#00FF00:#0000FF>这是一个从红色到绿色再到蓝色的渐变色的文本。
 * <p> <gradient#10:#FF0000:#00FF00:#0000FF>这是一个速度为10的从红色到绿色再到蓝色的渐变色的文本。
 */
public final class HexUtils {

    private static final int CHARS_UNTIL_LOOP = 30;
    private static final Pattern RAINBOW_PATTERN = Pattern.compile("<(?<type>rainbow|r)(#(?<speed>\\d+))?(:(?<saturation>\\d*\\.?\\d+))?(:(?<brightness>\\d*\\.?\\d+))?(:(?<loop>l|L|loop))?>");
    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<(?<type>gradient|g)(#(?<speed>\\d+))?(?<hex>(:#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})){2,})(:(?<loop>l|L|loop))?>");
    private static final List<Pattern> HEX_PATTERNS = Arrays.asList(
            Pattern.compile("<#([A-Fa-f0-9]){6}>"),   // <#FFFFFF>
            Pattern.compile("\\{#([A-Fa-f0-9]){6}}"), // {#FFFFFF}
            Pattern.compile("&#([A-Fa-f0-9]){6}"),    // &#FFFFFF
            Pattern.compile("#([A-Fa-f0-9]){6}")      // #FFFFFF
    );

    //这个模式用于在彩色字符串中识别停止点。
    // 它匹配各种颜色格式，包括：
    // - 彩虹：<rainbow|r>(#(\\d+))?(:(\\d*\\.?\\d+))?(:(\\d*\\.?\\d+))?(:(l|L|loop))?>
    // - 渐变：<gradient|g>(#(\\d+))?((:#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})){2,})(:(l|L|loop))?>
    // - 传统颜色代码：(&[a-f0-9r])
    // - 各种格式的十六进制颜色代码：<#([A-Fa-f0-9]){6}>, \\{#([A-Fa-f0-9]){6}}, &#([A-Fa-f0-9]){6}, #([A-Fa-f0-9]){6}
    // - Bukkit ChatColor颜色代码：org.bukkit.ChatColor.COLOR_CHAR
    private static final Pattern STOP = Pattern.compile(
            "<(rainbow|r)(#(\\d+))?(:(\\d*\\.?\\d+))?(:(\\d*\\.?\\d+))?(:(l|L|loop))?>|" +
                    "<(gradient|g)(#(\\d+))?((:#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})){2,})(:(l|L|loop))?>|" +
                    "(&[a-f0-9r])|" +
                    "<#([A-Fa-f0-9]){6}>|" +
                    "\\{#([A-Fa-f0-9]){6}}|" +
                    "&#([A-Fa-f0-9]){6}|" +
                    "#([A-Fa-f0-9]){6}|" +
                    org.bukkit.ChatColor.COLOR_CHAR
    );

    private HexUtils() {

    }

    /**
     * 如果存在，从正则表达式匹配器中获取捕获组
     *
     * @param matcher 正则表达式匹配器
     * @param group   要获取的捕获组的名称
     * @return 捕获组的值，如果未找到则返回null
     */
    private static String getCaptureGroup(Matcher matcher, String group) {
        try {
            return matcher.group(group);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 向 CommandSender 发送彩色消息
     *
     * @param sender  要发送的 CommandSender
     * @param message 要发送的消息
     */
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(colorify(message));
    }

    /**
     * 解析渐变色，十六进制颜色和传统颜色代码
     *
     * @param message 消息
     * @return 替换颜色后的消息
     */
    public static String colorify(String message) {
        String parsed = message;
        if (NMSUtil.getVersionNumber() >= 16) {
            parsed = parseLegacy(parsed);
            parsed = parseRainbow(parsed);
            parsed = parseGradients(parsed);
            parsed = parseHex(parsed);
        } else {
            parsed = parseLegacy(parsed);
        }
        return parsed;
    }

    public static String parseRainbow(String message) {
        String parsed = message;

        Matcher matcher = RAINBOW_PATTERN.matcher(parsed);
        while (matcher.find()) {
            StringBuilder parsedRainbow = new StringBuilder();

            // Possible parameters and their defaults
            int speed = -1;
            float saturation = 1.0F;
            float brightness = 1.0F;
            boolean looping = getCaptureGroup(matcher, "looping") != null;

            String speedGroup = getCaptureGroup(matcher, "speed");
            if (speedGroup != null) {
                try {
                    speed = Integer.parseInt(speedGroup);
                } catch (NumberFormatException ignored) {
                }
            }

            String saturationGroup = getCaptureGroup(matcher, "saturation");
            if (saturationGroup != null) {
                try {
                    saturation = Float.parseFloat(saturationGroup);
                } catch (NumberFormatException ignored) {
                }
            }

            String brightnessGroup = getCaptureGroup(matcher, "brightness");
            if (brightnessGroup != null) {
                try {
                    brightness = Float.parseFloat(brightnessGroup);
                } catch (NumberFormatException ignored) {
                }
            }

            int stop = findStop(parsed, matcher.end());
            String content = parsed.substring(matcher.end(), stop);
            int contentLength = content.length();
            char[] chars = content.toCharArray();
            for (int i = 0; i < chars.length - 1; i++)
                if (chars[i] == '&' && "KkLlMmNnOoRr".indexOf(chars[i + 1]) > -1)
                    contentLength -= 2;

            int length = looping ? Math.min(contentLength, CHARS_UNTIL_LOOP) : contentLength;

            ColorGenerator rainbow;
            if (speed == -1) {
                rainbow = new Rainbow(length, saturation, brightness);
            } else {
                rainbow = new AnimatedRainbow(length, saturation, brightness, speed);
            }

            String compoundedFormat = ""; // Carry the format codes through the rainbow gradient
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c == '&' && i + 1 < chars.length) {
                    char next = chars[i + 1];
                    org.bukkit.ChatColor color = org.bukkit.ChatColor.getByChar(next);
                    if (color != null && color.isFormat()) {
                        compoundedFormat += String.valueOf(ChatColor.COLOR_CHAR) + next;
                        i++; // Skip next character
                        continue;
                    }
                }
                parsedRainbow.append(rainbow.nextChatColor()).append(compoundedFormat).append(c);
            }

            String before = parsed.substring(0, matcher.start());
            String after = parsed.substring(stop);
            parsed = before + parsedRainbow + after;
            matcher = RAINBOW_PATTERN.matcher(parsed);
        }

        return parsed;
    }

    public static String parseGradients(String message) {
        String parsed = message;

        Matcher matcher = GRADIENT_PATTERN.matcher(parsed);
        while (matcher.find()) {
            StringBuilder parsedGradient = new StringBuilder();

            int speed = -1;
            boolean looping = getCaptureGroup(matcher, "loop") != null;

            List<Color> hexSteps = Arrays.stream(getCaptureGroup(matcher, "hex").substring(1).split(":"))
                    .map(x -> x.length() != 4 ? x : String.format("#%s%s%s%s%s%s", x.charAt(1), x.charAt(1), x.charAt(2), x.charAt(2), x.charAt(3), x.charAt(3)))
                    .map(Color::decode)
                    .collect(Collectors.toList());

            String speedGroup = getCaptureGroup(matcher, "speed");
            if (speedGroup != null) {
                try {
                    speed = Integer.parseInt(speedGroup);
                } catch (NumberFormatException ignored) {
                }
            }

            int stop = findStop(parsed, matcher.end());
            String content = parsed.substring(matcher.end(), stop);
            int contentLength = content.length();
            char[] chars = content.toCharArray();
            for (int i = 0; i < chars.length - 1; i++)
                if (chars[i] == '&' && "KkLlMmNnOoRr".indexOf(chars[i + 1]) > -1)
                    contentLength -= 2;

            int length = looping ? Math.min(contentLength, CHARS_UNTIL_LOOP) : contentLength;
            ColorGenerator gradient;
            if (speed == -1) {
                gradient = new Gradient(hexSteps, length);
            } else {
                gradient = new AnimatedGradient(hexSteps, length, speed);
            }

            String compoundedFormat = ""; // Carry the format codes through the gradient
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c == '&' && i + 1 < chars.length) {
                    char next = chars[i + 1];
                    org.bukkit.ChatColor color = org.bukkit.ChatColor.getByChar(next);
                    if (color != null && color.isFormat()) {
                        compoundedFormat += String.valueOf(ChatColor.COLOR_CHAR) + next;
                        i++; // Skip next character
                        continue;
                    }
                }
                parsedGradient.append(gradient.nextChatColor()).append(compoundedFormat).append(c);
            }

            String before = parsed.substring(0, matcher.start());
            String after = parsed.substring(stop);
            parsed = before + parsedGradient + after;
            matcher = GRADIENT_PATTERN.matcher(parsed);
        }

        return parsed;
    }

    public static String parseHex(String message) {
        String parsed = message;

        for (Pattern pattern : HEX_PATTERNS) {
            Matcher matcher = pattern.matcher(parsed);
            while (matcher.find()) {
                String color = translateHex(cleanHex(matcher.group())).toString();
                String before = parsed.substring(0, matcher.start());
                String after = parsed.substring(matcher.end());
                parsed = before + color + after;
                matcher = pattern.matcher(parsed);
            }
        }

        return parsed;
    }

    public static String parseLegacy(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Returns the index before the color changes
     *
     * @param content     The content to search through
     * @param searchAfter The index at which to search after
     * @return the index of the color stop, or the end of the string index if none is found
     */
    private static int findStop(String content, int searchAfter) {
        Matcher matcher = STOP.matcher(content);
        while (matcher.find()) {
            if (matcher.start() > searchAfter)
                return matcher.start();
        }
        return content.length();
    }

    private static String cleanHex(String hex) {
        if (hex.startsWith("<") || hex.startsWith("{")) {
            return hex.substring(1, hex.length() - 1);
        } else if (hex.startsWith("&")) {
            return hex.substring(1);
        } else {
            return hex;
        }
    }

    /**
     * Finds the closest hex or ChatColor value as the hex string
     *
     * @param hex The hex color
     * @return The closest ChatColor value
     */
    public static ChatColor translateHex(String hex) {
        if (NMSUtil.getVersionNumber() >= 16)
            return ChatColor.of(hex);
        return translateHex(Color.decode(hex));
    }

    public static ChatColor translateHex(Color color) {
        if (NMSUtil.getVersionNumber() >= 16)
            return ChatColor.of(color);

        int minDist = Integer.MAX_VALUE;
        ChatColor legacy = ChatColor.WHITE;
        for (ChatColorHexMapping mapping : ChatColorHexMapping.values()) {
            int r = mapping.getRed() - color.getRed();
            int g = mapping.getGreen() - color.getGreen();
            int b = mapping.getBlue() - color.getBlue();
            int dist = r * r + g * g + b * b;
            if (dist < minDist) {
                minDist = dist;
                legacy = mapping.getChatColor();
            }
        }

        return legacy;
    }

    /**
     * Maps hex codes to ChatColors
     */
    public enum ChatColorHexMapping {

        BLACK(0x000000, ChatColor.BLACK),
        DARK_BLUE(0x0000AA, ChatColor.DARK_BLUE),
        DARK_GREEN(0x00AA00, ChatColor.DARK_GREEN),
        DARK_AQUA(0x00AAAA, ChatColor.DARK_AQUA),
        DARK_RED(0xAA0000, ChatColor.DARK_RED),
        DARK_PURPLE(0xAA00AA, ChatColor.DARK_PURPLE),
        GOLD(0xFFAA00, ChatColor.GOLD),
        GRAY(0xAAAAAA, ChatColor.GRAY),
        DARK_GRAY(0x555555, ChatColor.DARK_GRAY),
        BLUE(0x5555FF, ChatColor.BLUE),
        GREEN(0x55FF55, ChatColor.GREEN),
        AQUA(0x55FFFF, ChatColor.AQUA),
        RED(0xFF5555, ChatColor.RED),
        LIGHT_PURPLE(0xFF55FF, ChatColor.LIGHT_PURPLE),
        YELLOW(0xFFFF55, ChatColor.YELLOW),
        WHITE(0xFFFFFF, ChatColor.WHITE);

        private final int r, g, b;
        @Getter
        private final ChatColor chatColor;

        ChatColorHexMapping(int hex, ChatColor chatColor) {
            this.r = (hex >> 16) & 0xFF;
            this.g = (hex >> 8) & 0xFF;
            this.b = hex & 0xFF;
            this.chatColor = chatColor;
        }

        public int getRed() {
            return this.r;
        }

        public int getGreen() {
            return this.g;
        }

        public int getBlue() {
            return this.b;
        }

    }

    public interface ColorGenerator {

        /**
         * @return the next color in the sequence as a ChatColor
         */
        ChatColor nextChatColor();

        /**
         * @return the next color in the sequence as a Color
         */
        Color nextColor();

    }

    /**
     * Allows generation of a multi-part gradient with a defined number of steps
     */
    public static class Gradient implements ColorGenerator {

        private final List<TwoStopGradient> gradients;
        private final int steps;
        protected long step;

        public Gradient(List<Color> colors, int steps) {
            if (colors.size() < 2)
                throw new IllegalArgumentException("Must provide at least 2 colors");

            this.gradients = new ArrayList<>();
            this.steps = steps;
            this.step = 0;

            float increment = (float) (this.steps - 1) / (colors.size() - 1);
            for (int i = 0; i < colors.size() - 1; i++)
                this.gradients.add(new TwoStopGradient(colors.get(i), colors.get(i + 1), increment * i, increment * (i + 1)));
        }

        @Override
        public ChatColor nextChatColor() {
            // Gradients will use the first color if the entire spectrum won't be available to preserve prettiness
            if (NMSUtil.getVersionNumber() < 16 || this.steps <= 1)
                return translateHex(this.gradients.get(0).colorAt(0));
            return translateHex(this.nextColor());
        }

        @Override
        public Color nextColor() {
            // Do some wizardry to get a function that bounces back and forth between 0 and a cap given an increasing input
            // Thanks to BomBardyGamer for assisting with this
            int adjustedStep = (int) Math.round(Math.abs(((2 * Math.asin(Math.sin(this.step * (Math.PI / (2 * this.steps))))) / Math.PI) * this.steps));

            Color color;
            if (this.gradients.size() < 2) {
                color = this.gradients.get(0).colorAt(adjustedStep);
            } else {
                float segment = (float) this.steps / this.gradients.size();
                int index = (int) Math.min(Math.floor(adjustedStep / segment), this.gradients.size() - 1);
                color = this.gradients.get(index).colorAt(adjustedStep);
            }

            this.step++;
            return color;
        }

        public static class TwoStopGradient {

            private final Color startColor;
            private final Color endColor;
            private final float lowerRange;
            private final float upperRange;

            private TwoStopGradient(Color startColor, Color endColor, float lowerRange, float upperRange) {
                this.startColor = startColor;
                this.endColor = endColor;
                this.lowerRange = lowerRange;
                this.upperRange = upperRange;
            }

            /**
             * Gets the color of this gradient at the given step
             *
             * @param step The step
             * @return The color of this gradient at the given step
             */
            public Color colorAt(int step) {
                return new Color(
                        this.calculateHexPiece(step, this.startColor.getRed(), this.endColor.getRed()),
                        this.calculateHexPiece(step, this.startColor.getGreen(), this.endColor.getGreen()),
                        this.calculateHexPiece(step, this.startColor.getBlue(), this.endColor.getBlue())
                );
            }

            private int calculateHexPiece(int step, int channelStart, int channelEnd) {
                float range = this.upperRange - this.lowerRange;
                if (range == 0) // No range, don't divide by 0
                    return channelStart;
                float interval = (channelEnd - channelStart) / range;
                return Math.min(Math.max(Math.round(interval * (step - this.lowerRange) + channelStart), 0), 255);
            }

        }

    }

    /**
     * Allows generation of an animated multi-part gradient with a defined number of steps
     */
    public static class AnimatedGradient extends Gradient {

        public AnimatedGradient(List<Color> colors, int steps, int speed) {
            super(colors, steps);

            this.step = System.currentTimeMillis() / speed;
        }

    }

    /**
     * Allows generation of a rainbow gradient with a fixed number of steps
     */
    public static class Rainbow implements ColorGenerator {

        protected final float hueStep, saturation, brightness;
        protected float hue;

        public Rainbow(int totalColors, float saturation, float brightness) {
            if (totalColors < 1)
                totalColors = 1;

            this.hueStep = 1.0F / totalColors;
            this.saturation = Math.max(0, Math.min(1, saturation));
            this.brightness = Math.max(0, Math.min(1, brightness));
            this.hue = 0;
        }

        @Override
        public ChatColor nextChatColor() {
            return translateHex(this.nextColor());
        }

        @Override
        public Color nextColor() {
            Color color = Color.getHSBColor(this.hue, this.saturation, this.brightness);
            this.hue += this.hueStep;
            return color;
        }

    }

    /**
     * Allows generation of an animated rainbow gradient with a fixed number of steps
     */
    public static class AnimatedRainbow extends Rainbow {

        public AnimatedRainbow(int totalColors, float saturation, float brightness, int speed) {
            super(totalColors, saturation, brightness);

            this.hue = (float) ((((Math.floor(System.currentTimeMillis() / 50.0)) / 360) * speed) % 1);
        }

    }

}