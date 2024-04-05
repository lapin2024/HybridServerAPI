/*
 * Copyright 2022 https://dejvokep.dev/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.updater.operators;

import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.block.implementation.Section;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.route.Route;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.updater.ValueMapper;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mapper is a utility class and one of the updating process operators, responsible for applying value mappings while
 * updating.
 */
public class Mapper {

    /**
     * Applies the provided mappers to the given section.
     *
     * @param section the section
     * @param mappers the mappers to apply
     */
    public static void apply(@NotNull Section section, @NotNull Map<Route, ValueMapper> mappers) {
        mappers.forEach(((route, mapper) -> section.getParent(route).ifPresent(parent -> {
            // Single key route
            Route key = Route.fromSingleKey(route.get(route.length() - 1));
            // There is no value
            if (!parent.getStoredValue().containsKey(key.get(0)))
                return;
            // Replace
            parent.set(key, mapper.map(parent, key));
        })));
    }

}