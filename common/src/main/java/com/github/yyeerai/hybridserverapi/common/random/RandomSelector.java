/*
 * This file is part of helper, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.github.yyeerai.hybridserverapi.common.random;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * 从集合中随机选择元素的工具类
 *
 * @param <E> 元素类型
 */
public interface RandomSelector<E> {

    /**
     * 创建一个随机选择元素的统一选择器
     *
     * @param elements 可供选择的元素集合
     * @param <E>      元素类型
     * @return 选择器实例
     */
    static <E> RandomSelector<E> uniform(Collection<E> elements) {
        return RandomSelectorImpl.uniform(elements);
    }

    /**
     * 创建一个加权选择器，根据 {@link Weighted#getWeight()} 的值来选择元素。
     *
     * @param elements 可供选择的元素集合
     * @param <E>      元素类型
     * @return 选择器实例
     */
    static <E extends Weighted> RandomSelector<E> weighted(Collection<E> elements) {
        return weighted(elements, Weighted.WEIGHER);
    }

    /**
     * 创建一个加权选择器，根据权重函数使用元素的权重来选择元素
     *
     * @param elements 可供选择的元素集合
     * @param <E>      元素类型
     * @return 选择器实例
     */
    static <E> RandomSelector<E> weighted(Collection<E> elements, Weigher<? super E> weigher) {
        return RandomSelectorImpl.weighted(elements, weigher);
    }

    /**
     * 随机选择一个元素
     *
     * @param random 用于选择的随机实例
     * @return 一个元素
     */
    E pick(Random random);

    /**
     * 随机选择一个元素
     *
     * @return 一个元素
     */
    default E pick() {
        return pick(ThreadLocalRandom.current());
    }

    /**
     * 从此选择器返回有效无限的随机元素流
     *
     * @param random 用于选择的随机实例
     * @return 元素流
     */
    Stream<E> stream(Random random);

    /**
     * 从此选择器返回有效无限的随机元素流
     *
     * @return 元素流
     */
    default Stream<E> stream() {
        return stream(ThreadLocalRandom.current());
    }
}