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

package com.mattunderscore.trees.linked.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;

public final class LinkedTreeTest {
    private LinkedTree<String> tree;

    @Before
    public void setUp() {
        final Constructor<String> constructor = new Constructor<>();
        tree = constructor.build(
            "a",
                constructor.build(
                    "b"),
                constructor.build(
                    "c"));
    }

    @Test
    public void structure() {
        assertFalse(tree.isEmpty());
        assertNotNull(tree.getRoot());
        assertFalse(tree.getRoot().isLeaf());
        assertEquals(2, tree.getRoot().getNumberOfChildren());
        final Iterator<? extends MutableSettableStructuredNode<String>> iterator = tree.childIterator();
        final MutableSettableStructuredNode<String> child0 = iterator.next();
        final MutableSettableStructuredNode<String> child1 = iterator.next();

        assertTrue(child0.isLeaf());
        assertTrue(child1.isLeaf());

        assertEquals(String.class, child0.getElementClass());
        assertEquals("c", child1.getElement());
    }

    @Test
    public void add() {
        assertNotNull(tree.getRoot());
        final MutableSettableStructuredNode<String> newNode = tree.getRoot().addChild("d");
        assertEquals("d", newNode.getElement());

        assertEquals(3, tree.getRoot().getNumberOfChildren());
        final Iterator<? extends MutableSettableStructuredNode<String>> iterator = tree.childIterator();
        final MutableSettableStructuredNode<String> child0 = iterator.next();
        final MutableSettableStructuredNode<String> child1 = iterator.next();
        final MutableSettableStructuredNode<String> child2 = iterator.next();

        assertEquals("b", child0.getElement());
        assertEquals("c", child1.getElement());
        assertEquals("d", child2.getElement());
    }

    @Test
    public void remove() {
        assertNotNull(tree.getRoot());
        assertEquals(2, tree.getRoot().getNumberOfChildren());
        final Iterator<? extends MutableSettableStructuredNode<String>> iterator0 = tree.childIterator();
        final MutableSettableStructuredNode<String> child0 = iterator0.next();
        final MutableSettableStructuredNode<String> child1 = iterator0.next();

        assertEquals("b", child0.getElement());
        assertEquals("c", child1.getElement());
        assertFalse(iterator0.hasNext());
        assertTrue(tree.getRoot().removeChild(child0));

        final Iterator<? extends MutableSettableStructuredNode<String>> iterator1 = tree.childIterator();
        final MutableSettableStructuredNode<String> child2 = iterator1.next();
        assertEquals("c", child2.getElement());
        assertFalse(iterator1.hasNext());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get() {
        assertNotNull(tree.getRoot());
        final LinkedTree<String> node0 = tree.getRoot().getChild(0);
        final LinkedTree<String> node1 = tree.getRoot().getChild(1);
        assertEquals("b", node0.getElement());
        assertEquals("c", node1.getElement());

        tree.getRoot().getChild(2);
    }

    @Test
    public void set() {
        assertNotNull(tree.getRoot());
        tree.getRoot().setChild(2, "d");

        assertEquals(3, tree.getRoot().getNumberOfChildren());
        final Iterator<LinkedTree<String>> iterator = tree.childIterator();
        assertEquals("b", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void setWithNulls() {
        assertNotNull(tree.getRoot());
        tree.getRoot().setChild(3, "d");

        assertEquals(4, tree.getRoot().getNumberOfChildren());
        final Iterator<LinkedTree<String>> iterator = tree.childIterator();
        assertEquals("b", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertFalse(iterator.hasNext());

        final Iterator<LinkedTree<String>> structuralIterator = tree.childStructuralIterator();
        assertEquals("b", structuralIterator.next().getElement());
        assertEquals("c", structuralIterator.next().getElement());
        assertNull(structuralIterator.next());
        assertEquals("d", structuralIterator.next().getElement());
        assertFalse(structuralIterator.hasNext());
    }

    @Test
    public void emptyConstructor() {
        final EmptyTreeConstructor<String, MutableSettableStructuredNode<String>, LinkedTree<String>> constructor = new EmptyConstructor<>();
        final LinkedTree<String> emptyTree = constructor.build();
        assertTrue(emptyTree.isEmpty());
        assertNull(emptyTree.getRoot());
        assertEquals(LinkedTree.class, constructor.forClass());
    }

    @Test
    public void nodeToTreeConverterTest0() {
        final NodeConverter<String> converter = new NodeConverter<>();
        final LinkedTree<String> newTree = converter.treeFromRootNode(tree);

        assertFalse(newTree.isEmpty());
        assertNotNull(newTree.getRoot());
        assertFalse(newTree.getRoot().isLeaf());
        assertEquals(2, newTree.getRoot().getNumberOfChildren());
        final Iterator<? extends MutableSettableStructuredNode<String>> iterator = newTree.childIterator();
        final MutableSettableStructuredNode<String> child0 = iterator.next();
        final MutableSettableStructuredNode<String> child1 = iterator.next();

        assertTrue(child0.isLeaf());
        assertTrue(child1.isLeaf());

        assertEquals(String.class, child0.getElementClass());
        assertEquals("c", child1.getElement());

        assertEquals(LinkedTree.class, converter.forClass());
    }

    @Test
    public void nodeToTreeConverterTest1() {
        final NodeConverter<String> converter = new NodeConverter<>();
        final LinkedTree<String> newTree = converter.treeFromRootNode(tree.getChild(1));

        assertFalse(newTree.isEmpty());
        assertNotNull(newTree.getRoot());
        assertTrue(newTree.getRoot().isLeaf());
        assertEquals("c", newTree.getRoot().getElement());

        assertEquals(LinkedTree.class, converter.forClass());
    }

    @Test
    public void converter() {
        final Converter<String> converter = new Converter<>();
        final LinkedTree<String> newTree = converter.build(tree);

        assertFalse(newTree.isEmpty());
        assertNotNull(newTree.getRoot());
        assertFalse(newTree.getRoot().isLeaf());
        assertEquals(2, newTree.getRoot().getNumberOfChildren());
        final Iterator<? extends MutableSettableStructuredNode<String>> iterator = newTree.childIterator();
        final MutableSettableStructuredNode<String> child0 = iterator.next();
        final MutableSettableStructuredNode<String> child1 = iterator.next();

        assertTrue(child0.isLeaf());
        assertTrue(child1.isLeaf());

        assertEquals(String.class, child0.getElementClass());
        assertEquals("c", child1.getElement());

        assertEquals(LinkedTree.class, converter.forClass());
    }
}
