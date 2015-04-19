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

package com.mattunderscore.trees.selectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.matchers.AlwaysMatcher;
import com.mattunderscore.trees.matchers.NeverMatcher;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link SelectorNodeSelector}.
 * @author Matt Champion on 19/04/15
 */
public final class SelectorNodeSelectorTest {
      private static Tree<String, Node<String>> tree;

      @BeforeClass
      public static void setUpClass() {
            final TreeConstructor<String, LinkedTree<String>> constructor = new LinkedTree.Constructor<>();
            final Tree<String, ? extends Node<String>> aTree = constructor.build(
                "a",
                new LinkedTree[]{
                    constructor.build(
                        "b",
                        new LinkedTree[]{}),
                    constructor.build(
                        "c",
                        new LinkedTree[]{})});
            tree = (Tree<String, Node<String>>) aTree;
      }

      @Test
      public void select() {
            final NodeSelector<String> selector = new SelectorNodeSelector<>(
                new RootMatcherSelector<>(new AlwaysMatcher<>()),
                new RootMatcherSelector<>(new AlwaysMatcher<>()));
            final Iterator<Node<String>> iterator = selector.select(tree);

            assertTrue(iterator.hasNext());
            assertEquals("b", iterator.next().getElement());
            assertEquals("c", iterator.next().getElement());
            assertFalse(iterator.hasNext());
      }

      @Test
      public void selectNothing() {
            final NodeSelector<String> selector = new SelectorNodeSelector<>(
                new RootMatcherSelector<>(new AlwaysMatcher<>()),
                new RootMatcherSelector<>(new NeverMatcher<>()));
            final Iterator<Node<String>> iterator = selector.select(tree);

            assertFalse(iterator.hasNext());
      }
}