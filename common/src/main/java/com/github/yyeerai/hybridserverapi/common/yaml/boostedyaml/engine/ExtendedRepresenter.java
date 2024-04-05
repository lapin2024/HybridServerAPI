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
package com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.engine;

import com.github.yyeerai.hybridserverapi.common.yaml.YamlDocument;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.block.Block;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.block.Comments;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.block.implementation.Section;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.dumper.DumperSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.general.GeneralSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.api.DumpSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.api.RepresentToNode;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.common.ScalarStyle;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.nodes.Node;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.nodes.NodeTuple;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.representer.StandardRepresenter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * A custom representer for the SnakeYAML Engine allowing to represent {@link Section} objects, serializing custom
 * objects, all of which while keeping comments and without any additional time consumption.
 */
public class ExtendedRepresenter extends StandardRepresenter {

    //General settings
    private final GeneralSettings generalSettings;
    //Dumper settings
    private final DumperSettings dumperSettings;

    /**
     * Creates an instance of the representer.
     *
     * @param generalSettings general settings of the root's file, whose contents are going to be represented
     * @param dumperSettings  dumper settings
     * @param engineSettings  engine dump settings already built by the dumper settings
     */
    public ExtendedRepresenter(@NotNull GeneralSettings generalSettings, @NotNull DumperSettings dumperSettings, @NotNull DumpSettings engineSettings) {
        //Call the superclass constructor
        super(engineSettings);
        //Set
        this.generalSettings = generalSettings;
        this.dumperSettings = dumperSettings;

        //Representers
        RepresentToNode representSection = new RepresentSection(), representSerializable = new RepresentSerializable();
        //Add representers
        super.representers.put(Section.class, representSection);
        super.representers.put(YamlDocument.class, representSection);
        super.representers.put(Enum.class, new RepresentEnum());
        super.representers.put(String.class, new RepresentString(super.representers.get(String.class)));
        //Add all types
        for (Class<?> clazz : generalSettings.getSerializer().getSupportedClasses())
            super.representers.put(clazz, representSerializable);
        for (Class<?> clazz : generalSettings.getSerializer().getSupportedParentClasses())
            super.parentClassRepresenters.put(clazz, representSerializable);
    }

    /**
     * Creates an instance of the representer.
     *
     * @param generalSettings general settings of the root's file, whose contents are going to be represented
     * @param dumperSettings  dumper settings
     * @see #ExtendedRepresenter(GeneralSettings, DumperSettings)
     */
    public ExtendedRepresenter(@NotNull GeneralSettings generalSettings, @NotNull DumperSettings dumperSettings) {
        this(generalSettings, dumperSettings, dumperSettings.buildEngineSettings());
    }

    /**
     * Applies the given block's (if not <code>null</code>) key comments to the given node and returns the given node
     * itself.
     *
     * @param block the block whose key comments to apply
     * @param node  node to apply the comments to
     * @return the given node
     */
    @NotNull
    public Node applyKeyComments(@Nullable Block<?> block, @NotNull Node node) {
        //If not null
        if (block != null) {
            //Set
            node.setBlockComments(Comments.get(block, Comments.NodeType.KEY, Comments.Position.BEFORE));
            node.setInLineComments(Comments.get(block, Comments.NodeType.KEY, Comments.Position.INLINE));
            node.setEndComments(Comments.get(block, Comments.NodeType.KEY, Comments.Position.AFTER));
        }
        //Return
        return node;
    }

    /**
     * Applies the given block's (if not <code>null</code>) value comments to the given node and returns the given node
     * itself.
     *
     * @param block the block whose value comments to apply
     * @param node  node to apply the comments to
     * @return the given node
     */
    @NotNull
    public Node applyValueComments(@Nullable Block<?> block, @NotNull Node node) {
        //If not null
        if (block != null) {
            //Set
            node.setBlockComments(Comments.get(block, Comments.NodeType.VALUE, Comments.Position.BEFORE));
            node.setInLineComments(Comments.get(block, Comments.NodeType.VALUE, Comments.Position.INLINE));
            node.setEndComments(Comments.get(block, Comments.NodeType.VALUE, Comments.Position.AFTER));
        }
        //Return
        return node;
    }

    @Override
    protected NodeTuple representMappingEntry(Map.Entry<?, ?> entry) {
        //Block
        Block<?> block = entry.getValue() instanceof Block ? (Block<?>) entry.getValue() : null;
        //Represent nodes
        Node key = applyKeyComments(block, representData(entry.getKey()));
        Node value = applyValueComments(block, representData(block == null ? entry.getValue() : block.getStoredValue()));
        //Create
        return new NodeTuple(key, value);
    }

    /**
     * Node representer implementation for serializable objects.
     */
    private class RepresentSerializable implements RepresentToNode {

        @Override
        public Node representData(Object data) {
            //Serialize
            Object serialized = generalSettings.getSerializer().serialize(data, generalSettings.getDefaultMapSupplier());
            //Return
            return ExtendedRepresenter.this.representData(serialized == null ? data : serialized);
        }

    }

    /**
     * Node representer implementation for {@link Section sections}.
     */
    private class RepresentSection implements RepresentToNode {

        @Override
        public Node representData(Object data) {
            //Cast
            Section section = (Section) data;
            //Return
            return applyKeyComments(section, ExtendedRepresenter.this.representData(section.getStoredValue()));
        }

    }

    /**
     * Node representer implementation for {@link Enum enums}.
     */
    private class RepresentEnum implements RepresentToNode {

        @Override
        public Node representData(Object data) {
            return ExtendedRepresenter.this.representData(((Enum<?>) data).name());
        }

    }

    private class RepresentString implements RepresentToNode {

        // Previous representer
        private final RepresentToNode previous;

        /**
         * Creates an instance of the custom string representer.
         *
         * @param previous the previous representer, used to represent the string itself
         */
        private RepresentString(@NotNull RepresentToNode previous) {
            this.previous = previous;
        }

        @Override
        public Node representData(Object data) {
            // Update the style
            ScalarStyle previousStyle = defaultScalarStyle;
            defaultScalarStyle = dumperSettings.getStringStyle();
            // Represent
            Node node = previous.representData(data);
            // Revert back
            defaultScalarStyle = previousStyle;
            return node;
        }

    }

}