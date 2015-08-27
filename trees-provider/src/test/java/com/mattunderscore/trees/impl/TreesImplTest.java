package com.mattunderscore.trees.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.query.Querier;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.transformation.TreeTransformer;
import com.mattunderscore.trees.traversal.NodeStreamFactory;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;

/**
 * Unit tests for {@link TreesImpl}.
 *
 * @author Matt Champion on 26/07/2015
 */
public final class TreesImplTest {

    @Test
    public void setUp() {
        assertTrue(Trees.get() instanceof TreesImpl);
    }

    @Test
    public void availableTreeImplementations() {
        final Trees trees = Trees.get();
        final SimpleCollection<Class<?>> implementations = trees.availableTreeImplementations();
        assertTrue(implementations.size() == 1);
        final List<Class<?>> classes = new ArrayList<>();
        implementations.forEach(classes::add);
        assertTrue(classes.contains(LinkedTree.class));
    }

    @Test
    public void transformations() {
        final Trees trees = Trees.get();
        final TreeTransformer transformer = trees.transformations();
        assertTrue(transformer instanceof TreeTransformerImpl);
    }

    @Test
    public void builders() {
        final Trees trees = Trees.get();
        final TreeBuilderFactory transformer = trees.treeBuilders();
        assertTrue(transformer instanceof TreeBuilderFactoryImpl);
    }

    @Test
    public void streams() {
        final Trees trees = Trees.get();
        final NodeStreamFactory transformer = trees.nodeStreams();
        assertTrue(transformer instanceof NodeStreamFactoryImpl);
    }

    @Test
    public void iterators() {
        final Trees trees = Trees.get();
        final TreeIteratorFactory iterators = trees.treeIterators();
        assertTrue(iterators instanceof TreeIteratorFactoryImpl);
    }

    @Test
    public void walkers() {
        final Trees trees = Trees.get();
        final TreeWalkerFactory walkers = trees.treeWalkers();
        assertTrue(walkers instanceof TreeWalkerFactoryImpl);
    }

    @Test
    public void nodeSelectors() {
        final Trees trees = Trees.get();
        final NodeSelectorFactory selectors = trees.nodeSelectors();
        assertTrue(selectors instanceof NodeSelectorFactoryImpl);
    }

    @Test
    public void treeSelectors() {
        final Trees trees = Trees.get();
        final TreeSelectorFactory selectors = trees.treeSelectors();
        assertTrue(selectors instanceof TreeSelectorFactoryImpl);
    }

    @Test
    public void querier() {
        final Trees trees = Trees.get();
        final Querier querier = trees.querier();
        assertTrue(querier instanceof QuerierImpl);
    }
}
