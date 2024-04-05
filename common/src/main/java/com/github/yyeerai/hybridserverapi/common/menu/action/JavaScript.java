package com.github.yyeerai.hybridserverapi.common.menu.action;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.common.javascriptparse.JavaScriptEngine;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.script.ScriptEngine;
import java.util.List;

public class JavaScript extends AbstractActionExecutor {

    private final String script;

    public JavaScript(JavaPlugin plugin, String content) {
        super(plugin, content);
        String substring = content.substring(11);
        List<String> angleBracketsContent = getAngleBracketsContent(substring);
        script = removeAngleBracketsContent(substring).trim().replaceAll(",+$", "");
        delay = getDelay(angleBracketsContent);
        chance = getChance(angleBracketsContent);
    }

    @Override
    public void execute(Player player, @Nullable String argument, @Nullable String catchArgument) {
        initializationParameters(argument, catchArgument);
        if (script == null || script.isEmpty()) {
            return;
        }
        @NotNull String newScript = PlaceholderAPI.setPlaceholders(player, HexUtils.colorify(script.replace("%arg%", this.argument != null ? this.argument : "").replace("%catchArg%", this.catchArgument != null ? this.catchArgument : "")));
        ScriptEngine scriptEngine = JavaScriptEngine.ENGINE;
        scriptEngine.put("player", player);
        if (chance > 0.0D && Math.random() > chance) {
            return;
        }
        if (delay > 0) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                try {
                    scriptEngine.eval(newScript);
                } catch (Exception e) {
                    plugin.getLogger().warning("JavaScript解析错误: " + e.getMessage());
                }
            }, delay);
        } else {
            try {
                scriptEngine.eval(newScript);
            } catch (Exception e) {
                plugin.getLogger().warning("JavaScript解析错误: " + e.getMessage());
            }
        }
    }
}