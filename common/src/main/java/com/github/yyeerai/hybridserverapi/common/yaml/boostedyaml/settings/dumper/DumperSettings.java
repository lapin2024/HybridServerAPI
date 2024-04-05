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
package com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.settings.dumper;

import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.api.DumpSettings;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.api.DumpSettingsBuilder;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.common.FlowStyle;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.common.NonPrintableStyle;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.common.ScalarStyle;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.common.SpecVersion;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.nodes.Tag;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.resolver.ScalarResolver;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.serializer.AnchorGenerator;
import com.github.yyeerai.hybridserverapi.common.yaml.engine.v2.serializer.NumberAnchorGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Dumper settings cover all options related explicitly (only) to file dumping.
 * <p>
 * Settings introduced by BoostedYAML follow builder design pattern, e.g. you may build your own settings using
 * <code>DumperSettings.builder() //configure// .build()</code>
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class DumperSettings {

    /**
     * Default dumper settings.
     */
    public static final DumperSettings DEFAULT = builder().build();
    //SnakeYAML Engine dump settings builder
    private final DumpSettingsBuilder builder;
    //Anchor generator supplier
    private final Supplier<AnchorGenerator> generatorSupplier;
    //String style
    private final ScalarStyle stringStyle;

    /**
     * Creates final, immutable dumper settings from the given builder.
     *
     * @param builder the builder
     */
    private DumperSettings(Builder builder) {
        this.builder = builder.builder;
        this.generatorSupplier = builder.anchorGeneratorSupplier;
        this.stringStyle = builder.stringStyle;
    }

    /**
     * Returns a new builder.
     *
     * @return the new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates and returns a new builder from the given, already created SnakeYAML Engine settings builder.
     * <p>
     * <b>Note that the given builder is not cloned, so it is in the caller's best interest to never change it's
     * settings from now on.</b>
     * <p>
     * Please note that {@link Builder#setAnchorGenerator(Supplier)} still has to be called (if you want to alter the
     * default), as they are not part of the Engine's settings.
     *
     * @param builder the underlying builder
     * @return the new builder
     */
    public static Builder builder(DumpSettingsBuilder builder) {
        return new Builder(builder);
    }

    /**
     * Returns a new builder with the same configuration as the given settings.
     *
     * @param settings preset settings
     * @return the new builder
     */
    public static Builder builder(DumperSettings settings) {
        return builder(settings.builder).setAnchorGenerator(settings.generatorSupplier);
    }

    /**
     * Builds the SnakeYAML Engine settings.
     *
     * @return the new settings
     */
    public DumpSettings buildEngineSettings() {
        return builder.setAnchorGenerator(generatorSupplier.get()).setDumpComments(true).build();
    }

    /**
     * Returns the style to use for {@link String} instances.
     *
     * @return the style to use for {@link String} instances
     */
    public ScalarStyle getStringStyle() {
        return stringStyle;
    }

    /**
     * Represents supported encoding. Please learn more <a
     * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setUseUnicodeEncoding(boolean)">here</a>.
     */
    public enum Encoding {
        /**
         * Unicode encoding.
         */
        UNICODE,
        /**
         * ASCII encoding.
         */
        ASCII;

        /**
         * Returns <code>true</code> if the encoding represented is Unicode; <code>false</code> otherwise.
         *
         * @return if the represented encoding is Unicode
         */
        boolean isUnicode() {
            return this == Encoding.UNICODE;
        }
    }

    /**
     * Builder for dumper settings; wrapper for SnakeYAML Engine's {@link DumpSettingsBuilder} class which is more
     * detailed, provides more options and possibilities, hides options which should not be configured.
     */
    public static class Builder {

        /**
         * Default anchor generator supplier.
         */
        public static final Supplier<AnchorGenerator> DEFAULT_ANCHOR_GENERATOR = () -> new NumberAnchorGenerator(0);
        /**
         * Default flow style.
         */
        public static final FlowStyle DEFAULT_FLOW_STYLE = FlowStyle.BLOCK;
        /**
         * Default scalar style.
         */
        public static final ScalarStyle DEFAULT_SCALAR_STYLE = ScalarStyle.PLAIN;
        /**
         * Default string style.
         */
        public static final ScalarStyle DEFAULT_STRING_STYLE = ScalarStyle.PLAIN;
        /**
         * If to add document start by default.
         */
        public static final boolean DEFAULT_START_MARKER = false;
        /**
         * If to add document end by default.
         */
        public static final boolean DEFAULT_END_MARKER = false;
        /**
         * Default root tag.
         */
        public static final Tag DEFAULT_ROOT_TAG = null;
        /**
         * If to dump in canonical form by default.
         */
        public static final boolean DEFAULT_CANONICAL = false;
        /**
         * If to use multiline format by default.
         */
        public static final boolean DEFAULT_MULTILINE_FORMAT = false;
        /**
         * Default encoding.
         */
        public static final Encoding DEFAULT_ENCODING = Encoding.UNICODE;
        /**
         * Default spaces per one indentation level.
         */
        public static final int DEFAULT_INDENTATION = 2;
        /**
         * Default spaces to use to indent indicators.
         */
        public static final int DEFAULT_INDICATOR_INDENTATION = 0;
        /**
         * Max line width by default.
         */
        public static final int DEFAULT_MAX_LINE_WIDTH = 0;
        /**
         * Max length for a key to have to be dumped in simple format.
         */
        public static final int DEFAULT_MAX_SIMPLE_KEY_LENGTH = 0;
        /**
         * If to escape unprintable unicode characters by default.
         */
        public static final boolean DEFAULT_ESCAPE_UNPRINTABLE = true;

        //Underlying SnakeYAML Engine settings builder
        private final DumpSettingsBuilder builder;
        //Anchor generator
        private Supplier<AnchorGenerator> anchorGeneratorSupplier = DEFAULT_ANCHOR_GENERATOR;
        //String style
        private ScalarStyle stringStyle = DEFAULT_STRING_STYLE;

        /**
         * Creates a new builder from the given, already created SnakeYAML Engine settings builder.
         * <p>
         * Please note that {@link #setAnchorGenerator(Supplier)} still has to be called (if you want to alter the
         * default), as they are not part of the Engine's settings.
         *
         * @param builder the underlying builder
         */
        private Builder(DumpSettingsBuilder builder) {
            this.builder = builder;
        }

        /**
         * Creates a new builder. Automatically applies the defaults, compatible with Spigot/BungeeCord API.
         */
        private Builder() {
            //Create
            builder = DumpSettings.builder();
            //Set defaults
            setFlowStyle(DEFAULT_FLOW_STYLE);
            setScalarStyle(DEFAULT_SCALAR_STYLE);
            setStringStyle(DEFAULT_STRING_STYLE);
            setStartMarker(DEFAULT_START_MARKER);
            setEndMarker(DEFAULT_END_MARKER);
            setRootTag(DEFAULT_ROOT_TAG);
            setCanonicalForm(DEFAULT_CANONICAL);
            setMultilineStyle(DEFAULT_MULTILINE_FORMAT);
            setEncoding(DEFAULT_ENCODING);
            setIndentation(DEFAULT_INDENTATION);
            setIndicatorIndentation(DEFAULT_INDICATOR_INDENTATION);
            setLineWidth(DEFAULT_MAX_LINE_WIDTH);
            setMaxSimpleKeyLength(DEFAULT_MAX_SIMPLE_KEY_LENGTH);
            setEscapeUnprintable(DEFAULT_ESCAPE_UNPRINTABLE);
        }

        /**
         * Sets a custom anchor generator supplier used to supply generators when dumping. Anchor generators are used to
         * generate anchor IDs for duplicate nodes.
         * <p>
         * Supplier ensures that a brand new, yet unused generator, is used on every file dump.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_ANCHOR_GENERATOR}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setAnchorGenerator(AnchorGenerator)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setAnchorGenerator(org.snakeyaml.engine.v2.serializer.AnchorGenerator)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#3222-anchors-and-aliases">anchors
         * and aliases</a>
         *
         * @param generator the new anchor generator supplier
         * @return the builder
         * @see DumpSettingsBuilder#setAnchorGenerator(AnchorGenerator)
         */
        public Builder setAnchorGenerator(@NotNull Supplier<AnchorGenerator> generator) {
            this.anchorGeneratorSupplier = generator;
            return this;
        }

        /**
         * Sets the flow style to use. Flow styles determine style of the dumped document.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_FLOW_STYLE}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setDefaultFlowStyle(FlowStyle)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setDefaultFlowStyle(org.snakeyaml.engine.v2.common.FlowStyle)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#3231-node-styles">node styles</a>
         *
         * @param flowStyle the flow style to use
         * @return the builder
         * @see DumpSettingsBuilder#setDefaultFlowStyle(FlowStyle)
         */
        public Builder setFlowStyle(@NotNull FlowStyle flowStyle) {
            builder.setDefaultFlowStyle(flowStyle);
            return this;
        }

        /**
         * Sets the scalar style to use.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_SCALAR_STYLE}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setDefaultScalarStyle(ScalarStyle)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setDefaultScalarStyle(org.snakeyaml.engine.v2.common.ScalarStyle)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#81-block-scalar-styles">for
         * <code>BLOCK</code> flow style</a>, <a href="https://yaml.org/spec/1.2.2/#73-flow-scalar-styles">for
         * <code>FLOW</code> flow style</a>
         *
         * @param scalarStyle the scalar style to use
         * @return the builder
         * @see DumpSettingsBuilder#setDefaultScalarStyle(ScalarStyle)
         */
        public Builder setScalarStyle(@NotNull ScalarStyle scalarStyle) {
            builder.setDefaultScalarStyle(scalarStyle);
            return this;
        }

        /**
         * Sets the string style to use. This is the same as {@link #setScalarStyle(ScalarStyle)}, but used exclusively
         * for {@link String} instances.
         * <p>
         * For additional information please refer to documentation of the relevant parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_STRING_STYLE}<br>
         * <b>Relevant parent method: </b> {@link DumpSettingsBuilder#setDefaultScalarStyle(ScalarStyle)}<br>
         * <b>Relevant parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setDefaultScalarStyle(org.snakeyaml.engine.v2.common.ScalarStyle)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#81-block-scalar-styles">for
         * <code>BLOCK</code> flow style</a>, <a href="https://yaml.org/spec/1.2.2/#73-flow-scalar-styles">for
         * <code>FLOW</code> flow style</a>
         *
         * @param stringStyle the scalar style to use
         * @return the builder
         * @see DumpSettingsBuilder#setDefaultScalarStyle(ScalarStyle)
         */
        public Builder setStringStyle(@NotNull ScalarStyle stringStyle) {
            this.stringStyle = stringStyle;
            return this;
        }

        /**
         * Sets if to forcefully add document start marker (<code>---</code>). If there are any directives to be dumped,
         * it is added automatically.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_START_MARKER}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setExplicitStart(boolean)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setExplicitStart(boolean)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#document-markers">document
         * markers</a>
         *
         * @param startMarker if to forcefully add document start marker
         * @return the builder
         * @see DumpSettingsBuilder#setExplicitStart(boolean)
         */
        public Builder setStartMarker(boolean startMarker) {
            builder.setExplicitStart(startMarker);
            return this;
        }

        /**
         * Sets if to forcefully add document end marker (<code>...</code>).
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_END_MARKER}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setExplicitEnd(boolean)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setExplicitEnd(boolean)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#document-markers">document
         * markers</a>
         *
         * @param endMarker if to forcefully add document end marker
         * @return the builder
         * @see DumpSettingsBuilder#setExplicitEnd(boolean)
         */
        public Builder setEndMarker(boolean endMarker) {
            builder.setExplicitEnd(endMarker);
            return this;
        }

        /**
         * Sets a custom scalar resolver, used to resolve tags for objects.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> defined by the parent method<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setScalarResolver(ScalarResolver)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setScalarResolver(org.snakeyaml.engine.v2.resolver.ScalarResolver)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#1021-tags">JSON schema tags</a>, <a
         * href="https://yaml.org/spec/1.2.2/#failsafe-schema">failsafe schema tags</a>
         *
         * @param resolver the resolver to set
         * @return the builder
         * @see DumpSettingsBuilder#setScalarResolver(ScalarResolver)
         */
        public Builder setScalarResolver(@NotNull ScalarResolver resolver) {
            builder.setScalarResolver(resolver);
            return this;
        }

        /**
         * Sets the (explicit) tag of the root document element (top-level element in the document).
         * <p>
         * As this library does not support anything other than {@link Map} (represented by section) as the top-level
         * object, the given tag must be referring to a class implementing {@link Map} interface, serious issues will
         * occur otherwise (the given tag is <b>not</b> validated).
         * <p>
         * If <code>null</code>, does not dump any tag for the root section (which will make the resolver resolve it
         * automatically when the document's loaded next time).
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_ROOT_TAG}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setExplicitRootTag(Optional)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setExplicitRootTag(java.util.Optional)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#1021-tags">JSON schema tags</a>, <a
         * href="https://yaml.org/spec/1.2.2/#failsafe-schema">failsafe schema tags</a>
         *
         * @param rootTag the root section tag (type)
         * @return the builder
         * @see DumpSettingsBuilder#setExplicitRootTag(Optional)
         */
        public Builder setRootTag(@Nullable Tag rootTag) {
            builder.setExplicitRootTag(Optional.ofNullable(rootTag));
            return this;
        }

        /**
         * Sets the version (<code>%YAML</code>) directive. If <code>null</code>, does not dump any explicit version
         * directive.
         * <p>
         * SnakeYAML Engine supports YAML v1.2 only, however, per the Engine specification, most of the older YAML can
         * be processed. Always refer to the <a
         * href="https://bitbucket.org/asomov/snakeyaml-engine/wiki/Documentation">Engine's documentation</a> for more
         * information. To avoid problems, update to 1.2 for full support, please.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> defined by the parent method<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setYamlDirective(Optional)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setYamlDirective(java.util.Optional)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#681-yaml-directives">YAML
         * directives</a>
         *
         * @param directive the version directive
         * @return the builder
         * @see DumpSettingsBuilder#setYamlDirective(Optional)
         */
        public Builder setYamlDirective(@Nullable SpecVersion directive) {
            builder.setYamlDirective(Optional.ofNullable(directive));
            return this;
        }

        /**
         * Sets the given tag (<code>%TAG</code>) directives in form of a map, where key is the <i>!handle!</i>
         * (including the exclamation marks) and value the <i>prefix</i> (per the YAML spec).
         * <p>
         * If there were any tag directives set previously, they are <b>all</b> overwritten.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> defined by the parent method<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setTagDirective(Map)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setTagDirective(java.util.Map)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#682-tag-directives">TAG
         * directives</a>
         *
         * @param directives the tag directives
         * @return the builder
         * @see DumpSettingsBuilder#setTagDirective(Map)
         */
        public Builder setTagDirectives(@NotNull Map<String, String> directives) {
            builder.setTagDirective(directives);
            return this;
        }

        /**
         * Sets if to dump in canonical form.
         * <p>
         * Though there is no information and/or specification regarding "canonical form", if enabled (according to
         * experiment shown at the wiki), the dumped file looks as if:
         * <ul>
         *     <li>{@link #setFlowStyle(FlowStyle)} is set to {@link FlowStyle#FLOW},</li>
         *     <li>{@link #setScalarStyle(ScalarStyle)} is set to {@link ScalarStyle#DOUBLE_QUOTED},</li>
         *     <li>{@link #setMultilineStyle(boolean)} is enabled,</li>
         *     <li>{@link #setMaxSimpleKeyLength(int)} is set to <code>1</code>,</li>
         *     <li>{@link #setStartMarker(boolean)} is enabled.</li>
         * </ul>
         * Enabling this option might overwrite those settings as well, detailed behaviour is not documented.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_CANONICAL}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setCanonical(boolean)}<br>
         * <b>Parent method docs (v2.3): </b><a href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setCanonical(boolean)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#canonical-form">canonical form</a>
         *
         * @param canonical if to use canonical form
         * @return the builder
         * @see DumpSettingsBuilder#setCanonical(boolean)
         */
        public Builder setCanonicalForm(boolean canonical) {
            builder.setCanonical(canonical);
            return this;
        }

        /**
         * Sets if to separate content of the document using newlines to make the dumped file somewhat readable; has
         * effect if and only if the flow style is set to {@link FlowStyle#FLOW}.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_MULTILINE_FORMAT}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setMultiLineFlow(boolean)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setMultiLineFlow(boolean)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b>-
         *
         * @param multilineStyle if to use multiline format
         * @return the builder
         * @see DumpSettingsBuilder#setMultiLineFlow(boolean)
         */
        public Builder setMultilineStyle(boolean multilineStyle) {
            builder.setMultiLineFlow(multilineStyle);
            return this;
        }

        /**
         * Sets the encoding to use.
         * <p>
         * For additional information regarding this option and charsets, please refer to documentation of the parent
         * method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_ENCODING}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setUseUnicodeEncoding(boolean)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setUseUnicodeEncoding(boolean)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#51-character-set">character
         * sets</a>
         *
         * @param encoding the encoding to use
         * @return the builder
         * @see DumpSettingsBuilder#setUseUnicodeEncoding(boolean)
         */
        public Builder setEncoding(@NotNull Encoding encoding) {
            builder.setUseUnicodeEncoding(encoding.isUnicode());
            return this;
        }

        /**
         * Sets how many spaces to use per one indent = one level in YAML indentation hierarchy.
         * <p>
         * For additional information please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_INDENTATION}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setIndent(int)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setIndent(int)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a
         * href="https://yaml.org/spec/1.2.2/#61-indentation-spaces">indentation</a>
         *
         * @param spaces amount of spaces per one indentation level
         * @return the builder
         * @see DumpSettingsBuilder#setUseUnicodeEncoding(boolean)
         * @see <a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setIndent(int)">docs
         * for the wrapped method</a>
         */
        public Builder setIndentation(int spaces) {
            builder.setIndent(spaces);
            return this;
        }

        /**
         * Sets how many spaces to use per one indentation level for indicators. If the given value is less than or
         * equal to <code>0</code>, disables indicator indentation.
         * <p>
         * For additional information regarding this option and indicators, please refer to documentation of the parent
         * method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_INDICATOR_INDENTATION}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setIndent(int)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setIndicatorIndent(int)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a
         * href="https://yaml.org/spec/1.2.2/#61-indentation-spaces">indentation</a>,
         * <a href="https://yaml.org/spec/1.2.2/#indicator-characters">indicators</a>
         *
         * @param spaces amount of spaces to use to indent indicators
         * @return the builder
         * @see DumpSettingsBuilder#setIndicatorIndent(int)
         * @see <a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setIndicatorIndent(int)">docs
         * for the wrapped method</a>
         */
        public Builder setIndicatorIndentation(int spaces) {
            builder.setIndentWithIndicator(spaces > 0);
            builder.setIndicatorIndent(Math.max(spaces, 0));
            return this;
        }

        /**
         * Sets the preferred line width. If any scalar makes the line longer than the given width, the dumper attempts
         * to break the line at the nearest (next) applicable whitespace, if any.
         * <p>
         * If the given value is less than or equal to <code>0</code>, disables the limit and therefore, allows for
         * theoretically unlimited line lengths (up to {@link Integer#MAX_VALUE}).
         * <p>
         * For additional information, please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_MAX_LINE_WIDTH}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setWidth(int)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setWidth(int)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b>-
         *
         * @param width preferred line width
         * @return the builder
         * @see DumpSettingsBuilder#setWidth(int)
         * @see <a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setWidth(int)">docs
         * for the wrapped method</a>
         */
        public Builder setLineWidth(int width) {
            builder.setWidth(width <= 0 ? Integer.MAX_VALUE : width);
            return this;
        }

        /**
         * Sets the line break appended at the end of each line.
         * <p>
         * For additional information, please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> defined by the parent method<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setBestLineBreak(String)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setBestLineBreak(java.lang.String)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b>-
         *
         * @param lineBreak line break
         * @return the builder
         * @see DumpSettingsBuilder#setBestLineBreak(String)
         */
        public Builder setLineBreak(@NotNull String lineBreak) {
            builder.setBestLineBreak(lineBreak);
            return this;
        }

        /**
         * Sets the maximum length a key can (in serialized form, also applies to flow sequence and map keys) have to be
         * printed in simple format (without the explicit key indicator <code>?</code>).
         * <p>
         * If the given value is less than or equal to <code>0</code>, disables the limit and therefore, allows for keys
         * of length up to <code>1024</code> (limit enforced by the YAML spec). If any value greater than
         * <code>1018</code> is given, an {@link IllegalArgumentException} will be thrown (not a typo - the limit here
         * is lower as there is some "processing").
         * <p>
         * For additional information, please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_MAX_SIMPLE_KEY_LENGTH}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setMaxSimpleKeyLength(int)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setMaxSimpleKeyLength(int)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a
         * href="https://yaml.org/spec/1.2.2/#example-explicit-block-mapping-entries">explicit keys</a>
         *
         * @param length maximum length for simple key format
         * @return the builder
         * @see DumpSettingsBuilder#setMaxSimpleKeyLength(int)
         */
        public Builder setMaxSimpleKeyLength(int length) {
            // Check the limit
            if (length > 1018)
                throw new IllegalArgumentException("Maximum simple key length is limited to 1018!");

            // Set
            builder.setMaxSimpleKeyLength(length <= 0 ? 1024 : length + 6);
            return this;
        }

        /**
         * Sets if strings containing unprintable characters should have those characters escaped, or the whole string
         * dumped as binary data.
         * <p>
         * For additional information, please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> {@link #DEFAULT_ESCAPE_UNPRINTABLE}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setNonPrintableStyle(NonPrintableStyle)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setNonPrintableStyle(org.snakeyaml.engine.v2.common.NonPrintableStyle)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#51-character-set">character
         * sets</a>
         *
         * @param escape if to escape rather than dumping as binary
         * @return the builder
         * @see DumpSettingsBuilder#setNonPrintableStyle(NonPrintableStyle)
         */
        public Builder setEscapeUnprintable(boolean escape) {
            return setUnprintableStyle(escape ? NonPrintableStyle.ESCAPE : NonPrintableStyle.BINARY);
        }

        /**
         * Sets if strings containing unprintable characters should have those characters escaped, or the whole string
         * dumped as binary data.
         * <p>
         * For additional information, please refer to documentation of the parent method listed below.
         * <p>
         * <b>Default: </b> corresponding to {@link #DEFAULT_ESCAPE_UNPRINTABLE}<br>
         * <b>Alias method: </b>{@link #setEscapeUnprintable(boolean)}<br>
         * <b>Parent method: </b> {@link DumpSettingsBuilder#setNonPrintableStyle(NonPrintableStyle)}<br>
         * <b>Parent method docs (v2.3): </b><a
         * href="https://javadoc.io/static/org.snakeyaml/snakeyaml-engine/2.3/org/snakeyaml/engine/v2/api/DumpSettingsBuilder.html#setNonPrintableStyle(org.snakeyaml.engine.v2.common.NonPrintableStyle)">click</a><br>
         * <b>Related YAML spec (v1.2.2): </b><a href="https://yaml.org/spec/1.2.2/#51-character-set">character
         * sets</a>
         *
         * @param style style to use
         * @return the builder
         * @see DumpSettingsBuilder#setNonPrintableStyle(NonPrintableStyle)
         */
        public Builder setUnprintableStyle(@NotNull NonPrintableStyle style) {
            builder.setNonPrintableStyle(style);
            return this;
        }

        /**
         * Builds the settings.
         *
         * @return the settings
         */
        public DumperSettings build() {
            return new DumperSettings(this);
        }

    }

}