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

package com.mattunderscore.trees.immutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Tests for {@link TreeNodeImpl}.
 * @author Matt Champion on 09/05/2015
 */
public final class TreeNodeImplTest {
    private static final Trees trees = Trees.get();

    @Test
    public void topDownEmpty0() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>topDownBuilder()
            .build(new TypeKey<Tree<String, Node<String>>>() {});
        assertTrue(tree.isEmpty());
        assertNull(tree.getRoot());
    }

    @Test
    public void topDownEmpty1() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>topDownBuilder()
            .build(TreeNodeImpl.<String>typeKey());
        assertTrue(tree.isEmpty());
        assertNull(tree.getRoot());
    }

    @Test
    public void bottomUpEmpty0() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>bottomUpBuilder()
            .build(new TypeKey<Tree<String, Node<String>>>() {});
        assertTrue(tree.isEmpty());
        assertNull(tree.getRoot());
    }

    @Test
    public void bottomUpEmpty1() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>bottomUpBuilder()
            .build(Tree.<String, Node<String>>typeKey());
        assertTrue(tree.isEmpty());
        assertNull(tree.getRoot());
    }

    @Test
    public void singleNodeTree0() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>topDownBuilder()
            .root("root")
            .build(TreeNodeImpl.<String>typeKey());
        assertFalse(tree.isEmpty());
        final Node<String> root = tree.getRoot();
        assertTrue(root.isLeaf());
        assertEquals("root", root.getElement());
        assertEquals(0, root.getNumberOfChildren());
        assertFalse(root.childIterator().hasNext());
    }

    @Test
    public void singleNodeTree1() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>bottomUpBuilder()
            .create("root")
            .build(TreeNodeImpl.<String>typeKey());
        assertFalse(tree.isEmpty());
        final Node<String> root = tree.getRoot();
        assertTrue(root.isLeaf());
        assertEquals("root", root.getElement());
        assertEquals(0, root.getNumberOfChildren());
        assertFalse(root.childIterator().hasNext());
    }

    @Test
    public void singleNodeTree2() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>topDownBuilder()
            .root("root")
            .build(new TypeKey<Tree<String, Node<String>>>() {});
        assertFalse(tree.isEmpty());
        final Node<String> root = tree.getRoot();
        assertTrue(root.isLeaf());
        assertEquals("root", root.getElement());
        assertEquals(0, root.getNumberOfChildren());
        assertFalse(root.childIterator().hasNext());
    }

    @Test
    public void singleNodeTree3() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>bottomUpBuilder()
            .create("root")
            .build(new TypeKey<Tree<String, Node<String>>>() {});
        assertFalse(tree.isEmpty());
        final Node<String> root = tree.getRoot();
        assertTrue(root.isLeaf());
        assertEquals("root", root.getElement());
        assertEquals(0, root.getNumberOfChildren());
        assertFalse(root.childIterator().hasNext());
    }

    @Test
    public void singleNodeTree4() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>topDownBuilder()
            .root("root")
            .build(TreeNodeImpl.<String>typeKey());
        assertFalse(tree.isEmpty());
        final Node<String> root = tree.getRoot();
        assertTrue(root.isLeaf());
        assertEquals("root", root.getElement());
        assertEquals(0, root.getNumberOfChildren());
        assertFalse(root.childIterator().hasNext());
    }

    @Test
    public void singleNodeTree5() {
        final Tree<String, Node<String>> tree = trees.treeBuilders()
            .<String, Node<String>>bottomUpBuilder()
            .create("root")
            .build(TreeNodeImpl.<String>typeKey());
        assertFalse(tree.isEmpty());
        final Node<String> root = tree.getRoot();
        assertTrue(root.isLeaf());
        assertEquals("root", root.getElement());
        assertEquals(0, root.getNumberOfChildren());
        assertFalse(root.childIterator().hasNext());
    }
}
