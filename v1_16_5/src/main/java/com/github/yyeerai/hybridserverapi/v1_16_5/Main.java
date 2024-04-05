package com.github.yyeerai.hybridserverapi.v1_16_5;

import com.github.yyeerai.hybridserverapi.v1_16_5.api.pokemon.PapiHook;
import com.github.yyeerai.hybridserverapi.v1_16_5.api.pokemon.PokemonApi;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Main implements Listener {

    private final JavaPlugin plugin;
    private PokemonApi pokemonApi;


    public Main(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {

        plugin.getLogger().info("你的服务器正在使用混合端API v1.16.5");
        plugin.getLogger().info("正在初始化...");
        File file = new File(String.valueOf(plugin.getDataFolder()));
        if (!file.exists()) {
            if(file.mkdirs()){
                plugin.getLogger().info("文件夹创建成功");
            }
        }
        pokemonApi = new PokemonApi(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        PapiHook papiHook = new PapiHook(this);
        papiHook.register();
        plugin.getLogger().info("初始化成功");
    }

    public void onStop() {
        plugin.getLogger().info("正在卸载...");
        pokemonApi = null;
        plugin.getLogger().info("卸载成功");
    }

}