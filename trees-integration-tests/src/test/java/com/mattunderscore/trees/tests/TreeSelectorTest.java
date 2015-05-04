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

package com.mattunderscore.trees.tests;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.immutable.TreeNodeImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.matchers.EqualityMatcher;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.mutable.MutableTreeImpl;
import com.mattunderscore.trees.pathcopy.holder.PathCopyTree;
import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.selection.TreeSelector;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Tests the application of tree selectors.
 * @author Matt Champion on 29/06/14.
 */
@RunWith(Parameterized.class)
public final class TreeSelectorTest {
    private static final Trees trees = new TreesImpl();
    private final Class<? extends Tree<String, Node<String>>> treeClass;

    public TreeSelectorTest(Class<? extends Tree<String, Node<String>>> treeClass) {
        this.treeClass = treeClass;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {Tree.class}, // 0
            {TreeNodeImpl.class}, // 1, repeats 0 with different key
            {LinkedTree.class}, // 2
            {MutableTree.class}, // 3
            {MutableTreeImpl.class}, // 4, repeats 3 with different key
            {PathCopyTree.class} // 5
        });
    }

    @Test
    public void select() {
        final TopDownTreeRootBuilder<String> builder = trees.treeBuilders().topDownBuilder();
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String> nodeApp0 = builder.root("A");
        nodeApp0.addChild("B");
        nodeApp0.addChild("C");
        final Tree<String, Node<String>> tree = nodeApp0.build(treeClass);

        final TreeSelectorFactory selectorFactory = trees.treeSelectors();
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("A");
        final TreeSelector<String> selector0 = selectorFactory.newSelector(matcher0);
        final Iterator<Tree<String, Node<String>>> treeIterator0 = selector0.select(tree);
        Assert.assertTrue(treeIterator0.hasNext());
        Assert.assertEquals("A", treeIterator0.next().getRoot().getElement());
        Assert.assertFalse(treeIterator0.hasNext());

        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("B");
        final TreeSelector<String> selector1 = selectorFactory.newSelector(selector0, matcher1);
        final Iterator<Tree<String, Node<String>>> treeIterator1 = selector1.select(tree);
        Assert.assertTrue(treeIterator1.hasNext());
        Assert.assertEquals("B", treeIterator1.next().getRoot().getElement());
        Assert.assertFalse(treeIterator1.hasNext());
    }
}
