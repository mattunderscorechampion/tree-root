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
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.tree.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public final class LinkedTreeTest {
    private static final Trees trees = new TreesImpl();
    private LinkedTree<String> tree;

    @Before
    public void setUp() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("a", builder.create("b"), builder.create("c")).build(LinkedTree.<String>typeKey());
    }

    @Test
    public void structure() {
        assertFalse(tree.isEmpty());
        assertFalse(tree.getRoot().isLeaf());
        final SimpleCollection<? extends MutableNode<String>> children = tree.getRoot().getChildren();
        assertEquals(2, children.size());
        final Iterator<? extends Node<String>> iterator = children.iterator();
        final Node<String> child0 = iterator.next();
        final Node<String> child1 = iterator.next();

        assertTrue(child0.isLeaf());
        assertTrue(child1.isLeaf());

        assertEquals(String.class, child0.getElementClass());
        assertEquals("c", child1.getElement());
    }

    @Test
    public void add() {
        final Node<String> newNode = tree.getRoot().addChild("d");

        final SimpleCollection<? extends MutableNode<String>> children = tree.getRoot().getChildren();
        assertEquals(3, children.size());
        final Iterator<? extends Node<String>> iterator = children.iterator();
        final Node<String> child0 = iterator.next();
        final Node<String> child1 = iterator.next();
        final Node<String> child2 = iterator.next();

        assertEquals("d", child2.getElement());
    }

    @Test
    public void remove() {
        final SimpleCollection<? extends MutableNode<String>> children = tree.getRoot().getChildren();
        assertEquals(2, children.size());
        final Iterator<? extends MutableNode<String>> iterator0 = children.iterator();
        final MutableNode<String> child0 = iterator0.next();
        final MutableNode<String> child1 = iterator0.next();

        assertEquals("b", child0.getElement());
        assertEquals("c", child1.getElement());
        assertFalse(iterator0.hasNext());
        assertTrue(tree.getRoot().removeChild(child0));

        final Iterator<? extends MutableNode<String>> iterator1 = children.iterator();
        final MutableNode<String> child2 = iterator1.next();
        assertEquals("c", child2.getElement());
        assertFalse(iterator1.hasNext());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get() {
        final LinkedTree<String> node0 = tree.getRoot().getChild(0);
        final LinkedTree<String> node1 = tree.getRoot().getChild(1);
        assertEquals("b", node0.getElement());
        assertEquals("c", node1.getElement());

        tree.getRoot().getChild(2);
    }

    @Test
    public void set() {
        tree.getRoot().setChild(2, "d");

        assertEquals(3, tree.getRoot().getChildren().size());
        final Iterator<LinkedTree<String>> iterator = tree.getChildren().iterator();
        assertEquals("b", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void setWithNulls() {
        tree.getRoot().setChild(3, "d");

        assertEquals(4, tree.getRoot().getChildren().size());
        final Iterator<LinkedTree<String>> iterator = tree.getChildren().iterator();
        assertEquals("b", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertFalse(iterator.hasNext());

        final Iterator<LinkedTree<String>> structuralIterator = tree.getChildren().structuralIterator();
        assertEquals("b", structuralIterator.next().getElement());
        assertEquals("c", structuralIterator.next().getElement());
        assertNull(structuralIterator.next());
        assertEquals("d", structuralIterator.next().getElement());
        assertFalse(structuralIterator.hasNext());
    }
}
