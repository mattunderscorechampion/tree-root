package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.common.LinkedTree;
import com.mattunderscore.trees.common.TreesImpl;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.internal.pathcopy.holder.PathCopyTree;
import com.mattunderscore.trees.internal.pathcopy.simple.SimplePathCopyTree;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for bottom up builders.
 * Builds a tree using the bottom up tree builder and verifies its structure.
 * @author Matt Champion
 */
@RunWith(Parameterized.class)
public class StringTreeBottomUpBuilderTest {
    private static final Trees trees = new TreesImpl();
    private final Class<? extends Tree<String, Node<String>>> treeClass;

    public StringTreeBottomUpBuilderTest(Class<? extends Tree<String, Node<String>>> treeClass) {
        this.treeClass = treeClass;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {Tree.class}, // 0
            {LinkedTree.class}, // 1
            {MutableTree.class}, // 2
            {PathCopyTree.class}, // 3
            {SimplePathCopyTree.class} // 4
        });
    }

    @Test
    public void build() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final Tree<String, Node<String>> tree = builder.create("a",
            builder.create("b",
                builder.create("c")),
            builder.create("d")).build(treeClass);

        final Node<String> root = tree.getRoot();
        assertEquals(String.class, root.getElementClass());
        assertEquals("a", root.getElement());
        final SimpleCollection<? extends Node<String>> children0 = root.getChildren();
        final Iterator<? extends Node<String>> iterator0 = children0.iterator();
        assertEquals(2, children0.size());
        assertEquals("a", root.getElement());
        assertTrue(iterator0.hasNext());
        final Node<String> bNode = iterator0.next();
        assertTrue(iterator0.hasNext());
        final Node<String> dNode = iterator0.next();
        assertFalse(iterator0.hasNext());
        assertEquals("b", bNode.getElement());
        assertEquals("d", dNode.getElement());
        final SimpleCollection<? extends Node<String>> children1 = bNode.getChildren();
        final Iterator<? extends Node<String>> iterator1 = children1.iterator();
        assertTrue(iterator1.hasNext());
        final Node<String> cNode = iterator1.next();
        assertFalse(iterator1.hasNext());
        assertEquals("c", cNode.getElement());
    }
}
