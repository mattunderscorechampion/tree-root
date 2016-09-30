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

package com.mattunderscore.trees.impl;

import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.impl.suppliers.impl.EmptySortedTreeConstructorSupplierImpl;
import com.mattunderscore.trees.impl.suppliers.impl.EmptyTreeConstructorSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.KeyMappingSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.TreeConstructorSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.TreeConverterSupplier;
import com.mattunderscore.trees.sorted.SortedTreeBuilder;
import com.mattunderscore.trees.sorted.SortingAlgorithm;
import com.mattunderscore.trees.sorted.SortingTreeBuilder;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.ComparableComparator;

import java.util.Comparator;

/**
 * Implementation of {@link com.mattunderscore.trees.construction.TreeBuilderFactory}.
 * @author Matt Champion on 11/09/14.
 */
public final class TreeBuilderFactoryImpl implements TreeBuilderFactory {
    private final TopDownTreeRootBuilder topDownTreeRootBuilder;
    private final BottomUpTreeBuilder bottomUpTreeBuilder;
    private final EmptySortedTreeConstructorSupplierImpl emptySortedTreeConstructorSupplier;
    private final TreeConverterSupplier treeConverterSupplier;

    public TreeBuilderFactoryImpl(KeyMappingSupplier keyMappingSupplier, TreeConstructorSupplier treeConstructorSupplier, EmptyTreeConstructorSupplier emptyTreeConstructorSupplier, TreeConverterSupplier treeConverterSupplier, EmptySortedTreeConstructorSupplierImpl emptySortedTreeConstructorSupplier) {
        this.emptySortedTreeConstructorSupplier = emptySortedTreeConstructorSupplier;
        this.treeConverterSupplier = treeConverterSupplier;
        topDownTreeRootBuilder = new TopDownTreeRootBuilderImpl(this.treeConverterSupplier, emptyTreeConstructorSupplier);
        bottomUpTreeBuilder = new BottomUpTreeBuilderImpl(treeConstructorSupplier, emptyTreeConstructorSupplier, keyMappingSupplier);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E, N extends OpenNode<E, N>> TopDownTreeRootBuilder<E, N> topDownBuilder() {
        return topDownTreeRootBuilder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E, N extends OpenNode<E, N>> BottomUpTreeBuilder<E, N> bottomUpBuilder() {
        return bottomUpTreeBuilder;
    }

    @Override
    public <E, N extends OpenNode<E, N>> SortingTreeBuilder<E, N> sortingTreeBuilder(Comparator<E> comparator) {
        return new SortingTreeBuilderImpl<>(emptySortedTreeConstructorSupplier, comparator);
    }

    @Override
    public <E extends Comparable<E>, N extends OpenNode<E, N>> SortingTreeBuilder<E, N> sortingTreeBuilder() {
        return new SortingTreeBuilderImpl<>(emptySortedTreeConstructorSupplier, ComparableComparator.get());
    }

    @Override
    public <E, N extends OpenNode<E, N>> SortedTreeBuilder<E, N> sortedTreeBuilder(Comparator<E> comparator, SortingAlgorithm algorithm) {
        throw new UnsupportedOperationException("Sorting algorithms not yet implemented");
    }

    @Override
    public <E extends Comparable<E>, N extends OpenNode<E, N>> SortedTreeBuilder<E, N> sortedTreeBuilder(SortingAlgorithm algorithm) {
        return sortedTreeBuilder(ComparableComparator.get(), algorithm);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> T copy(T tree) {
        return treeConverterSupplier.get((Class<T>)tree.getClass()).build(tree);
    }

    @Override
    public <E, N extends OpenNode<E, N>, N2 extends OpenNode<E, N2>, T2 extends Tree<E, N2>> T2 copy(Tree<E, N> tree, Class<T2> klass) {
        return treeConverterSupplier.get(klass).build(tree);
    }
}
