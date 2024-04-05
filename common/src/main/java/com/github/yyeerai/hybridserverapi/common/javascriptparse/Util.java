package com.github.yyeerai.hybridserverapi.common.javascriptparse;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Util {
    private static final String NASHORN_JAR_NAME = "nashorn-core-15.4.jar";
    private static final String ASM_NAME = "asm-all-5.2.jar";

    private static final String NASHORN_URL = "https://maven.aliyun.com/repository/central/org/openjdk/nashorn/nashorn-core/15.4/nashorn-core-15.4.jar";
    private static final String ASM = "https://maven.aliyun.com/repository/central/org/ow2/asm/asm-all/5.2/asm-all-5.2.jar";
    private static final String LIBS_DIR = "/libs/";

    public void loadNashorn(JavaPlugin plugin) {
        try {
            if (getJavaMajorVersion() < 15) {
                plugin.getLogger().warning("Nashorn引擎已经存在,跳过加载！");
                return;
            }
            File libsDir = new File(plugin.getDataFolder() + LIBS_DIR);
            if (!libsDir.exists()) {
                libsDir.mkdirs();
            }
            File nashornJar = new File(plugin.getDataFolder() + LIBS_DIR + NASHORN_JAR_NAME);
            File asmJar = new File(plugin.getDataFolder() + LIBS_DIR + ASM_NAME);
            if (!nashornJar.exists()) {
                downloadFile(NASHORN_URL, nashornJar);
            }
            if (!asmJar.exists()) {
                downloadFile(ASM, asmJar);
            }

            ClassLoader classLoader = plugin.getClass().getClassLoader();

            URL url1 = asmJar.toURI().toURL();
            URL url2 = nashornJar.toURI().toURL();
            URL[] urls = new URL[]{url1, url2};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, classLoader);
            Thread.currentThread().setContextClassLoader(urlClassLoader);
            plugin.getLogger().info("Nashorn引擎加载成功！");
        } catch (IOException e) {
            throw new RuntimeException("库文件加载失败" + e);
        }
    }

    private void downloadFile(String url, File file) throws IOException {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private int getJavaMajorVersion() {
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2);
        }
        int dotPos = version.indexOf('.');
        int dashPos = version.indexOf('-');
        return Integer.parseInt(version.substring(0, dotPos > -1 ? dotPos : dashPos > -1 ? dashPos : 1));
    }
}