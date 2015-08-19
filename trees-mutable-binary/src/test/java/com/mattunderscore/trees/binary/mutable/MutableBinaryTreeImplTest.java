/* Copyright © 2015 Matthew Champion
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

package com.mattunderscore.trees.binary.mutable;

import java.util.Iterator;

import com.mattunderscore.trees.binary.MutableBinaryTree;
import org.junit.Test;

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;

import static org.junit.Assert.*;

/**
 * Tests for {@link MutableBinaryTreeImpl}.
 *
 * @author Matt Champion on 04/05/15
 */
public final class MutableBinaryTreeImplTest {
    private final Constructor<String> constructor = new Constructor<>();

    @Test
    public void build() {
        final MutableBinaryTreeImpl<String> leftSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("c", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{leftSubtree, rightSubtree});

        assertEquals("a", tree.getRoot().getElement());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator = tree.getRoot().childIterator();
        final MutableBinaryTreeNode<String> left = iterator.next();
        final MutableBinaryTreeNode<String> right = iterator.next();
        assertFalse(iterator.hasNext());

        assertEquals("b", left.getElement());
        assertEquals("c", right.getElement());
        assertFalse(left.childIterator().hasNext());
        assertFalse(right.childIterator().hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void badBuild() {
        final MutableBinaryTreeImpl<String> subtree0 = constructor.build("a", new MutableBinaryTreeImpl[0]);
        final MutableBinaryTreeImpl<String> subtree1 = constructor.build("b", new MutableBinaryTreeImpl[0]);
        final MutableBinaryTreeImpl<String> subtree2 = constructor.build("c", new MutableBinaryTreeImpl[0]);

        constructor.build("a", new MutableBinaryTreeImpl[]{subtree0, subtree1, subtree2});
    }

    @Test
    public void mutate() {
        final MutableBinaryTreeImpl<String> leftSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("c", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{leftSubtree, rightSubtree});

        assertEquals("a", tree.getRoot().getElement());
        final MutableBinaryTreeNode<String> left = tree.getRoot().getLeft();
        assertNull(left.getLeft());
        final MutableBinaryTreeNode<String> leftOfLeft = left.setLeft("d");
        assertEquals("d", left.getLeft().getElement());
        assertEquals(leftOfLeft, left.getLeft());

        assertNull(left.getRight());
        final MutableBinaryTreeNode<String> rightOfLeft = left.setRight("e");
        assertEquals("e", left.getRight().getElement());
        assertEquals(rightOfLeft, left.getRight());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator = left.childIterator();
        assertTrue(iterator.hasNext());
        assertEquals("d", iterator.next().getElement());
        assertEquals("e", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void setRoot() {
        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>();

        assertTrue(tree.isEmpty());

        final MutableBinaryTreeNode<String> node = tree.setRoot("a");

        assertEquals("a", node.getElement());
        assertFalse(tree.isEmpty());
        assertSame(node, tree.getRoot());
    }
}
