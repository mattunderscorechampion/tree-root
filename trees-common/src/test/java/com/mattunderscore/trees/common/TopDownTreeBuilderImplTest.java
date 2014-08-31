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

package com.mattunderscore.trees.common;

import com.mattunderscore.trees.TopDownTreeRootBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author matt on 15/08/14.
 */
public class TopDownTreeBuilderImplTest {
    public static final SPISupport helper = new SPISupport();

    @Test
    public void buildEmpty() {
        final TopDownTreeRootBuilderImpl<String> builder = new TopDownTreeRootBuilderImpl<>(helper);
        final LinkedTree<String> tree = builder.build(LinkedTree.class);
        assertNull(tree.getRoot());
        assertTrue(tree.isEmpty());
    }

    @Test
    public void buildLeaf() {
        final TopDownTreeRootBuilderImpl<String> builder = new TopDownTreeRootBuilderImpl<>(helper);
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String> builder0 = builder.root("ROOT");

        final LinkedTree<String> tree = builder0.build(LinkedTree.class);
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(0, tree.getChildren().size());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void buildSimple() {
        final TopDownTreeRootBuilderImpl<String> builder = new TopDownTreeRootBuilderImpl<>(helper);
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String> builder0 = builder.root("ROOT");
        builder0.addChild("a");
        builder0.addChild("b");

        final LinkedTree<String> tree = builder0.build(LinkedTree.class);
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(2, tree.getChildren().size());
        assertFalse(tree.isEmpty());
    }
}
