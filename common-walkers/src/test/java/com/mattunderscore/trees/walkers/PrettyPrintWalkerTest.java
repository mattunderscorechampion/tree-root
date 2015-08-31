package com.mattunderscore.trees.walkers;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.immutable.TreeNodeImpl;
import com.mattunderscore.trees.traversal.TreeWalker;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link PrettyPrintWalker}.
 *
 * @author Matt Champion on 31/08/2015
 */
public final class PrettyPrintWalkerTest {
    private static final Trees TREES = Trees.get();
    private Tree<Integer, Node<Integer>> tree;

    @Before
    public void setUp() {
        final BottomUpTreeBuilder<Integer, Node<Integer>> builder = TREES.treeBuilders().bottomUpBuilder();
        tree = builder.create(
            5,
            builder.create(3),
            builder.create(
                3,
                builder.create(
                    7,
                    builder.create(9))))
            .build(TreeNodeImpl.<Integer>typeKey());
    }

    @Test
    public void sum() throws UnsupportedEncodingException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream(64);
        final TreeWalker<Integer> walker = new PrettyPrintWalker<>(stream);
        TREES
            .treeWalkers()
            .walkElementsPreOrder(tree, walker);

        assertEquals("(5 (3 3 (7 (9))))", stream.toString("UTF-8"));
    }
}
