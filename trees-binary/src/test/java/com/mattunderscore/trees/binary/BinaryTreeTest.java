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

package com.mattunderscore.trees.binary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;

/**
 * @author Matt Champion on 06/09/14.
 */
public final class BinaryTreeTest {
    private final Trees trees = new TreesImpl();

    @Test
    public void empty() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree = builder.build(BinaryTreeNodeImpl.<String>typeKey());
        assertTrue(tree.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void moreThanTwo() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        builder.create("a",
            builder.create("b"),
            builder.create("c"),
            builder.create("d"))
                .build(BinaryTreeNodeImpl.<String>typeKey());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void noChildren() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree =
            builder.create("a").build(BinaryTreeNodeImpl.<String>typeKey());

        assertFalse(tree.isEmpty());
        assertEquals("a", tree.getRoot().getElement());
        assertEquals(0, tree.getRoot().getNumberOfChildren());
        assertTrue(tree.getRoot().isLeaf());
        assertNull(tree.getRoot().getLeft());
        assertNull(tree.getRoot().getRight());
        assertNull(tree.getRoot().getChild(0));
        assertNull(tree.getRoot().getChild(1));
        final Iterator<? extends BinaryTreeNode<String>> iterator = tree.getRoot().childIterator();
        assertFalse(iterator.hasNext());
        final Iterator<? extends BinaryTreeNode<String>> structuralIterator = tree.getRoot().childStructuralIterator();
        assertNull(structuralIterator.next());
        assertNull(structuralIterator.next());
        assertFalse(structuralIterator.hasNext());

        tree.getRoot().getChild(2);
    }

    @Test
    public void twoChildren() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree =
            builder.create("a",
                builder.create("b"),
                builder.create("c")).build(BinaryTreeNodeImpl.<String>typeKey());

        assertFalse(tree.isEmpty());
        assertEquals("a", tree.getRoot().getElement());
        assertEquals(2, tree.getRoot().getNumberOfChildren());
        assertFalse(tree.getRoot().isLeaf());
        assertEquals("b", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());

        final Iterator<? extends BinaryTreeNode<String>> iterator = tree.getRoot().childIterator();
        final BinaryTreeNode<String> left = iterator.next();
        assertSame(left, tree.getRoot().getChild(0));
        assertSame(left, tree.getRoot().getLeft());
        assertTrue(left.isLeaf());
        assertEquals("b", left.getElement());
        final BinaryTreeNode<String> right = iterator.next();
        assertSame(right, tree.getRoot().getChild(1));
        assertSame(right, tree.getRoot().getRight());
        assertTrue(right.isLeaf());
        assertEquals("c", right.getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void leftOnly() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree =
            builder.create("a",
                    builder.create("b")).build(BinaryTreeNodeImpl.<String>typeKey());

        assertFalse(tree.isEmpty());
        assertEquals("a", tree.getRoot().getElement());
        assertEquals(1, tree.getRoot().getNumberOfChildren());
        assertFalse(tree.getRoot().isLeaf());
        assertEquals("b", tree.getRoot().getLeft().getElement());
        assertSame(tree.getRoot().getLeft(), tree.getRoot().getChild(0));
        assertNull(tree.getRoot().getRight());
        assertNull(tree.getRoot().getChild(1));

        final Iterator<? extends BinaryTreeNode<String>> iterator = tree.getRoot().childIterator();
        final BinaryTreeNode<String> left = iterator.next();
        assertTrue(left.isLeaf());
        assertEquals("b", left.getElement());
        assertFalse(iterator.hasNext());
    }

    @Ignore("Not sure how I want to build a binary tree with only a right child")
    @Test
    public void rightOnly() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final BinaryTree<String, BinaryTreeNode<String>> tree =
            builder.create("a",
                    null,
                    builder.create("c")).build(BinaryTreeNodeImpl.<String>typeKey());

        assertFalse(tree.isEmpty());
        assertEquals("a", tree.getRoot().getElement());
        assertEquals(1, tree.getRoot().getNumberOfChildren());
        assertFalse(tree.getRoot().isLeaf());
        assertNull(tree.getRoot().getLeft());
        assertEquals("c", tree.getRoot().getRight().getElement());

        final Iterator<? extends BinaryTreeNode<String>> iterator = tree.getRoot().childIterator();
        final BinaryTreeNode<String> left = iterator.next();
        assertNull(left);
        assertNull(tree.getRoot().getLeft());
        assertNull(tree.getRoot().getChild(0));
        final BinaryTreeNode<String> right = iterator.next();
        assertTrue(right.isLeaf());
        assertEquals("c", right.getElement());
        assertSame(right, tree.getRoot().getRight());
        assertSame(right, tree.getRoot().getChild(1));
        assertFalse(iterator.hasNext());
    }

    @Test
    public void topDownBuilder() {
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String> builder =
            trees.treeBuilders().<String>topDownBuilder().root("root");
        final TopDownTreeRootBuilder.TopDownTreeBuilderAppender<String> child0 = builder.addChild("child0");
        builder.addChild("child1");
        child0.addChild("child2");

        final BinaryTree<String, BinaryTreeNode<String>> tree = builder.build(BinaryTree.class);

        assertEquals("root", tree.getRoot().getElement());
        assertEquals("child0", tree.getRoot().getLeft().getElement());
        assertEquals("child1", tree.getRoot().getRight().getElement());
        assertEquals("child2", tree.getRoot().getLeft().getLeft().getElement());
    }
}
