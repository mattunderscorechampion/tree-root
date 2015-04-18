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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.matchers.AlwaysMatcher;
import com.mattunderscore.trees.selectors.ChildSelector;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for ChildSelector.
 * @author Matt Champion on 25/12/14
 */
public final class ChildSelectorTest {
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
    public void selectsChildren() {
        final NodeSelector<String> selector = factory.newSelector(new AlwaysMatcher<String>());
        final NodeSelector<String> extendedSelector = new ChildSelector<>(selector);

        final Iterator<Node<String>> iterator = extendedSelector.select(tree);
        Assert.assertEquals("b", iterator.next().getElement());
        Assert.assertEquals("c", iterator.next().getElement());
        Assert.assertFalse(iterator.hasNext());
    }
}
