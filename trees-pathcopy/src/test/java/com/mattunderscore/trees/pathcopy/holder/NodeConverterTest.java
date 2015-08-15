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

package com.mattunderscore.trees.pathcopy.holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.impl.TreeBuilderFactoryImpl;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.impl.suppliers.impl.EmptySortedTreeConstructorSupplierImpl;
import com.mattunderscore.trees.impl.suppliers.impl.EmptyTreeConstructorSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.KeyMappingSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.TreeConstructorSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.TreeConverterSupplier;
import com.mattunderscore.trees.mutable.MutableNode;

/**
 * Test for {@link NodeConverter}.
 * @author Matt Champion on 04/05/15
 */
public final class NodeConverterTest {
    private static final Trees trees = Trees.get();

    @SuppressWarnings("unchecked")
    @Test
    public void convertToSelf() {
        final BottomUpTreeBuilder<String, MutableNode<String>> builder = trees.treeBuilders().bottomUpBuilder();
        final PathCopyTree<String> tree = builder.create("a", builder.create("b"), builder.create("c")).build(PathCopyTree.typeKey());

        final KeyMappingSupplier keyMappingSupplier = new KeyMappingSupplier();
        final NodeConverter<String> converter = new NodeConverter<>();
        converter.setTreeBuilderFactory(new TreeBuilderFactoryImpl(
            keyMappingSupplier,
            new TreeConstructorSupplier(keyMappingSupplier),
            new EmptyTreeConstructorSupplier(keyMappingSupplier),
            new TreeConverterSupplier(keyMappingSupplier),
            new EmptySortedTreeConstructorSupplierImpl(keyMappingSupplier)));

        final PathCopyTree<String> convertedTree =
            converter.treeFromRootNode(tree.getRoot());

        final PathCopyNode<String> convertedRoot = convertedTree.getRoot();
        assertEquals(2, convertedRoot.getNumberOfChildren());
        final Iterator<? extends MutableNode<String>> iterator = convertedRoot.childIterator();
        assertTrue(iterator.hasNext());
        final MutableNode<String> child0 = iterator.next();
        assertEquals("b", child0.getElement());

        assertTrue(iterator.hasNext());
        final MutableNode<String> child1 = iterator.next();
        assertEquals("c", child1.getElement());
        assertFalse(iterator.hasNext());
    }
}
