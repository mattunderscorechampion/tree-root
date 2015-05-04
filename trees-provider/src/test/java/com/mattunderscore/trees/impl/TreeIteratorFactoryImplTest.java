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

package com.mattunderscore.trees.impl;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;

/**
 * Tests for {@link TreeIteratorFactoryImpl}.
 * @author Matt Champion on 04/05/15
 */
public final class TreeIteratorFactoryImplTest {
      private static TreeIteratorFactory iteratorFactory;
      private static LinkedTree<String> tree;

      @BeforeClass
      public static void setUp() {
            final Trees trees = new TreesImpl();
            iteratorFactory = trees.treeIterators();
            final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
            tree = builder.create("root", builder.create("a", builder.create("c")), builder.create("b"))
                .build(LinkedTree.<String>typeKey());
      }

      @Test
      public void preOrderIterator() {
            final Iterator<LinkedTree<String>> iterator = iteratorFactory.preOrderIterator(tree);
            assertEquals("root", iterator.next().getElement());
            assertEquals("a", iterator.next().getElement());
            assertEquals("c", iterator.next().getElement());
            assertEquals("b", iterator.next().getElement());
      }

      @Test
      public void preOrderElementsIterator() {
            final Iterator<String> iterator = iteratorFactory.preOrderElementsIterator(tree);
            assertEquals("root", iterator.next());
            assertEquals("a", iterator.next());
            assertEquals("c", iterator.next());
            assertEquals("b", iterator.next());
      }

      @Test
      public void postOrderIterator() {
            final Iterator<LinkedTree<String>> iterator = iteratorFactory.postOrderIterator(tree);
            assertEquals("c", iterator.next().getElement());
            assertEquals("a", iterator.next().getElement());
            assertEquals("b", iterator.next().getElement());
            assertEquals("root", iterator.next().getElement());
      }

      @Test
      public void postOrderElementsIterator() {
            final Iterator<String> iterator = iteratorFactory.postOrderElementsIterator(tree);
            assertEquals("c", iterator.next());
            assertEquals("a", iterator.next());
            assertEquals("b", iterator.next());
            assertEquals("root", iterator.next());
      }

      @Test
      public void inOrderIterator() {
            final Iterator<LinkedTree<String>> iterator = iteratorFactory.inOrderIterator(tree);
            assertEquals("c", iterator.next().getElement());
            assertEquals("a", iterator.next().getElement());
            assertEquals("root", iterator.next().getElement());
            assertEquals("b", iterator.next().getElement());
      }

      @Test
      public void inOrderElementsIterator() {
            final Iterator<String> iterator = iteratorFactory.inOrderElementsIterator(tree);
            assertEquals("c", iterator.next());
            assertEquals("a", iterator.next());
            assertEquals("root", iterator.next());
            assertEquals("b", iterator.next());
      }

      @Test
      public void breadthFirstIterator() {
            final Iterator<LinkedTree<String>> iterator = iteratorFactory.breadthFirstIterator(tree);
            assertEquals("root", iterator.next().getElement());
            assertEquals("a", iterator.next().getElement());
            assertEquals("b", iterator.next().getElement());
            assertEquals("c", iterator.next().getElement());
      }

      @Test
      public void breadthFirstElementsIterator() {
            final Iterator<String> iterator = iteratorFactory.breadthFirstElementsIterator(tree);
            assertEquals("root", iterator.next());
            assertEquals("a", iterator.next());
            assertEquals("b", iterator.next());
            assertEquals("c", iterator.next());
      }
}
