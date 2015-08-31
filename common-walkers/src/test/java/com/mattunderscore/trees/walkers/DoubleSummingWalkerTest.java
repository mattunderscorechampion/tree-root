package com.mattunderscore.trees.walkers;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.immutable.TreeNodeImpl;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link DoubleSummingWalker}.
 *
 * @author Matt Champion on 31/08/2015
 */
public final class DoubleSummingWalkerTest {
    private static final Trees TREES = Trees.get();
    private Tree<Double, Node<Double>> tree;

    @Before
    public void setUp() {
        final BottomUpTreeBuilder<Double, Node<Double>> builder = TREES.treeBuilders().bottomUpBuilder();
        tree = builder.create(
            5.2,
            builder.create(3.4),
            builder.create(
                3.6,
                builder.create(
                    7.1,
                    builder.create(9.6))))
            .build(TreeNodeImpl.<Double>typeKey());
    }

    @Test
    public void sum() {
        final SummingWalker<Double> walker = new DoubleSummingWalker();
        TREES
            .treeWalkers()
            .walkElementsInOrder(tree, walker);

        assertEquals(28.9, walker.getValue().doubleValue(), 0.02);
    }
}
