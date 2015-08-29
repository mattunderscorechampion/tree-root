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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;

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

        assertFalse(tree.getRoot().isLeaf());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator = tree.getRoot().childIterator();
        final MutableBinaryTreeNode<String> left = iterator.next();
        final MutableBinaryTreeNode<String> right = iterator.next();
        assertFalse(iterator.hasNext());

        assertEquals("b", left.getElement());
        assertEquals("c", right.getElement());
        assertFalse(left.childIterator().hasNext());
        assertFalse(right.childIterator().hasNext());
        assertTrue(left.isLeaf());
        assertTrue(right.isLeaf());

        assertEquals(left, tree.getRoot().getChild(0));
        assertEquals(right, tree.getRoot().getChild(1));
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

    @Test
    public void removeByIterator() {
        final MutableBinaryTreeImpl<String> leftSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("c", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{leftSubtree, rightSubtree});

        assertEquals(2, tree.getRoot().getNumberOfChildren());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator0 = tree.getRoot().childIterator();
        assertEquals("b", iterator0.next().getElement());
        iterator0.remove();
        assertTrue(iterator0.hasNext());

        assertEquals(1, tree.getRoot().getNumberOfChildren());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator1 = tree.getRoot().childIterator();
        assertEquals("c", iterator1.next().getElement());
        iterator1.remove();
        assertFalse(iterator1.hasNext());

        assertEquals(0, tree.getRoot().getNumberOfChildren());
    }

    @Test
    public void removeByStructuralIterator() {
        final MutableBinaryTreeImpl<String> leftSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("c", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{leftSubtree, rightSubtree});

        assertEquals(2, tree.getRoot().getNumberOfChildren());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator0 = tree.getRoot().childStructuralIterator();
        assertEquals("b", iterator0.next().getElement());
        iterator0.remove();
        assertEquals("c", iterator0.next().getElement());

        assertEquals(1, tree.getRoot().getNumberOfChildren());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator1 = tree.getRoot().childStructuralIterator();
        assertNull(iterator1.next());
        assertEquals("c", iterator1.next().getElement());
        iterator1.remove();
        assertFalse(iterator1.hasNext());

        assertEquals(0, tree.getRoot().getNumberOfChildren());
    }

    @Test(expected = IllegalStateException.class)
    public void earlyRemove() {
        final MutableBinaryTreeImpl<String> leftSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("c", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{leftSubtree, rightSubtree});

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator0 = tree.getRoot().childIterator();

        iterator0.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void earlyStructuralRemove() {
        final MutableBinaryTreeImpl<String> leftSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("c", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{leftSubtree, rightSubtree});

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator0 = tree.getRoot().childStructuralIterator();

        iterator0.remove();
    }

    @Test
    public void skipMissing() {
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{new MutableBinaryTreeImpl<>(), rightSubtree});

        assertEquals(1, tree.getRoot().getNumberOfChildren());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator0 = tree.getRoot().childIterator();
        assertEquals("b", iterator0.next().getElement());
        assertFalse(iterator0.hasNext());
    }

    @Test
    public void nullsForEmpty() {
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{new MutableBinaryTreeImpl<>(), rightSubtree});

        assertEquals(1, tree.getRoot().getNumberOfChildren());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator0 = tree.getRoot().childStructuralIterator();
        assertNull(iterator0.next());
        assertEquals("b", iterator0.next().getElement());
        assertFalse(iterator0.hasNext());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLarge() {
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{new MutableBinaryTreeImpl<>(), rightSubtree});
        tree.getRoot().getChild(3);
    }

    @Test
    public void getNull() {
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{new MutableBinaryTreeImpl<>(), rightSubtree});
        assertNull(tree.getRoot().getChild(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator() {
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{new MutableBinaryTreeImpl<>(), rightSubtree});

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator = tree.getRoot().childIterator();

        assertEquals("b", iterator.next().getElement());
        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void structuralIterator() {
        final MutableBinaryTreeImpl<String> rightSubtree = constructor.build("b", new MutableBinaryTreeImpl[0]);

        final MutableBinaryTreeImpl<String> tree = constructor.build("a", new MutableBinaryTreeImpl[]{new MutableBinaryTreeImpl<>(), rightSubtree});

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator = tree.getRoot().childStructuralIterator();

        assertNull(iterator.next());
        assertEquals("b", iterator.next().getElement());
        assertFalse(iterator.hasNext());
        iterator.next();
    }
}
