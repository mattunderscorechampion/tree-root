package com.mattunderscore.trees.common;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.organised.OrganisedTreeBuilder;
import com.mattunderscore.trees.sorted.SortedTreeBuilder;
import com.mattunderscore.trees.sorted.SortingAlgorithm;
import com.mattunderscore.trees.sorted.SortingTree;
import com.mattunderscore.trees.sorted.SortingTreeBuilder;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.ComparableComparator;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TreeBuilderFactoryImplTest {
    private final static Trees trees = new TreesImpl();

    @Test
    public void testTopDownBuilder() {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final LinkedTree<String> tree = builder.build(LinkedTree.<String>typeKey());
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testBottomUpBuilder() {
        final TopDownTreeRootBuilder<String> builder = trees.treeBuilders().topDownBuilder();
        final LinkedTree<String> tree = builder.build(LinkedTree.<String>typeKey());
        assertTrue(tree.isEmpty());
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void testSortingTreeBuilder() {
        final SortingTreeBuilder<String> builder = trees.treeBuilders()
            .sortingTreeBuilder(new ComparableComparator<String>());
        builder.build(new TypeKey<SortingTree<String, ? extends Node<String>>>() {});
    }

    @Test(expected = UnsupportedOperationException.class) // Sorting algorithms not implemented
    public void testSortedTreeBuilder() {
        final SortedTreeBuilder<String> builder = trees.treeBuilders()
            .sortedTreeBuilder(new ComparableComparator<String>(), new SortingAlgorithm() {
            });
    }
}