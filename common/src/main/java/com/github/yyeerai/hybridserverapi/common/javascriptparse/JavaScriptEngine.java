package com.github.yyeerai.hybridserverapi.common.javascriptparse;

import cn.hutool.script.ScriptUtil;
import lombok.Getter;
import org.bukkit.Bukkit;

import javax.script.ScriptEngine;

@Getter
public class JavaScriptEngine {

    public static ScriptEngine ENGINE;


    static {
        ENGINE = ScriptUtil.getJsEngine();
        ENGINE.put("Bukkit", Bukkit.getServer());
    }


}