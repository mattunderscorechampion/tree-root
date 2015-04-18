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

package com.mattunderscore.trees.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.matchers.AlwaysMatcher;
import com.mattunderscore.trees.matchers.EqualityMatcher;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for NodeSelectorFactoryImpl.
 * @author Matt Champion on 20/12/14
 */
public final class NodeSelectorFactoryImplTest {
    private static NodeSelectorFactory factory;
    private static Tree<String, Node<String>> tree;

    @BeforeClass
    public static void setUpClass() {
        final Trees trees = new TreesImpl();
        factory = trees.nodeSelectors();
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("a",
            builder.create("b"),
            builder.create("c")).build(LinkedTree.class);
    }

    @Test
    public void selectorFromMatcher() {
        final NodeSelector<String> selector = factory.newSelector(new AlwaysMatcher<String>());
        final Iterator<Node<String>> iterator = selector.select(tree);
        final Node<String> selectedNode = iterator.next();
        assertEquals("a", selectedNode.getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void selectorFromSelectorAndMatcher() {
        final NodeSelector<String> selector0 = factory.newSelector(new AlwaysMatcher<String>());
        final NodeSelector<String> selector1 = factory.newSelector(selector0, new AlwaysMatcher<String>());
        final Iterator<Node<String>> iterator = selector1.select(tree);
        final Node<String> selectedNode0 = iterator.next();
        assertEquals("b", selectedNode0.getElement());
        final Node<String> selectedNode1 = iterator.next();
        assertEquals("c", selectedNode1.getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void selectorFromSelectorAndSelector() {
        final NodeSelector<String> selector0 = factory.newSelector(new AlwaysMatcher<String>());
        final NodeSelector<String> selector1 = factory.newSelector(new EqualityMatcher<String>("b"));
        final NodeSelector<String> selector2 = factory.newSelector(selector0, selector1);
        final Iterator<Node<String>> iterator = selector2.select(tree);
        final Node<String> selectedNode0 = iterator.next();
        assertEquals("b", selectedNode0.getElement());
        assertFalse(iterator.hasNext());
    }
}
