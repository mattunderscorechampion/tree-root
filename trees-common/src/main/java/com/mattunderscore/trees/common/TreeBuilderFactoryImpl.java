/* Copyright © 2014 Matthew Champion
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

package com.mattunderscore.trees.common;

import com.mattunderscore.trees.*;

import java.util.Comparator;

/**
 * @author Matt Champion on 11/09/14.
 */
public class TreeBuilderFactoryImpl implements TreeBuilderFactory {
    private final SPISupport support;
    private final TopDownTreeRootBuilder topDownTreeRootBuilder;
    private final BottomUpTreeBuilder bottomUpTreeBuilder;

    public TreeBuilderFactoryImpl(SPISupport support) {
        this.support = support;
        topDownTreeRootBuilder = new TopDownTreeRootBuilderImpl(support);
        bottomUpTreeBuilder = new BottomUpTreeBuilderImpl(support);
    }

    @Override
    public <E> TopDownTreeRootBuilder<E> topDownBuilder() {
        return topDownTreeRootBuilder;
    }

    @Override
    public <E> BottomUpTreeBuilder<E> bottomUpBuilder() {
        return bottomUpTreeBuilder;
    }

    @Override
    public <E> SortingTreeBuilder<E> sortingTreeBuilder(Comparator<E> comparator) {
        return new SortingTreeBuilderImpl<>(support, comparator);
    }

    @Override
    public <E> SortedTreeBuilder<E> sortedTreeBuilder(Comparator<E> comparator, SortingAlgorithm algorithm) {
        throw new UnsupportedOperationException("Sorting algorithms not yet implemented");
    }
}
