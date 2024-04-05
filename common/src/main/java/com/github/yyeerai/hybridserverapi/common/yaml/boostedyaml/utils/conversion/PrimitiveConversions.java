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
package com.github.yyeerai.hybridserverapi.common.yaml.boostedyaml.utils.conversion;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.*;

/**
 * Utility class for primitives.
 */
public class PrimitiveConversions {

    /**
     * All numeric primitives and their corresponding non-primitive representations.
     */
    public static final Map<Class<?>, Class<?>> NUMERIC_PRIMITIVES = Collections.unmodifiableMap(new HashMap<Class<?>, Class<?>>() {{
        put(int.class, Integer.class);
        put(byte.class, Byte.class);
        put(short.class, Short.class);
        put(long.class, Long.class);
        put(float.class, Float.class);
        put(double.class, Double.class);
    }});

    /**
     * All primitives mapped to their object representations.
     */
    public static final Map<Class<?>, Class<?>> PRIMITIVES_TO_OBJECTS = Collections.unmodifiableMap(new HashMap<Class<?>, Class<?>>() {{
        putAll(NUMERIC_PRIMITIVES);
        put(boolean.class, Boolean.class);
        put(char.class, Character.class);
    }});

    /**
     * All non-numeric primitives and their corresponding non-primitive representations; vice-versa.
     */
    public static final Map<Class<?>, Class<?>> NON_NUMERIC_CONVERSIONS = Collections.unmodifiableMap(new HashMap<Class<?>, Class<?>>() {{
        put(boolean.class, Boolean.class);
        put(char.class, Character.class);
        put(Boolean.class, boolean.class);
        put(Character.class, char.class);
    }});

    /**
     * All numeric data type classes.
     */
    public static final Set<Class<?>> NUMERIC_CLASSES = Collections.unmodifiableSet(new HashSet<Class<?>>() {{
        add(int.class);
        add(byte.class);
        add(short.class);
        add(long.class);
        add(float.class);
        add(double.class);
        add(Integer.class);
        add(Byte.class);
        add(Short.class);
        add(Long.class);
        add(Float.class);
        add(Double.class);
    }});

    /**
     * Returns if the given class represents a numeric data type.
     *
     * @param clazz the class to check
     * @return if it's a numeric class
     */
    public static boolean isNumber(@NotNull Class<?> clazz) {
        return NUMERIC_CLASSES.contains(clazz);
    }

    /**
     * Converts a number to the target type. It must be guaranteed that the given object is an instance of {@link
     * Number} and {@link #NUMERIC_CLASSES} must contain the target class.
     * <p>
     * <b>This method supports</b> casting between two numeric primitives, two non-primitive numeric
     * representations and one of each kind. Casting between any primitive type, and it's non-primitive representation
     * is also supported.
     *
     * @param value  the value to convert
     * @param target the target type
     * @return the converted number
     */
    public static Object convertNumber(@NotNull Object value, @NotNull Class<?> target) {
        // Convert to number
        Number number = (Number) value;
        // Primitive
        boolean primitive = target.isPrimitive();
        // If primitive
        if (primitive)
            target = NUMERIC_PRIMITIVES.get(target);

        // Convert
        if (target == Integer.class)
            return number.intValue();
        else if (target == Byte.class)
            return number.byteValue();
        else if (target == Short.class)
            return number.shortValue();
        else if (target == Long.class)
            return number.longValue();
        else if (target == Float.class)
            return number.floatValue();
        else
            return number.doubleValue();
    }

