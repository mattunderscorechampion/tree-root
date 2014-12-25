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

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;
import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class TreeWalkerFactoryImplTest {
    private static TreeWalkerFactory walkerFactory;
    private static LinkedTree<String> tree;

    @BeforeClass
    public static void setUp() {
        final Trees trees = new TreesImpl();
        walkerFactory = trees.treeWalkers();
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("root", builder.create("a", builder.create("c")), builder.create("b"))
            .build(LinkedTree.<String>typeKey());
    }

    @Test
    public void testWalkPreOrder() {
        final TestWalker<LinkedTree<String>> walker = new TestWalker<>();
        walkerFactory.walkPreOrder(tree, walker);
        assertEquals(4, walker.count);
    }

    @Test
    public void testWalkInOrder() {
        final TestWalker<LinkedTree<String>> walker = new TestWalker<>();
        walkerFactory.walkInOrder(tree, walker);
        assertEquals(4, walker.count);
    }

    @Test
    public void testWalkPostOrder() {
        final TestWalker<LinkedTree<String>> walker = new TestWalker<>();
        walkerFactory.walkPostOrder(tree, walker);
        assertEquals(4, walker.count);
    }

    @Test
    public void testWalkBreadthFirst() {
        final TestWalker<LinkedTree<String>> walker = new TestWalker<>();
        walkerFactory.walkBreadthFirst(tree, walker);
        assertEquals(4, walker.count);
    }

    @Test
    public void testWalkElementsPreOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsPreOrder(tree, walker);
        assertEquals(4, walker.count);
    }

    @Test
    public void testWalkElementsInOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsInOrder(tree, walker);
        assertEquals(4, walker.count);
    }

    @Test
    public void testWalkElementsPostOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsPostOrder(tree, walker);
        assertEquals(4, walker.count);
    }

    @Test
    public void testWalkElementsBreadthFirst() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsBreadthFirst(tree, walker);
        assertEquals(4, walker.count);
    }

    public static final class TestWalker<N> implements Walker<N> {
        private int count = 0;

        @Override
        public void onEmpty() {

        }

        @Override
        public boolean onNext(N node) {
            count++;
            return true;
        }

        @Override
        public void onCompleted() {
        }
    }
}