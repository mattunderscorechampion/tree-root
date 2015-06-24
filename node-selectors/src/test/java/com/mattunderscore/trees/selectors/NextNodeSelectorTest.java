package com.mattunderscore.trees.selectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.matchers.AlwaysMatcher;
import com.mattunderscore.trees.matchers.NeverMatcher;
import com.mattunderscore.trees.mutable.ClosedMutableSettableStructuredNode;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link NextNodeSelector}.
 * @author Matt Champion on 25/12/14
 */
public final class NextNodeSelectorTest {
    private static Tree<String, ClosedMutableSettableStructuredNode<String>> tree;

    @BeforeClass
    public static void setUpClass() {
        final TreeConstructor<String, ClosedMutableSettableStructuredNode<String>, LinkedTree<String>> constructor = new LinkedTree.Constructor<>();
        final Tree<String, ? extends ClosedMutableSettableStructuredNode<String>> aTree = constructor.build(
            "a",
            new LinkedTree[]{
                constructor.build(
                    "b",
                    new LinkedTree[]{}),
                constructor.build(
                    "c",
                    new LinkedTree[]{})});
        tree = (Tree<String, ClosedMutableSettableStructuredNode<String>>) aTree;
    }

    @Test
    public void select() {
        final NodeSelector<String> selector = new NextNodeSelector<>(
            new RootMatcherSelector<>(new AlwaysMatcher<>()),
            new AlwaysMatcher<>());
        final Iterator<? extends ClosedMutableSettableStructuredNode<String>> iterator = selector.select(tree);

        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void selectNothing() {
        final NodeSelector<String> selector = new NextNodeSelector<>(
            new RootMatcherSelector<>(new AlwaysMatcher<>()),
            new NeverMatcher<>());
        final Iterator<? extends ClosedMutableSettableStructuredNode<String>> iterator = selector.select(tree);

        assertFalse(iterator.hasNext());
    }
}
