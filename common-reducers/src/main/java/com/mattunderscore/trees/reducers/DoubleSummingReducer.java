/* Copyright © 2016 Matthew Champion
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

package com.mattunderscore.trees.reducers;

import com.mattunderscore.trees.tree.Node;

import java.util.Collection;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.summingDouble;

/**
 * Implementation of {@link BiFunction} that reduces a tree of {@link Double}s by summing.
 *
 * @author Matt Champion on 02/05/16
 */
public final class DoubleSummingReducer implements BiFunction<Node<Double>, Collection<Double>, Double> {
    private static final DoubleSummingReducer INSTANCE = new DoubleSummingReducer();

    private DoubleSummingReducer() {
    }

    @Override
    public Double apply(Node<Double> node, Collection<Double> childResults) {
        final double childSum = childResults
                .stream()
                .filter(result -> result != null)
                .collect(summingDouble(result -> result));

        final Double element = node.getElement();
        if (element == null) {
            return childSum;
        }
        else {
            return childSum + element;
        }
    }

    /**
     * @return A {@link IntegerSummingReducer}
     */
    public static DoubleSummingReducer get() {
        return INSTANCE;
    }
}
