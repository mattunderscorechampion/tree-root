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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.impl.providers.impl.EmptyTreeConstructorProvider;
import com.mattunderscore.trees.impl.providers.impl.KeyMappingProvider;
import com.mattunderscore.trees.impl.providers.impl.TreeConverterProvider;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;

/**
 * @author Matt Champion on 15/08/14.
 */
public class TopDownTreeBuilderImplTest {
    private static final KeyMappingProvider KEY_MAPPING_PROVIDER = KeyMappingProvider.get();
    private static final EmptyTreeConstructorProvider EMPTY_TREE_CONSTRUCTOR_PROVIDER =
        new EmptyTreeConstructorProvider(KEY_MAPPING_PROVIDER);
    private static final TreeConverterProvider TREE_CONVERTER_PROVIDER = new TreeConverterProvider(KEY_MAPPING_PROVIDER);

    @Test
    public void buildEmpty() {
        final TopDownTreeRootBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new TopDownTreeRootBuilderImpl<>(TREE_CONVERTER_PROVIDER, EMPTY_TREE_CONSTRUCTOR_PROVIDER);
        final LinkedTree<String> tree = builder.build(LinkedTree.<String>typeKey());
        assertNull(tree.getRoot());
        assertTrue(tree.isEmpty());
    }

    @Test
    public void buildLeaf() {
        final TopDownTreeRootBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new TopDownTreeRootBuilderImpl<>(TREE_CONVERTER_PROVIDER, EMPTY_TREE_CONSTRUCTOR_PROVIDER);
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.root("ROOT");

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(0, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void buildSimple() {
        final TopDownTreeRootBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new TopDownTreeRootBuilderImpl<>(TREE_CONVERTER_PROVIDER, EMPTY_TREE_CONSTRUCTOR_PROVIDER);
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.root("ROOT");
        builder0.addChild("a");
        builder0.addChild("b");

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(2, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }
}
