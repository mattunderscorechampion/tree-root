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

package com.mattunderscore.trees.traversers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.spi.DefaultRemovalHandler;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.tree.Tree;

/**
 * Breadth first iterator tests
 * @author Matt Champion on 05/09/14.
 */
public final class BreadthFirstIteratorTest {
    private static Tree<String, MutableSettableStructuredNode<String>> tree;
    private Iterator<? extends MutableSettableStructuredNode<String>> iterator;
    private Iterator<String> elementIterator;

    @BeforeClass
    public static void setUpTree() {
        final TreeConstructor<String, MutableSettableStructuredNode<String>, LinkedTree<String>> constructor = new LinkedTree.Constructor<>();
        tree = constructor.build(
            "f",
            new LinkedTree[]{
                constructor.build(
                    "b",
                    new LinkedTree[]{
                        constructor.build(
                            "a",
                            new LinkedTree[]{}),
                        constructor.build(
                            "d",
                            new LinkedTree[]{
                                constructor.build(
                                    "c",
                                    new LinkedTree[]{}),
                                constructor.build(
                                    "e",
                                    new LinkedTree[]{})
                            })
                    }),
                constructor.build(
                    "i",
                    new LinkedTree[]{
                        constructor.build(
                            "h",
                            new LinkedTree[]{
                                constructor.build(
                                    "g",
                                    new LinkedTree[]{})})})});
    }

    @Before
    public void setUp() {
        iterator = new BreadthFirstIterator<>(tree, new DefaultRemovalHandler<>());
        elementIterator = new NodeToElementIterators<>(iterator);
    }

    @Test
    public void nodeIterator()  {
        assertEquals("f", iterator.next().getElement());
        assertEquals("b", iterator.next().getElement());
        assertEquals("i", iterator.next().getElement());
        assertEquals("a", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertEquals("h", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("e", iterator.next().getElement());
        assertEquals("g", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void elementIterator()  {
        assertEquals("f", elementIterator.next());
        assertEquals("b", elementIterator.next());
        assertEquals("i", elementIterator.next());
        assertEquals("a", elementIterator.next());
        assertEquals("d", elementIterator.next());
        assertEquals("h", elementIterator.next());
        assertEquals("c", elementIterator.next());
        assertEquals("e", elementIterator.next());
        assertEquals("g", elementIterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void prestartRemove() {
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        assertEquals("f", iterator.next().getElement());
        iterator.remove();
    }
}
