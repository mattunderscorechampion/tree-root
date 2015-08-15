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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.traversal.TreeWalker;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;
import com.mattunderscore.trees.traversal.Walker;

public final class TreeWalkerFactoryImplTest {
    private static TreeWalkerFactory walkerFactory;
    private static LinkedTree<String> tree;

    @BeforeClass
    public static void setUp() {
        final Trees trees = Trees.get();
        walkerFactory = trees.treeWalkers();
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("root",
            builder.create("a",
                builder.create("c")),
            builder.create("b"))
            .build(LinkedTree.<String>typeKey());
    }

    @Test
    public void testWalkPreOrder() {
        final TestWalker<MutableSettableStructuredNode<String>> walker = new TestWalker<>();
        walkerFactory.walkPreOrder(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("root", walker.elements.get(0).getElement());
        assertEquals("a", walker.elements.get(1).getElement());
        assertEquals("c", walker.elements.get(2).getElement());
        assertEquals("b", walker.elements.get(3).getElement());
    }

    @Test
    public void testWalkInOrder() {
        final TestWalker<MutableSettableStructuredNode<String>> walker = new TestWalker<>();
        walkerFactory.walkInOrder(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("c", walker.elements.get(0).getElement());
        assertEquals("a", walker.elements.get(1).getElement());
        assertEquals("root", walker.elements.get(2).getElement());
        assertEquals("b", walker.elements.get(3).getElement());
    }

    @Test
    public void testWalkPostOrder() {
        final TestWalker<MutableSettableStructuredNode<String>> walker = new TestWalker<>();
        walkerFactory.walkPostOrder(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("c", walker.elements.get(0).getElement());
        assertEquals("a", walker.elements.get(1).getElement());
        assertEquals("b", walker.elements.get(2).getElement());
        assertEquals("root", walker.elements.get(3).getElement());
    }

    @Test
    public void testWalkBreadthFirst() {
        final TestWalker<MutableSettableStructuredNode<String>> walker = new TestWalker<>();
        walkerFactory.walkBreadthFirst(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("root", walker.elements.get(0).getElement());
        assertEquals("a", walker.elements.get(1).getElement());
        assertEquals("b", walker.elements.get(2).getElement());
        assertEquals("c", walker.elements.get(3).getElement());
    }

    @Test
    public void testWalkElementsPreOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsPreOrder(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("root", walker.elements.get(0));
        assertEquals("a", walker.elements.get(1));
        assertEquals("c", walker.elements.get(2));
        assertEquals("b", walker.elements.get(3));
    }

    @Test
    public void testWalkElementsInOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsInOrder(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("c", walker.elements.get(0));
        assertEquals("a", walker.elements.get(1));
        assertEquals("root", walker.elements.get(2));
        assertEquals("b", walker.elements.get(3));
    }

    @Test
    public void testWalkElementsPostOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsPostOrder(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("c", walker.elements.get(0));
        assertEquals("a", walker.elements.get(1));
        assertEquals("b", walker.elements.get(2));
        assertEquals("root", walker.elements.get(3));
    }

    @Test
    public void testWalkElementsBreadthFirst() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsBreadthFirst(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("root", walker.elements.get(0));
        assertEquals("a", walker.elements.get(1));
        assertEquals("b", walker.elements.get(2));
        assertEquals("c", walker.elements.get(3));
    }

    @Test
    public void testWalkTreePreOrder() {
        final TestTreeWalker<MutableSettableStructuredNode<String>> walker = new TestTreeWalker<>();
        walkerFactory.walkPreOrder(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("root", walker.elements.get(0).getElement());
        assertEquals("a", walker.elements.get(1).getElement());
        assertEquals("c", walker.elements.get(2).getElement());
        assertEquals("b", walker.elements.get(3).getElement());
    }

    @Test
    public void testWalkTreeElementsPreOrder() {
        final TestTreeWalker<String> walker = new TestTreeWalker<>();
        walkerFactory.walkElementsPreOrder(tree, walker);
        assertEquals(4, walker.elements.size());
        assertEquals("root", walker.elements.get(0));
        assertEquals("a", walker.elements.get(1));
        assertEquals("c", walker.elements.get(2));
        assertEquals("b", walker.elements.get(3));
    }

    public static final class TestWalker<N> implements Walker<N> {
        private final List<N> elements = new ArrayList<>();

        @Override
        public void onEmpty() {

        }

        @Override
        public boolean onNext(N node) {
            elements.add(node);
            return true;
        }

        @Override
        public void onCompleted() {
        }
    }

    public static final class TestTreeWalker<N> implements TreeWalker<N> {
        private final List<N> elements = new ArrayList<>();

        @Override
        public void onStarted() {
        }

        @Override
        public void onNode(N node) {
            elements.add(node);
        }

        @Override
        public void onNodeChildrenStarted(N node) {
        }

        @Override
        public void onNodeChildrenRemaining(N node) {
        }

        @Override
        public void onNodeChildrenCompleted(N node) {
        }

        @Override
        public void onNodeNoChildren(N node) {
        }

        @Override
        public void onCompleted() {
        }
    }
}