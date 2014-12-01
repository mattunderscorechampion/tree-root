package com.mattunderscore.trees.common;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.tree.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        final SimpleCollection<? extends MutableNode<String>> children = tree.getRoot().getChildren();
        assertEquals(2, children.size());
        final Iterator<? extends Node<String>> iterator = children.iterator();
        final Node<String> child0 = iterator.next();
        final Node<String> child1 = iterator.next();

        assertTrue(child0.isLeaf());
        assertTrue(child1.isLeaf());

        assertEquals(String.class, child0.getElementClass());
        assertEquals("c", child1.getElement());
    }

    @Test
    public void add() {
        final Node<String> newNode = tree.getRoot().addChild("d");

        final SimpleCollection<? extends MutableNode<String>> children = tree.getRoot().getChildren();
        assertEquals(3, children.size());
        final Iterator<? extends Node<String>> iterator = children.iterator();
        final Node<String> child0 = iterator.next();
        final Node<String> child1 = iterator.next();
        final Node<String> child2 = iterator.next();

        assertEquals("d", child2.getElement());
    }

    @Test
    public void remove() {
        final SimpleCollection<? extends MutableNode<String>> children = tree.getRoot().getChildren();
        assertEquals(2, children.size());
        final Iterator<? extends MutableNode<String>> iterator0 = children.iterator();
        final MutableNode<String> child0 = iterator0.next();
        final MutableNode<String> child1 = iterator0.next();

        assertEquals("b", child0.getElement());
        assertEquals("c", child1.getElement());
        assertFalse(iterator0.hasNext());
        assertTrue(tree.getRoot().removeChild(child0));

        final Iterator<? extends MutableNode<String>> iterator1 = children.iterator();
        final MutableNode<String> child2 = iterator1.next();
        assertEquals("c", child2.getElement());
        assertFalse(iterator1.hasNext());
    }
}