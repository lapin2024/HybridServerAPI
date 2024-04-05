package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatchExecutor extends AbstractActionExecutor {


    private final List<AbstractActionExecutor> actionExecutors = new ArrayList<>();
    private Player player;
    private final String askText;


    public CatchExecutor(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(14);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        String trim = removeAngleBracketsContent(substring).trim();
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
        String textPattern = "text:([^,]*)";
        String actionsPattern = "actions:\\{([^}]*)\\}";
        Pattern pattern;
        Matcher matcher;
        List<String> actions = new ArrayList<>();
        pattern = Pattern.compile(textPattern);
        matcher = pattern.matcher(trim);
        if (matcher.find()) {
            askText = matcher.group(1).trim();
        }else {
            askText = "请输入: ";
        }
        pattern = Pattern.compile(actionsPattern);
        matcher = pattern.matcher(trim);
        if (matcher.find()) {
            String a = matcher.group(1).trim();
            actions = Arrays.asList(a.split("\\$"));
        }
        for (String action : actions) {
            ActionExecutorFactory.getActionExecutor(plugin, action).ifPresent(actionExecutors::add);
        }
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        this.player = player;
        ConversationFactory conversationFactory = new ConversationFactory(plugin);
        Conversation conversation = conversationFactory.withFirstPrompt(new CatchPrompt()).withLocalEcho(false).buildConversation(player);
        conversation.begin();
        // 创建一个新的线程来处理对话过期
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (conversation.getState().equals(Conversation.ConversationState.ABANDONED)) {
                return;
            }
            conversation.abandon();
            player.sendMessage("对话已过期");
        }, 20L * 10); // 这里的10是过期时间，单位是秒

    }

    private class CatchPrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext conversationContext) {
            return HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, askText));
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String input) {
            catchArgument = input;
            if (chance > 0.0D && Math.random() > chance) {
                return END_OF_CONVERSATION;
            }
            if (delay > 0) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> actionExecutors.forEach(actionExecutor -> actionExecutor.execute(player, argument, catchArgument)), delay);
            } else {
                Bukkit.getScheduler().runTask(plugin, () -> actionExecutors.forEach(actionExecutor -> actionExecutor.execute(player, argument, catchArgument)));
            }
            return END_OF_CONVERSATION;
        }
    }


}