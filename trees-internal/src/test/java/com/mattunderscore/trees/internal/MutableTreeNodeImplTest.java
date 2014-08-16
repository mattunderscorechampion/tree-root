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

import com.mattunderscore.trees.IMutableNode;
import com.mattunderscore.trees.IMutableTree;
import com.mattunderscore.trees.ITopDownTreeRootBuilder;
import com.mattunderscore.trees.ITrees;
import com.mattunderscore.trees.common.Trees;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author matt on 15/07/14.
 */
public final class MutableTreeNodeImplTest {
    private static final ITrees trees = new Trees();

    @Test
    public void mutateTree() {
        final ITopDownTreeRootBuilder builder = trees.topDownBuilder();
        final IMutableTree<String, IMutableNode<String>> tree = (IMutableTree<String, IMutableNode<String>>)builder.root("a").build(IMutableTree.class);
        final IMutableNode<String> root = tree.getRoot();
        final IMutableNode<String> depth1 = root.addChild("b");
        depth1.addChild("c");

        final Collection<? extends IMutableNode<String>> children = root.getChildren();
        assertEquals(1, children.size());
        final Iterator<? extends IMutableNode<String>> iterator0 = children.iterator();
        assertTrue(iterator0.hasNext());
        final IMutableNode<String> child0 = iterator0.next();
        assertEquals("b", child0.getElement());

        final Iterator<? extends IMutableNode<String>> iterator1 = child0.getChildren().iterator();
        assertTrue(iterator1.hasNext());
        final IMutableNode<String> child1 = iterator1.next();
        assertEquals("c", child1.getElement());
        assertFalse(iterator1.hasNext());

        depth1.addChild("d");
        final Iterator<? extends IMutableNode<String>> iterator2 = child0.getChildren().iterator();
        assertTrue(iterator2.hasNext());
        final IMutableNode<String> child2 = iterator2.next();
        assertEquals("c", child2.getElement());
        assertTrue(iterator2.hasNext());
        final IMutableNode<String> child3 = iterator2.next();
        assertEquals("d", child3.getElement());
        assertFalse(iterator2.hasNext());
    }
}
