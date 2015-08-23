package com.mattunderscore.trees.impl.suppliers.impl;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.impl.TreeBuilderFactoryImpl;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link NodeToRelatedTreeConverterSupplier}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class NodeToRelatedTreeConverterSupplierTest {
    @Mock
    private MutableBinaryTreeNode<String> node;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void get() {
        final KeyMappingSupplier keyMappingSupplier = new KeyMappingSupplier();
        final NodeToRelatedTreeConverterSupplier supplier = new NodeToRelatedTreeConverterSupplier(
            new KeyMappingSupplier(),
            new TreeBuilderFactoryImpl(
                keyMappingSupplier,
                new TreeConstructorSupplier(keyMappingSupplier),
                new EmptyTreeConstructorSupplier(keyMappingSupplier),
                new TreeConverterSupplier(keyMappingSupplier),
                new EmptySortedTreeConstructorSupplierImpl(keyMappingSupplier)));

        supplier.get(node);
    }
}
