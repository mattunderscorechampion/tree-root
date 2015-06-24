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

import static org.junit.Assert.assertTrue;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.mutable.ClosedMutableSettableStructuredNode;
import com.mattunderscore.trees.sorted.SortedTreeBuilder;
import com.mattunderscore.trees.sorted.SortingAlgorithm;
import com.mattunderscore.trees.sorted.SortingTree;
import com.mattunderscore.trees.sorted.SortingTreeBuilder;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.utilities.ComparableComparator;
import org.junit.Test;

public class TreeBuilderFactoryImplTest {
    private final static Trees trees = new TreesImpl();

    @Test
    public void testTopDownBuilder() {
        final BottomUpTreeBuilder<String, ClosedMutableSettableStructuredNode<String>> builder = trees.treeBuilders().bottomUpBuilder();
        final LinkedTree<String> tree = builder.build(LinkedTree.<String>typeKey());
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testBottomUpBuilder() {
        final TopDownTreeRootBuilder<String, ClosedMutableSettableStructuredNode<String>> builder = trees.treeBuilders().topDownBuilder();
        final LinkedTree<String> tree = builder.build(LinkedTree.<String>typeKey());
        assertTrue(tree.isEmpty());
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void testSortingTreeBuilder() {
        final SortingTreeBuilder<String, ClosedMutableSettableStructuredNode<String>> builder = trees.treeBuilders().sortingTreeBuilder();
        builder.build(new TypeKey<SortingTree<String, ClosedMutableSettableStructuredNode<String>>>() {});
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void testSortingTreeBuilderWithComparator() {
        final SortingTreeBuilder<String, ClosedMutableSettableStructuredNode<String>> builder = trees.treeBuilders()
            .sortingTreeBuilder(new ComparableComparator<String>());
        builder.build(new TypeKey<SortingTree<String, ClosedMutableSettableStructuredNode<String>>>() {});
    }

    @Test(expected = UnsupportedOperationException.class) // Sorting algorithms not implemented
    public void testSortedTreeBuilder() {
        final SortedTreeBuilder<String, ClosedMutableSettableStructuredNode<String>> builder = trees.treeBuilders()
            .sortedTreeBuilder(new ComparableComparator<String>(), new SortingAlgorithm() {
        });
    }
}