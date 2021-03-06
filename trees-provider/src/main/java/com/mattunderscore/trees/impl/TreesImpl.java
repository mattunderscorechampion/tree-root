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
import com.mattunderscore.tree.root.querying.QuerierImpl;
import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.impl.providers.impl.EmptySortedTreeConstructorProviderImpl;
import com.mattunderscore.trees.impl.providers.impl.EmptyTreeConstructorProvider;
import com.mattunderscore.trees.impl.providers.impl.IteratorRemoveHandlerProvider;
import com.mattunderscore.trees.impl.providers.impl.KeyMappingProvider;
import com.mattunderscore.trees.impl.providers.impl.NodeToRelatedTreeConverterProvider;
import com.mattunderscore.trees.impl.providers.impl.RootReferenceFactoryProviderImpl;
import com.mattunderscore.trees.impl.providers.impl.RotatorProviderImpl;
import com.mattunderscore.trees.impl.providers.impl.TreeConstructorProvider;
import com.mattunderscore.trees.impl.providers.impl.TreeConverterProvider;
import com.mattunderscore.trees.query.Querier;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.spi.TreeImplementation;
import com.mattunderscore.trees.transformation.TreeTransformer;
import com.mattunderscore.trees.traversal.NodeStreamFactory;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;

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
    private final Querier querier;

    public TreesImpl() {
        final KeyMappingProvider keyMappingProvider = KeyMappingProvider.get();
        nodeSelectorFactory = new NodeSelectorFactoryImpl();
        treeWalkerFactory = new TreeWalkerFactoryImpl();
        treeIteratorFactory = new TreeIteratorFactoryImpl(
            new IteratorRemoveHandlerProvider(keyMappingProvider));
        treeBuilderFactory = new TreeBuilderFactoryImpl(
            keyMappingProvider,
            new TreeConstructorProvider(keyMappingProvider),
            new EmptyTreeConstructorProvider(keyMappingProvider),
            new TreeConverterProvider(keyMappingProvider),
            new EmptySortedTreeConstructorProviderImpl(keyMappingProvider));
        treeSelectorFactory = new TreeSelectorFactoryImpl(
            new NodeToRelatedTreeConverterProvider(keyMappingProvider, treeBuilderFactory));
        nodeStreamFactory = new NodeStreamFactoryImpl(treeIteratorFactory);
        transformations = new TreeTransformerImpl(
            new RootReferenceFactoryProviderImpl(keyMappingProvider),
            new RotatorProviderImpl(keyMappingProvider));
        querier = new QuerierImpl();
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
    public Querier querier() {
        return querier;
    }

    @Override
    public SimpleCollection<Class<?>> availableTreeImplementations() {
        final ServiceLoader<TreeImplementation> loader = ServiceLoader.load(TreeImplementation.class);
        final ArrayListSimpleCollection<Class<?>> classes = new ArrayListSimpleCollection<>();
        loader.forEach(i -> classes.add(i.forClass()));
        return classes;
    }
}
