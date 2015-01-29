package com.mattunderscore.trees.binary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.binary.search.BinarySearchTreeSortingAlgorithm;
import com.mattunderscore.trees.common.LinkedTree;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.utilities.ComparableComparator;

/**
 * @author Matt Champion on 29/01/15
 */
public final class BinarySearchTreeSortingAlgorthimTest {
    private final BinarySearchTreeSortingAlgorithm algorithm = new BinarySearchTreeSortingAlgorithm();
    private LinkedTree<Integer> tree;

    @Before
    public void setUp() {
        final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
        final Trees trees = serviceLoader.iterator().next();
        tree = trees.treeBuilders().<Integer>topDownBuilder().build(LinkedTree.<Integer>typeKey());
    }

    @Test
    public void addToEmpty() {
        algorithm.addNewElement(new ComparableComparator<Integer>(), tree, 2);

        assertEquals(new Integer(2), tree.getRoot().getElement());
    }

    @Ignore("Not currently implemented")
    @Test
    public void addSmaller() {
        final ComparableComparator<Integer> comparator = new ComparableComparator<>();
        algorithm.addNewElement(comparator, tree, 2);
        algorithm.addNewElement(comparator, tree, 1);

        assertEquals(new Integer(2), tree.getRoot().getElement());
        final Iterator<? extends Node<Integer>> iterator = tree.getRoot().getChildren().structuralIterator();
        assertEquals(new Integer(1), iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Ignore("Not currently implemented")
    @Test
    public void addLarger() {
        final ComparableComparator<Integer> comparator = new ComparableComparator<>();
        algorithm.addNewElement(comparator, tree, 2);
        algorithm.addNewElement(comparator, tree, 3);

        assertEquals(new Integer(2), tree.getRoot().getElement());
        final Iterator<? extends Node<Integer>> iterator = tree.getRoot().getChildren().structuralIterator();
        assertNull(iterator.next());
        assertEquals(new Integer(3), iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Ignore("Not currently implemented")
    @Test
    public void addLargerThenSmaller() {
        final ComparableComparator<Integer> comparator = new ComparableComparator<>();
        algorithm.addNewElement(comparator, tree, 2);
        algorithm.addNewElement(comparator, tree, 3);

        assertEquals(new Integer(2), tree.getRoot().getElement());
        final Iterator<? extends Node<Integer>> iterator = tree.getRoot().getChildren().structuralIterator();
        assertEquals(new Integer(1), iterator.next().getElement());
        assertEquals(new Integer(3), iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }
}
