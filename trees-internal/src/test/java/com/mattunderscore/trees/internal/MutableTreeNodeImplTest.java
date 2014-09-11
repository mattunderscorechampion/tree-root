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
import com.mattunderscore.trees.common.traversers.PreOrderIterator;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Matt Champion on 15/07/14.
 */
public final class MutableTreeNodeImplTest {
    private static final Trees trees = new TreesImpl();

    @Test
    public void mutateTree() {
        final TopDownTreeRootBuilder<String> builder = trees.treeBuilders().topDownBuilder();
        final MutableTree<String, MutableNode<String>> tree = builder.root("a").build(MutableTree.class);
        final MutableNode<String> root = tree.getRoot();
        assertTrue(root.isLeaf());
        final MutableNode<String> depth1 = root.addChild("b");
        assertFalse(root.isLeaf());
        depth1.addChild("c");

        final SimpleCollection<? extends MutableNode<String>> children = root.getChildren();
        assertEquals(1, children.size());
        final Iterator<? extends MutableNode<String>> iterator0 = children.iterator();
        assertTrue(iterator0.hasNext());
        final MutableNode<String> child0 = iterator0.next();
        assertEquals("b", child0.getElement());

        final Iterator<? extends MutableNode<String>> iterator1 = child0.getChildren().iterator();
        assertTrue(iterator1.hasNext());
        final MutableNode<String> child1 = iterator1.next();
        assertEquals("c", child1.getElement());
        assertFalse(iterator1.hasNext());

        depth1.addChild("d");
        final Iterator<? extends MutableNode<String>> iterator2 = child0.getChildren().iterator();
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
        final NodeAppender appender0 = builder0.addChild("b");
        final NodeAppender appender1 = builder0.addChild("e");
        appender0.addChild("c");
        appender0.addChild("d");
        appender1.addChild("f");
        final MutableTree<String, MutableNode<String>> tree = builder0.build(MutableTree.class);

        // Begin iterating over the tree
        final Iterator<MutableNode<String>> iterator = new PreOrderIterator(tree);
        final MutableNode<String> root = iterator.next();
        assertEquals("a", root.getElement());
        assertEquals("b", iterator.next().getElement());

        // Get the left and right nodes of the root, remove them and add a new child
        final Iterator<? extends MutableNode<String>> childIterator = root.getChildren().iterator();
        final MutableNode<String> left = childIterator.next();
        final MutableNode<String> right = childIterator.next();
        root.removeChild(right);
        root.removeChild(left);
        root.addChild("g");

        final Iterator<? extends MutableNode<String>> grandchildIterator = right.getChildren().iterator();
        final MutableNode<String> grandchild = grandchildIterator.next();
        right.removeChild(grandchild);

        // A new preorder iterator sees the new state of the tree
        final Iterator<MutableNode<String>> newIterator = new PreOrderIterator(tree);
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
}
