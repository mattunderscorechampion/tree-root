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

import com.mattunderscore.trees.*;
import com.mattunderscore.trees.common.TreesImpl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author matt on 06/09/14.
 */
public final class BinaryTreeTest {

    @Test
    public void empty() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> builder = trees.bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree = builder.build(BinaryTree.class);
        assertTrue(tree.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void lessThanTwo() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> builder = trees.bottomUpBuilder();
        builder.create("a",
            builder.create("b"),
            builder.create("c"),
            builder.create("d"))
                .build(BinaryTree.class);
    }

    @Test
    public void twoChildren() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> builder = trees.bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree =
            builder.create("a",
                builder.create("b"),
                builder.create("c")).build(BinaryTree.class);

        assertFalse(tree.isEmpty());
        assertEquals("a", tree.getRoot().getElement());
        assertFalse(tree.getRoot().isLeaf());
        assertEquals("b", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());

        final Children<? extends BinaryTreeNode<String>> children = tree.getRoot().getChildren();
        final Iterator<? extends BinaryTreeNode<String>> iterator = children.iterator();
        final BinaryTreeNode<String> left = iterator.next();
        assertTrue(left.isLeaf());
        assertEquals("b", left.getElement());
        final BinaryTreeNode<String> right = iterator.next();
        assertTrue(right.isLeaf());
        assertEquals("c", right.getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void leftOnly() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> builder = trees.bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree =
                builder.create("a",
                        builder.create("b")).build(BinaryTree.class);

        assertFalse(tree.isEmpty());
        assertEquals("a", tree.getRoot().getElement());
        assertFalse(tree.getRoot().isLeaf());
        assertEquals("b", tree.getRoot().getLeft().getElement());
        assertNull(tree.getRoot().getRight());

        final Children<? extends BinaryTreeNode<String>> children = tree.getRoot().getChildren();
        final Iterator<? extends BinaryTreeNode<String>> iterator = children.iterator();
        final BinaryTreeNode<String> left = iterator.next();
        assertTrue(left.isLeaf());
        assertEquals("b", left.getElement());
        assertFalse(iterator.hasNext());
    }

    @Ignore("Not sure how I want to build a binary tree with only a right child")
    @Test
    public void rightOnly() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> builder = trees.bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree =
                builder.create("a",
                        null,
                        builder.create("c")).build(BinaryTree.class);

        assertFalse(tree.isEmpty());
        assertEquals("a", tree.getRoot().getElement());
        assertFalse(tree.getRoot().isLeaf());
        assertNull(tree.getRoot().getLeft());
        assertEquals("c", tree.getRoot().getRight().getElement());

        final Children<? extends BinaryTreeNode<String>> children = tree.getRoot().getChildren();
        final Iterator<? extends BinaryTreeNode<String>> iterator = children.iterator();
        final BinaryTreeNode<String> left = iterator.next();
        assertNull(left);
        final BinaryTreeNode<String> right = iterator.next();
        assertTrue(right.isLeaf());
        assertEquals("c", right.getElement());
        assertFalse(iterator.hasNext());
    }
}
