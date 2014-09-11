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
import com.mattunderscore.trees.common.traversers.TreeIteratorFactoryImpl;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;

import java.util.Comparator;

/**
 * Implementation of {@link com.mattunderscore.trees.Trees}.
 * @author Matt Champion on 16/08/14.
 */
public final class TreesImpl implements Trees {
    public final SPISupport helper = new SPISupport();

    @Override
    public <E> TopDownTreeRootBuilder<E> topDownBuilder() {
        return new TopDownTreeRootBuilderImpl<E>(helper);
    }

    @Override
    public <E> BottomUpTreeBuilder<E> bottomUpBuilder() {
        return new BottomUpTreeBuilderImpl<E>(helper);
    }

    @Override
    public <E> SortingTreeBuilder<E> sortingTreeBuilder(Comparator<E> comparator) {
        return new SortingTreeBuilderImpl<>(helper, comparator);
    }

    @Override
    public <E> SortedTreeBuilder<E> sortedTreeBuilder(Comparator<E> comparator, SortingAlgorithm algorithm) {
        throw new UnsupportedOperationException("Sorting algorithms not yet implemented");
    }

    @Override
    public TreeSelectorFactory treeSelectorFactory() {
        return new TreeSelectorFactoryImpl(helper);
    }

    @Override
    public NodeSelectorFactory nodeSelectorFactory() {
        return new NodeSelectorFactoryImpl(helper);
    }

    @Override
    public TreeWalkerFactory treeWalkers() {
        return new TreeWalkerFactoryImpl();
    }

    @Override
    public TreeIteratorFactory treeIterators() {
        return new TreeIteratorFactoryImpl();
    }
}
