package com.github.yyeerai.hybridserverapi.common.menu.requirement;

import com.github.yyeerai.hybridserverapi.common.colour.HexUtils;
import com.github.yyeerai.hybridserverapi.common.javascriptparse.JavaScriptEngine;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import javax.script.ScriptEngine;

public class JavaScript extends AbstractRequirementChecker {

    private final String script;

    public JavaScript(JavaPlugin plugin, String content) {
        super(plugin, content);
        script = content.substring(11).trim();
    }


    @Override
    public boolean meetsRequirements(Player player, @Nullable String argument) {
        initializationParameters(argument);
        boolean b = false;
        String newScript = HexUtils.colorify(PlaceholderAPI.setPlaceholders(player, script.replace("%arg%", this.argument == null ? "" : this.argument)));
        ScriptEngine engine = JavaScriptEngine.ENGINE;
        engine.put("player", player);
        Object result;
        try {
            result = engine.eval(newScript);
        } catch (Exception e) {
            plugin.getLogger().warning("JavaScript解析错误: " + e.getMessage());
            result = false;
        }
        if (result instanceof Boolean) {
            b = (boolean) result;
        }
        return b;
    }

}