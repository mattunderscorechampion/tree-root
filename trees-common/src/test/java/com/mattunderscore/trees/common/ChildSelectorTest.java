package com.mattunderscore.trees.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.common.matchers.AlwaysMatcher;
import com.mattunderscore.trees.common.selectors.ChildSelector;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for ChildSelector.
 * @author Matt Champion on 25/12/14
 */
public final class ChildSelectorTest {
    private static NodeSelectorFactory factory;
    private static Tree<String, Node<String>> tree;

    @BeforeClass
    public static void setUpClass() {
        final Trees trees = new TreesImpl();
        factory = trees.nodeSelectors();
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("a",
            builder.create("b"),
            builder.create("c")).build(LinkedTree.class);
    }

    @Test
    public void selectsChildren() {
        final NodeSelector<String> selector = factory.newSelector(new AlwaysMatcher<String>());
        final NodeSelector<String> extendedSelector = new ChildSelector<>(selector);

        final Iterator<Node<String>> iterator = extendedSelector.select(tree);
        assertEquals("b", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }
}
