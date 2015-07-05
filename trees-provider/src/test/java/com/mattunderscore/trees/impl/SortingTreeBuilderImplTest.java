package com.mattunderscore.trees.impl;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.binary.BinaryTreeNode;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.utilities.ComparableComparator;

/**
 * Unit tests for {@link SortingTreeBuilderImpl}.
 * @author Matt Champion on 05/07/2015.
 */
public final class SortingTreeBuilderImplTest {
    @Mock
    private SPISupport helper;
    @Mock
    private TestSortingTree tree;

    @Before
    public void setUp() {
        initMocks(this);

        when(helper.createEmptyTree(TestSortingTree.class)).thenReturn(tree);
        when(helper.createEmptyTree(eq(TestSortingTree.class), isA(Comparator.class))).thenReturn(tree);
    }

    @Test
    public void buildFromClass() {
        final Comparator<Integer> comparator = new ComparableComparator<>();
        final SortingTreeBuilderImpl<Integer, BinaryTreeNode<Integer>> builder =
            new SortingTreeBuilderImpl<>(helper, comparator);
        builder
            .addElement(5)
            .addElement(2)
            .addElement(3)
            .build(TestSortingTree.class);

        verify(helper).createEmptyTree(TestSortingTree.class, comparator);
        verify(tree).addElement(5);
        verify(tree).addElement(2);
        verify(tree).addElement(3);
    }

    @Test
    public void buildFromKey() {
        final Comparator<Integer> comparator = new ComparableComparator<>();
        final SortingTreeBuilderImpl<Integer, BinaryTreeNode<Integer>> builder =
            new SortingTreeBuilderImpl<>(helper, comparator);
        builder
            .addElement(5)
            .addElement(2)
            .addElement(3)
            .build(new TypeKey<TestSortingTree>() {});

        verify(helper).createEmptyTree(TestSortingTree.class, comparator);
        verify(tree).addElement(5);
        verify(tree).addElement(2);
        verify(tree).addElement(3);
    }

}
