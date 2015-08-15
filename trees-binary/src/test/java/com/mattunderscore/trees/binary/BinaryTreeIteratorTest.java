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

import java.util.Iterator;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.binary.mutable.MutableBinaryTreeImpl;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.tree.Tree;

/**
 * @author Matt Champion on 08/09/14.
 */
public final class BinaryTreeIteratorTest {
    private static final Trees trees = Trees.get();

    @Test
    public void preorder() {
        final Tree<String, MutableBinaryTreeNode<String>> tree = createTree();
        final Iterator<MutableBinaryTreeNode<String>> iterator = trees.treeIterators().preOrderIterator(tree);
        assertEquals("f", iterator.next().getElement());
        assertEquals("b", iterator.next().getElement());
        assertEquals("a", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("e", iterator.next().getElement());
        assertEquals("g", iterator.next().getElement());
        assertEquals("i", iterator.next().getElement());
        assertEquals("h", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void inorder() {
        final Tree<String, MutableBinaryTreeNode<String>> tree = createTree();
        final Iterator<MutableBinaryTreeNode<String>> iterator = trees.treeIterators().inOrderIterator(tree);
        assertEquals("a", iterator.next().getElement());
        assertEquals("b", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertEquals("e", iterator.next().getElement());
        assertEquals("f", iterator.next().getElement());
        assertEquals("g", iterator.next().getElement());
        assertEquals("h", iterator.next().getElement());
        assertEquals("i", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void postorder() {
        final Tree<String, MutableBinaryTreeNode<String>> tree = createTree();
        final Iterator<MutableBinaryTreeNode<String>> iterator = trees.treeIterators().postOrderIterator(tree);
        assertEquals("a", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("e", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertEquals("b", iterator.next().getElement());
        assertEquals("h", iterator.next().getElement());
        assertEquals("i", iterator.next().getElement());
        assertEquals("g", iterator.next().getElement());
        assertEquals("f", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void breadthfirst() {
        final Tree<String, MutableBinaryTreeNode<String>> tree = createTree();
        final Iterator<MutableBinaryTreeNode<String>> iterator = trees.treeIterators().breadthFirstIterator(tree);
        assertEquals("f", iterator.next().getElement());
        assertEquals("b", iterator.next().getElement());
        assertEquals("g", iterator.next().getElement());
        assertEquals("a", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertEquals("i", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("e", iterator.next().getElement());
        assertEquals("h", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    private static Tree<String, MutableBinaryTreeNode<String>> createTree() {
        final Tree<String, MutableBinaryTreeNode<String>> tree = trees.treeBuilders().<String, MutableBinaryTreeNode<String>>topDownBuilder()
            .root("f").build(MutableBinaryTreeImpl.<String>typeKey());
        final MutableBinaryTreeNode<String> f = tree.getRoot();
        final MutableBinaryTreeNode<String> b = f.setLeft("b");
        b.setLeft("a");
        final MutableBinaryTreeNode<String> d = b.setRight("d");
        d.setLeft("c");
        d.setRight("e");
        final MutableBinaryTreeNode<String> g = f.setRight("g");
        final MutableBinaryTreeNode<String> h = g.setRight("i");
        h.setLeft("h");
        return tree;
    }
}
