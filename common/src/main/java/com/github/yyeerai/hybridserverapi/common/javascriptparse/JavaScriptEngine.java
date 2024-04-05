package com.github.yyeerai.hybridserverapi.common.javascriptparse;

import lombok.Getter;
import org.bukkit.Bukkit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@Getter
public class JavaScriptEngine {

    public static ScriptEngine ENGINE;


    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        ENGINE = manager.getEngineByName("nashorn");
        ENGINE.put("Bukkit", Bukkit.getServer());
    }


}