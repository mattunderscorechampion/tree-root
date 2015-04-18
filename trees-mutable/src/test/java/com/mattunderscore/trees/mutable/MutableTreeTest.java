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

package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.construction.NodeAppender;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests that run on mutable trees.
 * The tests are not applied to path copy trees because of the view semantics.
 * @author Matt Champion on 15/07/14.
 */
@RunWith(Parameterized.class)
public final class MutableTreeTest {
    private static final Trees trees = new TreesImpl();
    private final Class<? extends MutableTree<String, MutableNode<String>>> treeClass;

    public MutableTreeTest(Class<? extends MutableTree<String, MutableNode<String>>> treeClass) {
        this.treeClass = treeClass;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {MutableTree.class}, // 0
            {MutableTreeImpl.class}, // 1, repeats 0 with different key
            {LinkedTree.class} // 2
        });
    }

    @Test
    public void mutateTree() {
        final TopDownTreeRootBuilder<String> builder = trees.treeBuilders().topDownBuilder();
        final MutableTree<String, MutableNode<String>> tree = builder.root("a").build(treeClass);
        final MutableNode<String> root = tree.getRoot();
        assertTrue(root.isLeaf());
        final MutableNode<String> depth1 = root.addChild("b");
        assertFalse(root.isLeaf());
        depth1.addChild("c");

        assertEquals(1, root.getNumberOfChildren());
        final Iterator<? extends MutableNode<String>> iterator0 = root.childIterator();
        assertTrue(iterator0.hasNext());
        final MutableNode<String> child0 = iterator0.next();
        assertEquals("b", child0.getElement());

        final Iterator<? extends MutableNode<String>> iterator1 = child0.childIterator();
        assertTrue(iterator1.hasNext());
        final MutableNode<String> child1 = iterator1.next();
        assertEquals("c", child1.getElement());
        assertFalse(iterator1.hasNext());

        depth1.addChild("d");
        final Iterator<? extends MutableNode<String>> iterator2 = child0.childIterator();
        assertTrue(iterator2.hasNext());
        final MutableNode<String> child2 = iterator2.next();
        assertEquals("c", child2.getElement());
        assertTrue(iterator2.hasNext());
        final MutableNode<String> child3 = iterator2.next();
        assertEquals("d", child3.getElement());
        assertFalse(iterator2.hasNext());
    }

    /**
     * This tests the copy on modify behavior in a single thread, NOT thread safety.
     */
    @Ignore("Modifications to the grandchildren of node visited even though modifications to the parent are not")
    @Test
    public void mutationDuringTraversal() {
        // Create a simple tree
        final TopDownTreeRootBuilder<String> builder = trees.treeBuilders().topDownBuilder();
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String> builder0 = builder.root("a");
        final NodeAppender<String, ?> appender0 = builder0.addChild("b");
        final NodeAppender<String, ?> appender1 = builder0.addChild("e");
        appender0.addChild("c");
        appender0.addChild("d");
        appender1.addChild("f");
        final MutableTree<String, MutableNode<String>> tree = builder0.build(treeClass);

        // Begin iterating over the tree
        final Iterator<MutableNode<String>> iterator = trees.treeIterators().preOrderIterator(tree);
        final MutableNode<String> root = iterator.next();
        assertEquals("a", root.getElement());
        assertEquals("b", iterator.next().getElement());

        // Get the left and right nodes of the root, remove them and add a new child
        final Iterator<? extends MutableNode<String>> childIterator = root.childIterator();
        final MutableNode<String> left = childIterator.next();
        final MutableNode<String> right = childIterator.next();
        root.removeChild(right);
        root.removeChild(left);
        root.addChild("g");

        final Iterator<? extends MutableNode<String>> grandchildIterator = right.childIterator();
        final MutableNode<String> grandchild = grandchildIterator.next();
        right.removeChild(grandchild);

        // A new preorder iterator sees the new state of the tree
        final Iterator<MutableNode<String>> newIterator = trees.treeIterators().preOrderIterator(tree);
        assertEquals("a", newIterator.next().getElement());
        assertEquals("g", newIterator.next().getElement());
        assertFalse(newIterator.hasNext());

        // The old preorder iterator sees the previous state of the tree
        assertEquals("c", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertEquals("e", iterator.next().getElement());
        assertEquals("f", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NullPointerException.class)
    public void addNull() {
        final TopDownTreeRootBuilder<String> rootBuilder = trees.treeBuilders().topDownBuilder();
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String> builder = rootBuilder.root("a");
        final MutableTree<String, MutableNode<String>> tree = builder.build(treeClass);
        tree.getRoot().addChild(null);
    }

    @Test
    public void remove() {
        final TopDownTreeRootBuilder<String> rootBuilder = trees.treeBuilders().topDownBuilder();
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String> builder = rootBuilder.root("a");
        final MutableTree<String, MutableNode<String>> tree = builder.build(treeClass);
        final MutableNode<String> child = tree.getRoot().addChild("b");
        assertTrue(tree.getRoot().removeChild(child));
        assertEquals(0, tree.getRoot().getNumberOfChildren());
    }
}
