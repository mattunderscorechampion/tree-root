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

package com.mattunderscore.trees.tests.traversers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.common.LinkedTree;
import com.mattunderscore.trees.common.TreesImpl;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for post-order iterator.
 * @author Matt Champion on 03/09/14.
 */
public final class PostOrderIteratorTest {
    private static TreeIteratorFactory iterators;
    private static Tree<String, ? extends Node<String>> tree;

    @BeforeClass
    public static void setUp() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("f",
            builder.create("b",
                builder.create("a"),
                builder.create("d",
                    builder.create("c"),
                    builder.create("e"))),
            builder.create("i",
                builder.create("h",
                    builder.create("g")))).build(LinkedTree.<String>typeKey());

        iterators = trees.treeIterators();
    }

    @Test
    public void nodeIterator()
    {
        final Iterator<Node<String>> iterator = iterators.postOrderIterator(tree);
        Assert.assertEquals("a", iterator.next().getElement());
        Assert.assertEquals("c", iterator.next().getElement());
        Assert.assertEquals("e", iterator.next().getElement());
        Assert.assertEquals("d", iterator.next().getElement());
        Assert.assertEquals("b", iterator.next().getElement());
        Assert.assertEquals("g", iterator.next().getElement());
        Assert.assertEquals("h", iterator.next().getElement());
        Assert.assertEquals("i", iterator.next().getElement());
        Assert.assertEquals("f", iterator.next().getElement());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void elementIterator()
    {
        final Iterator<String> iterator = iterators.postOrderElementsIterator(tree);
        Assert.assertEquals("a", iterator.next());
        Assert.assertEquals("c", iterator.next());
        Assert.assertEquals("e", iterator.next());
        Assert.assertEquals("d", iterator.next());
        Assert.assertEquals("b", iterator.next());
        Assert.assertEquals("g", iterator.next());
        Assert.assertEquals("h", iterator.next());
        Assert.assertEquals("i", iterator.next());
        Assert.assertEquals("f", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void prestartRemove() {
        final Iterator<Node<String>> iterator = iterators.postOrderIterator(tree);

        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        final Iterator<Node<String>> iterator = iterators.postOrderIterator(tree);

        Assert.assertEquals("a", iterator.next().getElement());
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void prestartElementRemove() {
        final Iterator<String> iterator = iterators.postOrderElementsIterator(tree);

        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void elementRemove() {
        final Iterator<String> iterator = iterators.postOrderElementsIterator(tree);

        Assert.assertEquals("a", iterator.next());
        iterator.remove();
    }
}