package com.mattunderscore.trees.common;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.tree.Node;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public final class LinkedTreeTest {
    private static final Trees trees = new TreesImpl();
    private LinkedTree<String> tree;

    @Before
    public void setUp() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("a", builder.create("b"), builder.create("c")).build(LinkedTree.<String>typeKey());
    }

    @Test
    public void structure() {
        assertFalse(tree.isEmpty());
        assertFalse(tree.getRoot().isLeaf());
        final SimpleCollection<? extends Node<String>> children = tree.getRoot().getChildren();
        assertEquals(2, children.size());
        final Iterator<? extends Node<String>> iterator = children.iterator();
        final Node<String> child0 = iterator.next();
        final Node<String> child1 = iterator.next();

        assertTrue(child0.isLeaf());
        assertTrue(child1.isLeaf());

        assertEquals(String.class, child0.getElementClass());
        assertEquals("c", child1.getElement());
    }
}