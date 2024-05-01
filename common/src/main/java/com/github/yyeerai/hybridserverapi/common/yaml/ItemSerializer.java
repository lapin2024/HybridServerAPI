package com.github.yyeerai.hybridserverapi.common.yaml;

import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.serialization.YamlSerializer;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.utils.supplier.MapSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ItemSerializer implements YamlSerializer {


    private static final ItemSerializer INSTANCE = new ItemSerializer();

    public static ItemSerializer getInstance() {
        return INSTANCE;
    }

    private static final Set<Class<?>> SUPPORTED_CLASSES = new HashSet<Class<?>>() {
        {
            add(BukkitItemStack.class);
        }
    };

    @Override
    public @Nullable Object deserialize(@NotNull Map<Object, Object> map) {
        if (!map.containsKey("hybridItem")) {
            return null;
        }
        Map<String, Object> itemMap = new HashMap<>();
        map.forEach((key, value) -> itemMap.put(key.toString(), value));
        return new BukkitItemStack(itemMap);
    }

    @Override
    public @Nullable <T> Map<Object, Object> serialize(@NotNull T object, @NotNull MapSupplier supplier) {
        if (!(object instanceof BukkitItemStack)) {
            return null;
        }
        BukkitItemStack bukkitItemStack = (BukkitItemStack) object;
        Map<Object, Object> serialized = supplier.supply(1);
        serialized.put("hybridItem", true);
        Map<String, Object> stringObjectMap = bukkitItemStack.serialize();
        serialized.putAll(stringObjectMap);
        return serialized;
    }

    @Override
    public @NotNull Set<Class<?>> getSupportedClasses() {
        return SUPPORTED_CLASSES;
    }

    @Override
    public @NotNull Set<Class<?>> getSupportedParentClasses() {
        return Collections.emptySet();
    }
}