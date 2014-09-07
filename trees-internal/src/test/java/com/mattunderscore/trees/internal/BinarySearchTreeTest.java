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

package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.SortingTreeBuilder;
import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.common.TreesImpl;
import com.mattunderscore.trees.utilities.ComparableComparator;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author matt on 06/09/14.
 */
public final class BinarySearchTreeTest {
    private final Trees trees = new TreesImpl();

    @Test
    public void test() {
        final Comparator<String> comparator = new ComparableComparator<>();
        final BinarySearchTree<String> tree = new BinarySearchTree<>(comparator);

        assertTrue(tree.isEmpty());
        tree.addElement("b");
        assertFalse(tree.isEmpty());
        tree.addElement("a");
        tree.addElement("c");

        assertEquals("b", tree.getRoot().getElement());
        assertEquals("a", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());
    }

    @Test
    public void complete() {
        final SortingTreeBuilder<String> builder = trees.sortingTreeBuilder(new ComparableComparator<String>());
        final BinarySearchTree<String> tree = builder
            .addElement("b")
            .addElement("a")
            .addElement("c")
            .build(BinarySearchTree.class);

        assertEquals("b", tree.getRoot().getElement());
        assertEquals("a", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());
    }
}
