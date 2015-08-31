package com.mattunderscore.trees.walkers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.immutable.TreeNodeImpl;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link LongSummingWalker}.
 *
 * @author Matt Champion on 31/08/2015
 */
public final class LongSummingWalkerTest {
    private static final Trees TREES = Trees.get();
    private Tree<Long, Node<Long>> tree;

    @Before
    public void setUp() {
        final BottomUpTreeBuilder<Long, Node<Long>> builder = TREES.treeBuilders().bottomUpBuilder();
        tree = builder.create(
            5L,
            builder.create(3L),
            builder.create(
                3L,
                builder.create(
                    7L,
                    builder.create(9L))))
            .build(TreeNodeImpl.<Long>typeKey());
    }

    @Test
    public void sum() {
        final SummingWalker<Long> walker = new LongSummingWalker();
        TREES
            .treeWalkers()
            .walkElementsInOrder(tree, walker);

        assertEquals(27L, walker.getValue().longValue());
    }
}
