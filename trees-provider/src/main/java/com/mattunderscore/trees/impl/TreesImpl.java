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

import java.util.ServiceLoader;

import com.mattunderscore.simple.collections.ArrayListSimpleCollection;
import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.impl.suppliers.impl.EmptySortedTreeConstructorSupplierImpl;
import com.mattunderscore.trees.impl.suppliers.impl.EmptyTreeConstructorSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.IteratorRemoveHandlerSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.KeyMappingSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.NodeToRelatedTreeConverterSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.RootReferenceFactorySupplier;
import com.mattunderscore.trees.impl.suppliers.impl.RotatorSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.TreeConstructorSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.TreeConverterSupplier;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.spi.TreeImplementation;
import com.mattunderscore.trees.transformation.TreeTransformer;
import com.mattunderscore.trees.traversal.NodeStreamFactory;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation of {@link com.mattunderscore.trees.Trees}.
 * @author Matt Champion on 16/08/14.
 */
public final class TreesImpl implements Trees {
    private final TreeSelectorFactory treeSelectorFactory;
    private final NodeSelectorFactory nodeSelectorFactory;
    private final TreeWalkerFactory treeWalkerFactory;
    private final TreeIteratorFactory treeIteratorFactory;
    private final TreeBuilderFactory treeBuilderFactory;
    private final NodeStreamFactory nodeStreamFactory;
    private final TreeTransformer transformations;

    public TreesImpl() {
        final KeyMappingSupplier keyMappingSupplier = new KeyMappingSupplier();
        nodeSelectorFactory = new NodeSelectorFactoryImpl();
        treeWalkerFactory = new TreeWalkerFactoryImpl();
        treeIteratorFactory = new TreeIteratorFactoryImpl(
            new IteratorRemoveHandlerSupplier(keyMappingSupplier));
        treeBuilderFactory = new TreeBuilderFactoryImpl(
            keyMappingSupplier,
            new TreeConstructorSupplier(keyMappingSupplier),
            new EmptyTreeConstructorSupplier(keyMappingSupplier),
            new TreeConverterSupplier(keyMappingSupplier),
            new EmptySortedTreeConstructorSupplierImpl(keyMappingSupplier));
        treeSelectorFactory = new TreeSelectorFactoryImpl(
            new NodeToRelatedTreeConverterSupplier(keyMappingSupplier, treeBuilderFactory));
        nodeStreamFactory = new NodeStreamFactoryImpl(treeIteratorFactory);
        transformations = new TreeTransformerImpl(
            new RootReferenceFactorySupplier(keyMappingSupplier),
            new RotatorSupplier(keyMappingSupplier));
    }

    @Override
    public TreeSelectorFactory treeSelectors() {
        return treeSelectorFactory;
    }

    @Override
    public NodeSelectorFactory nodeSelectors() {
        return nodeSelectorFactory;
    }

    @Override
    public TreeWalkerFactory treeWalkers() {
        return treeWalkerFactory;
    }

    @Override
    public TreeIteratorFactory treeIterators() {
        return treeIteratorFactory;
    }

    @Override
    public NodeStreamFactory nodeStreams() {
        return nodeStreamFactory;
    }

    @Override
    public TreeBuilderFactory treeBuilders() {
        return treeBuilderFactory;
    }

    @Override
    public TreeTransformer transformations() {
        return transformations;
    }

    @Override
    public SimpleCollection<Class<?>> availableTreeImplementations() {
        final ServiceLoader<TreeImplementation> loader = ServiceLoader.load(TreeImplementation.class);
        final ArrayListSimpleCollection<Class<?>> classes = new ArrayListSimpleCollection<>();
        loader.forEach(i -> classes.add(i.forClass()));
        return classes;
    }
}
