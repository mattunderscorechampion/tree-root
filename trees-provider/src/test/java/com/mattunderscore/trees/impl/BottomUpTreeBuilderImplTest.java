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

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.impl.providers.impl.EmptyTreeConstructorProvider;
import com.mattunderscore.trees.impl.providers.impl.KeyMappingProvider;
import com.mattunderscore.trees.impl.providers.impl.TreeConstructorProvider;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * @author Matt Champion on 13/08/14.
 */
public final class BottomUpTreeBuilderImplTest {
    private static final KeyMappingProvider KEY_MAPPING_PROVIDER = KeyMappingProvider.get();
    private static final TreeConstructorProvider treeConstructorProvider = new TreeConstructorProvider(KEY_MAPPING_PROVIDER);
    private static final EmptyTreeConstructorProvider emptyTreeConstructorProvider =
        new EmptyTreeConstructorProvider(KEY_MAPPING_PROVIDER);

    @Test
    public void buildEmpty() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final LinkedTree<String> tree = builder.build(LinkedTree.<String>typeKey());
        assertNull(tree.getRoot());
        assertTrue(tree.isEmpty());
    }

    @Test
    public void buildLeaf() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT");

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(0, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void buildSimple() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT",
            builder.create("a"),
            builder.create("b"));

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(2, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void buildComplex() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT",
            builder.create("a",
                builder.create("+",
                    builder.create("fah",
                        builder.create("I'm falling")))),
                builder.create("b",
                    builder.create("-"),
                    builder.create("!"),
                    builder.create("t",
                        builder.create("x",
                            builder.create("7")))));

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(2, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void failToBuild() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT");
        builder0.build(new TypeKey<FakeTree>() {});
    }

    @Test
    public void build1Child() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT",
            builder.create("a"));

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(1, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void build2Child() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT",
            builder.create("a"),
            builder.create("b"));

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(2, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void build3Child() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT",
            builder.create("a"),
            builder.create("b"),
            builder.create("c"));

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(3, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void build4Child() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT",
            builder.create("a"),
            builder.create("b"),
            builder.create("c"),
            builder.create("d"));

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(4, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void build5Child() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT",
            builder.create("a"),
            builder.create("b"),
            builder.create("c"),
            builder.create("d"),
            builder.create("e"));

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(5, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void build6Child() {
        final BottomUpTreeBuilderImpl<String, MutableSettableStructuredNode<String>> builder =
            new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, KEY_MAPPING_PROVIDER);
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder0 = builder.create("ROOT",
            builder.create("a"),
            builder.create("b"),
            builder.create("c"),
            builder.create("d"),
            builder.create("e"),
            builder.create("f"));

        final LinkedTree<String> tree = builder0.build(LinkedTree.<String>typeKey());
        assertEquals("ROOT", tree.getRoot().getElement());
        assertEquals(6, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    public interface FakeTree extends Tree<String, MutableSettableStructuredNode<String>> {
    }
}
