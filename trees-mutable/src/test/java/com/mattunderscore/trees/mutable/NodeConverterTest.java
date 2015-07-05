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

package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.impl.SPISupportImpl;
import com.mattunderscore.trees.impl.TreesImpl;

/**
 * Test for {@link NodeConverter}.
 * @author Matt Champion on 04/05/15
 */
public final class NodeConverterTest {
      private static final Trees trees = new TreesImpl();

      @Test
      public void convertToSelf() {
            final TopDownTreeRootBuilder<String, MutableSettableNode<String>> builder = trees.treeBuilders().topDownBuilder();
            final MutableTree<String, MutableSettableNode<String>> tree = builder.root("a").build(MutableTreeImpl.class);
            final MutableSettableNode<String> root = tree.getRoot();
            assertTrue(root.isLeaf());
            final MutableSettableNode<String> depth1 = root.addChild("b");
            assertFalse(root.isLeaf());
            depth1.addChild("c");

            final NodeConverter<String> converter = new NodeConverter<>();
            converter.setSupport(new SPISupportImpl());

            final MutableTree<String, MutableSettableNode<String>> convertedTree =
                converter.treeFromRootNode(root);

            final MutableSettableNode<String> convertedRoot = convertedTree.getRoot();
            assertEquals(1, convertedRoot.getNumberOfChildren());
            final Iterator<? extends MutableSettableNode<String>> iterator0 = convertedRoot.childIterator();
            assertTrue(iterator0.hasNext());
            final MutableSettableNode<String> child0 = iterator0.next();
            assertEquals("b", child0.getElement());

            final Iterator<? extends MutableSettableNode<String>> iterator1 = child0.childIterator();
            assertTrue(iterator1.hasNext());
            final MutableSettableNode<String> child1 = iterator1.next();
            assertEquals("c", child1.getElement());
            assertFalse(iterator1.hasNext());

            depth1.addChild("d");
            final Iterator<? extends MutableSettableNode<String>> iterator2 = child0.childIterator();
            assertTrue(iterator2.hasNext());
            final MutableSettableNode<String> child2 = iterator2.next();
            assertEquals("c", child2.getElement());
            assertFalse("Change to original tree should not be propagated to the converted tree", iterator2.hasNext());
      }
}
