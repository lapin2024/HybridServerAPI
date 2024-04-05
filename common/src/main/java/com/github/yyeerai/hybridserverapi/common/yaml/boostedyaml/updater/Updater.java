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
package com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.updater;

import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.block.implementation.Section;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.general.GeneralSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.updater.UpdaterSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.updater.operators.Mapper;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.updater.operators.Merger;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.updater.operators.Relocator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Updater class responsible for executing the whole process:
 * <ol>
 *     <li>loading version IDs of the document and defaults,</li>
 *     <li>comparing the IDs (to check if updating, downgrading...),</li>
 *     <li>applying relocations and mapping functions to the document (if the files are not the same version ID) - see {@link Relocator} and {@link Mapper},</li>
 *     <li>marking ignored blocks in the document,</li>
 *     <li>merging both files - see {@link Merger#merge(Section, Section, UpdaterSettings)}.</li>
 * </ol>
 */
public class Updater {

    /**
     * Updates the given document against the given defaults and settings. The process consists of:
     * <ol>
     *     <li>loading file version IDs,</li>
     *     <li>comparing the IDs (to check if updating, downgrading...),</li>
     *     <li>applying relocations and mapping functions to the document (if the files are not the same version ID) - see {@link Relocator} and {@link Mapper},</li>
     *     <li>marking ignored blocks in the document,</li>
     *     <li>merging both files - see {@link Merger#merge(Section, Section, UpdaterSettings)}.</li>
     * </ol>
     *
     * @param document        the document section to update
     * @param defaults        section equivalent in the defaults
     * @param updaterSettings updater settings to use
     * @param generalSettings general settings to use
     * @throws IOException an IO error
     */
    public static void update(@NotNull Section document, @NotNull Section defaults, @NotNull UpdaterSettings updaterSettings, @NotNull GeneralSettings generalSettings) throws IOException {
        //Apply versioning stuff
        if (VersionedOperations.run(document, defaults, updaterSettings, generalSettings.getRouteSeparator()))
            return;
        //Merge
        Merger.merge(document, defaults, updaterSettings);
        //If present
        if (updaterSettings.getVersioning() != null)
            //Set the new ID
            updaterSettings.getVersioning().updateVersionID(document, defaults);

        //If auto save is enabled
        if (updaterSettings.isAutoSave())
            document.getRoot().save();
    }

}