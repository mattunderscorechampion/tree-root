/* Copyright © 2016 Matthew Champion
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

package com.mattunderscore.trees.tests;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.NodeAppender;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.immutable.TreeNodeImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.mutable.MutableTreeImpl;
import com.mattunderscore.trees.pathcopy.holder.PathCopyTree;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Test for copying trees.
 * @author Matt Champion on 29/09/16
 */
@RunWith(Parameterized.class)
public final class TreeCopyTest {
    private static final Trees trees = Trees.get();
    private final Class<Tree<String, Node<String>>> treeClass0;
    private final Class<Tree<String, Node<String>>> treeClass1;

    private Tree<String, Node<String>> testTree;

    public TreeCopyTest(
            Class<Tree<String, Node<String>>> treeClass0,
            Class<Tree<String, Node<String>>> treeClass1) {
        this.treeClass0 = treeClass0;
        this.treeClass1 = treeClass1;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        final Collection<Class<?>> treeClasses = Arrays.asList(Tree.class, // 0
                TreeNodeImpl.class, // 1, repeats 0 with different key
                LinkedTree.class, // 2
                MutableTree.class, // 3
                MutableTreeImpl.class, // 4, repeats 3 with different key
                PathCopyTree.class // 5
        );
        final List<Object[]> classPairs = new ArrayList<>();
        for (Class<?> treeClass0 : treeClasses) {
            for (Class<?> treeClass1 : treeClasses) {
                classPairs.add(new Object[] { treeClass0, treeClass1 });
            }
        }

        return classPairs;
    }

    @Before
    @SuppressWarnings("unchecked")
    public void testTree() {
        final TopDownTreeRootBuilder<String, Node<String>> builder = trees.treeBuilders().topDownBuilder();
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String, Node<String>> nodeApp0 = builder.root("a");
        final NodeAppender<String, ? extends NodeAppender<String, ?>> nodeApp1 = nodeApp0.addChild("b");
        nodeApp1.addChild("c");
        nodeApp0.addChild("d");
        testTree = nodeApp0.build(treeClass0);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void copyTest() {
        final Tree<String, Node<String>> copiedTree = trees.treeBuilders().copy(testTree, treeClass1);
        assertNotSame(copiedTree, testTree);

        final OpenNode<String, ?> root = copiedTree.getRoot();
        assertEquals("a", root.getElement());
        assertEquals(2, root.getNumberOfChildren());
        final Iterator<?> childIterator = root.childIterator();
        final OpenNode<String, ?> child0 = (OpenNode<String, ?>) childIterator.next();
        final OpenNode<String, ?> child1 = (OpenNode<String, ?>) childIterator.next();

        assertEquals("b", child0.getElement());
        assertEquals(1, child0.getNumberOfChildren());

        assertEquals("d", child1.getElement());
        assertEquals(0, child1.getNumberOfChildren());

        final OpenNode<String, ?> child2 = child0.childIterator().next();
        assertEquals("c", child2.getElement());
        assertEquals(0, child2.getNumberOfChildren());
    }
}
