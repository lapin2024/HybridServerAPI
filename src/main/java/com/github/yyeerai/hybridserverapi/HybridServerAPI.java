package com.github.yyeerai.hybridserverapi;


import com.github.yyeerai.hybridserverapi.common.broadcast.*;
import com.github.yyeerai.hybridserverapi.common.javascriptparse.Util;
import com.github.yyeerai.hybridserverapi.common.menu.api.RegisterService;
import com.github.yyeerai.hybridserverapi.common.util.NMSUtil;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@SuppressWarnings("unused")
public class HybridServerAPI extends JavaPlugin {

    public static HybridServerAPI instance;
    private int versionNumber;
    private com.github.yyeerai.hybridserverapi.v1_12_2.Main main1_12_2;
    private com.github.yyeerai.hybridserverapi.v1_16_5.Main main1_16_5;
    private com.github.yyeerai.hybridserverapi.v1_20_2.Main main1_20_2;

    @Override
    public void onEnable() {
        instance = this;
        versionNumber = NMSUtil.getVersionNumber();
        RegisterService.registerActionExecutor();
        RegisterService.registerRequirementChecker();
        RegisterService.registerEventSubscriber(this);
        registerPokemonAPI();
        registerBroadcast();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        unregisterPokemonAPI();
    }

    @Override
    public void onLoad() {
        this.getLogger().info("HybridServerAPI已启动！");
        Util util = new Util();
        util.loadNashorn(this);
    }

    private void registerPokemonAPI() {
        boolean isPixelmon = false;
        try {
            Class<?> aClass = Class.forName("com.pixelmonmod.pixelmon.Pixelmon");
            isPixelmon = true;
        } catch (ClassNotFoundException ignored) {
        }
        if (!isPixelmon) {
            getLogger().warning("未检测到PixelmonMod，宝可梦API将不会启动！");
            return;
        }
        if (versionNumber == 12) {
            main1_12_2 = new com.github.yyeerai.hybridserverapi.v1_12_2.Main(this);
            main1_12_2.init();
            this.getLogger().info("服务器版本为1.12.2，已启动宝可梦API！");
        } else if (versionNumber == 16) {
            main1_16_5 = new com.github.yyeerai.hybridserverapi.v1_16_5.Main(this);
            main1_16_5.init();
            this.getLogger().info("服务器版本为1.16.5，已启动宝可梦API！");
        } else if (versionNumber == 20) {
            main1_20_2 = new com.github.yyeerai.hybridserverapi.v1_20_2.Main(this);
            main1_20_2.init();
            this.getLogger().info("服务器版本为1.20.2，已启动宝可梦API！");
        }
    }

    private void unregisterPokemonAPI() {
        switch (versionNumber) {
            case 12:
                if (main1_12_2 != null) {
                    main1_12_2.onStop();
                }
                break;
            case 16:
                if (main1_16_5 != null) {
                    main1_16_5.onStop();
                }
                break;
            case 20:
                if (main1_20_2 != null) {
                    main1_20_2.onStop();
                }
                break;
            default:
                getLogger().warning("Unsupported server version: " + NMSUtil.getVersion());
                break;
        }
    }

    private void registerBroadcast() {
        BroadcastType.registerBroadcast(BroadcastType.CHAT, Chat.class);
        BroadcastType.registerBroadcast(BroadcastType.ACTIONBAR, ActionBar.class);
        BroadcastType.registerBroadcast(BroadcastType.TITLE, Title.class);
        BroadcastType.registerBroadcast(BroadcastType.BOSSBAR, BossBar.class);
        if (versionNumber == 12) {
            BroadcastType.registerBroadcast(BroadcastType.POKENOTICE, com.github.yyeerai.hybridserverapi.v1_12_2.api.broadcast.PokeNotice.class);
        } else if (versionNumber == 16) {
            BroadcastType.registerBroadcast(BroadcastType.POKENOTICE, com.github.yyeerai.hybridserverapi.v1_16_5.api.broadcast.PokeNotice.class);
        } else if (versionNumber == 20) {
            BroadcastType.registerBroadcast(BroadcastType.POKENOTICE, com.github.yyeerai.hybridserverapi.v1_20_2.api.broadcast.PokeNotice.class);
        }
    }
}