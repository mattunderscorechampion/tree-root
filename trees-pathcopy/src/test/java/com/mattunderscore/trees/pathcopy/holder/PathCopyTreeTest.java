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

package com.mattunderscore.trees.pathcopy.holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;

/**
 * Tests for path copy tree.
 * @author Matt Champion on 14/11/14.
 */
public final class PathCopyTreeTest {
    private static TreeIteratorFactory iterators;
    private static BottomUpTreeBuilder<String, MutableNode<String>> builder;

    @BeforeClass
    public static void setUp() {
        final Trees trees = new TreesImpl();
        iterators = trees.treeIterators();
        builder = trees.treeBuilders().bottomUpBuilder();
    }

    @Test
    public void typeKeyTest() {
        final TypeKey<PathCopyTree<String>> key = PathCopyTree.<String>typeKey();
        assertEquals(PathCopyTree.class, key.getTreeType());
        assertTrue(key.toString().contains(PathCopyTree.class.getSimpleName()));
    }

    @Test
    public void test() {
        final MutableTree<String, MutableNode<String>> tree = builder.build(PathCopyTree.typeKey());
        final Iterator<String> iterator0 = iterators.inOrderElementsIterator(tree);
        final MutableNode<String> root = tree.setRoot("root");
        Assert.assertFalse(iterator0.hasNext());
        assertEquals(0, root.getNumberOfChildren());

        final Iterator<String> iterator1 = iterators.inOrderElementsIterator(tree);
        root.addChild("child");
        assertEquals(0, root.getNumberOfChildren());
        Assert.assertNotEquals(root, tree.getRoot());
        assertEquals(1, tree.getRoot().getNumberOfChildren());

        Assert.assertTrue(iterator1.hasNext());
        assertEquals("root", iterator1.next());
        Assert.assertFalse(iterator1.hasNext());

        final Iterator<String> iterator2 = iterators.inOrderElementsIterator(tree);
        final MutableNode<String> newRoot = tree.setRoot("newRoot");

        Assert.assertTrue(iterator2.hasNext());
        assertEquals("child", iterator2.next());
        assertEquals("root", iterator2.next());
        Assert.assertFalse(iterator2.hasNext());

        final MutableNode<String> newChild = newRoot.addChild("newChild");
        final Iterator<String> iterator3 = iterators.inOrderElementsIterator(tree);
        Assert.assertTrue(tree.getRoot().removeChild(newChild));
        final Iterator<String> iterator4 = iterators.inOrderElementsIterator(tree);

        Assert.assertTrue(iterator3.hasNext());
        assertEquals("newChild", iterator3.next());
        assertEquals("newRoot", iterator3.next());
        Assert.assertFalse(iterator3.hasNext());
        Assert.assertTrue(iterator4.hasNext());
        assertEquals("newRoot", iterator4.next());
        Assert.assertFalse(iterator4.hasNext());

        Assert.assertFalse(newRoot.removeChild(root));
    }

    @Test
    public void mutateTree() {
        final MutableTree<String, MutableNode<String>> tree = builder.build(PathCopyTree.typeKey());
        tree.setRoot("a");
        final MutableNode<String> root = tree.getRoot();
        Assert.assertTrue(root.isLeaf());
        final MutableNode<String> depth1 = root.addChild("b");
        Assert.assertFalse(tree.getRoot().isLeaf());
        depth1.addChild("c");

        assertEquals(1, tree.getRoot().getNumberOfChildren());
        final Iterator<? extends MutableNode<String>> iterator0 = tree.getRoot().childIterator();
        Assert.assertTrue(iterator0.hasNext());
        final MutableNode<String> child0 = iterator0.next();
        assertEquals("b", child0.getElement());

        final Iterator<? extends MutableNode<String>> iterator1 = child0.childIterator();
        Assert.assertTrue(iterator1.hasNext());
        final MutableNode<String> child1 = iterator1.next();
        assertEquals("c", child1.getElement());
        Assert.assertFalse(iterator1.hasNext());

        depth1.addChild("d");

        final Iterator<? extends MutableNode<String>> iterator2 = child0.childIterator();
        Assert.assertTrue(iterator2.hasNext());
        final MutableNode<String> child2 = iterator2.next();
        assertEquals("c", child2.getElement());
        Assert.assertFalse(iterator2.hasNext());

        final Iterator<? extends MutableNode<String>> iterator3 = tree.getRoot().childIterator().next().childIterator();
        Assert.assertTrue(iterator3.hasNext());
        final MutableNode<String> child4 = iterator3.next();
        assertEquals("c", child4.getElement());
        Assert.assertTrue(iterator3.hasNext());
        final MutableNode<String> child5 = iterator3.next();
        assertEquals("d", child5.getElement());
        Assert.assertFalse(iterator3.hasNext());
    }

    @Test
    public void mergedMutations() {
        final MutableTree<String, MutableNode<String>> tree = builder.build(PathCopyTree.typeKey());
        tree.setRoot("a");
        final MutableNode<String> root = tree.getRoot();
        final MutableNode<String> child0 = root.addChild("b");
        root.addChild("c");

        assertEquals(0, root.getNumberOfChildren());
        assertEquals(2, tree.getRoot().getNumberOfChildren());

        child0.addChild("d");
        assertEquals(0, child0.getNumberOfChildren());

        final MutableNode<String> childB = tree.getRoot().childIterator().next();
        assertEquals(1, childB.getNumberOfChildren());
        assertEquals("d", childB.childIterator().next().getElement());
    }

    @Test
    public void noRevertOfSetRoot() {
        final MutableTree<String, MutableNode<String>> tree = builder.build(PathCopyTree.typeKey());
        final MutableNode<String> rootA = tree.setRoot("a");
        tree.setRoot("root");
        rootA.addChild("b");
        final MutableNode<String> currentRoot = tree.getRoot();
        assertEquals("root", currentRoot.getElement());
    }

    @Test
    public void unmergeableChanges() {
        final MutableTree<String, MutableNode<String>> tree = builder.build(PathCopyTree.<String>typeKey());
        final MutableNode<String> child = tree.setRoot("a").addChild("b");
        Assert.assertTrue(tree.getRoot().removeChild(child));
        final MutableNode<String> grandchild = child.addChild("c");
        assertEquals("c", grandchild.getElement());

        final MutableNode<String> root = tree.getRoot();
        assertEquals("a", root.getElement());
        assertEquals(0, root.getNumberOfChildren());
    }

    @Test
    public void removalsAreSuccessful() {
        final MutableTree<String, MutableNode<String>> tree = builder.build(PathCopyTree.<String>typeKey());
        final MutableNode<String> grandchild = tree.setRoot("a").addChild("b").addChild("c");
        final MutableNode<String> child = tree.getRoot().childIterator().next();

        Assert.assertTrue(tree.getRoot().removeChild(child));
        Assert.assertTrue(child.removeChild(grandchild));
    }

    @Ignore("Adding a new child should not prevent the removal of the now stale view")
    @Test
    public void removalsWorkWithStaleViews() {
        final MutableTree<String, MutableNode<String>> tree = builder.build(PathCopyTree.<String>typeKey());
        final MutableNode<String> child = tree.setRoot("a").addChild("b");
        child.addChild("c");

        Assert.assertTrue(tree.getRoot().removeChild(child));
    }
}
