/* Copyright © 2015 Matthew Champion
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of mattunderscore.com nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL MATTHEW CHAMPION BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.mattunderscore.trees.matchers;

import java.util.Comparator;
import java.util.function.Predicate;

import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.utilities.ComparableComparator;

/**
 * Matches nodes with an element greater than some value.
 * @author Matt Champion on 14/09/2015
 */
public final class GreaterThanMatcher<E> implements Predicate<OpenNode<? extends E, ?>> {
    private final Comparator<E> comparator;
    private final E value;

    private GreaterThanMatcher(Comparator<E> comparator, E value) {
        this.comparator = comparator;
        this.value = value;
    }

    @Override
    public boolean test(OpenNode<? extends E, ?> openNode) {
        return comparator.compare(value, openNode.getElement()) < 0;
    }

    /**
     * Create matcher using comparator.
     * @param comparator The comparator
     * @param value The value
     * @param <E> The value to compare against
     * @return The comparator
     */
    public static <E> Predicate<OpenNode<? extends E, ?>> create(Comparator<E> comparator, E value) {
        return new GreaterThanMatcher<>(comparator, value);
    }

    /**
     * Create matcher using comparable.
     * @param value The value
     * @param <E> The value to compare against
     * @return The comparator
     */
    public static <E extends Comparable<E>> Predicate<OpenNode<? extends E, ?>> create(E value) {
        return create(ComparableComparator.get(), value);
    }
}