    /**
     * Converts the given value to integer.
     * <p>
     * If the given value is <code>null</code>, not an instance of {@link Number} or could not be converted by parsing
     * from string via {@link Integer#valueOf(String)}, returns an empty optional.
     *
     * @param value the number
     * @return the integer
     */
    public static Optional<Integer> toInt(@Nullable Object value) {
        //If null
        if (value == null)
            return Optional.empty();
        //If a number
        if (value instanceof Number)
            return Optional.of(((Number) value).intValue());

        //Try to parse
        try {
            return Optional.of(Integer.valueOf(value.toString()));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    /**
     * Converts the given value to byte.
     * <p>
     * If the given value is <code>null</code>, not an instance of {@link Number} or could not be converted by parsing
     * from string via {@link Byte#valueOf(String)}, returns an empty optional.
     *
     * @param value the number
     * @return the byte
     */
    public static Optional<Byte> toByte(@Nullable Object value) {
        //If null
        if (value == null)
            return Optional.empty();
        //If a number
        if (value instanceof Number)
            return Optional.of(((Number) value).byteValue());

        //Try to parse
        try {
            return Optional.of(Byte.valueOf(value.toString()));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    /**
     * Converts the given value to long.
     * <p>
     * If the given value is <code>null</code>, not an instance of {@link Number} or could not be converted by parsing
     * from string via {@link Long#valueOf(String)}, returns an empty optional.
     *
     * @param value the number
     * @return the long
     */
    public static Optional<Long> toLong(@Nullable Object value) {
        //If null
        if (value == null)
            return Optional.empty();
        //If a number
        if (value instanceof Number)
            return Optional.of(((Number) value).longValue());

        //Try to parse
        try {
            return Optional.of(Long.valueOf(value.toString()));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    /**
     * Converts the given value to double.
     * <p>
     * If the given value is <code>null</code>, not an instance of {@link Number} or could not be converted by parsing
     * from string via {@link Double#valueOf(String)}, returns an empty optional.
     *
     * @param value the number
     * @return the double
     */
    public static Optional<Double> toDouble(@Nullable Object value) {
        //If null
        if (value == null)
            return Optional.empty();
        //If a number
        if (value instanceof Number)
            return Optional.of(((Number) value).doubleValue());

        //Try to parse
        try {
            return Optional.of(Double.valueOf(value.toString()));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    /**
     * Converts the given value to float.
     * <p>
     * If the given value is <code>null</code>, not an instance of {@link Number} or could not be converted by parsing
     * from string via {@link Float#valueOf(String)}, returns an empty optional.
     *
     * @param value the number
     * @return the float
     */
    public static Optional<Float> toFloat(@Nullable Object value) {
        //If null
        if (value == null)
            return Optional.empty();
        //If a number
        if (value instanceof Number)
            return Optional.of(((Number) value).floatValue());

        //Try to parse
        try {
            return Optional.of(Float.valueOf(value.toString()));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    /**
     * Converts the given value to short.
     * <p>
     * If the given value is <code>null</code>, not an instance of {@link Number} or could not be converted by parsing
     * from string via {@link Short#valueOf(String)}, returns an empty optional.
     *
     * @param value the number
     * @return the short
     */
    public static Optional<Short> toShort(@Nullable Object value) {
        //If null
        if (value == null)
            return Optional.empty();
        //If a number
        if (value instanceof Number)
            return Optional.of(((Number) value).shortValue());

        //Try to parse
        try {
            return Optional.of(Short.valueOf(value.toString()));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    /**
     * Converts the given value to big integer.
     * <p>
     * If the given value is <code>null</code>, not an instance of {@link BigInteger}, {@link Number} or could not be
     * converted by parsing from string via {@link BigInteger#BigInteger(String)}, returns an empty optional.
     *
     * @param value the number
     * @return the big integer
     */
    public static Optional<BigInteger> toBigInt(@Nullable Object value) {
        //If null
        if (value == null)
            return Optional.empty();
        //If a big integer
        if (value instanceof BigInteger)
            return Optional.of((BigInteger) value);
        //If a number
        if (value instanceof Number)
            return Optional.of(BigInteger.valueOf(((Number) value).longValue()));

        //Try to parse
        try {
            return Optional.of(new BigInteger(value.toString()));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

}