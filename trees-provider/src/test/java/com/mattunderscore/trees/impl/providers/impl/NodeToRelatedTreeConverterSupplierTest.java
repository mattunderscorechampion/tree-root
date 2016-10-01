package com.mattunderscore.trees.impl.providers.impl;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.impl.TreeBuilderFactoryImpl;

/**
 * Unit tests for {@link NodeToRelatedTreeConverterProvider}.
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
        final KeyMappingProvider keyMappingProvider = KeyMappingProvider.get();
        final NodeToRelatedTreeConverterProvider supplier = new NodeToRelatedTreeConverterProvider(
            keyMappingProvider,
            new TreeBuilderFactoryImpl(
                keyMappingProvider,
                new TreeConstructorProvider(keyMappingProvider),
                new EmptyTreeConstructorProvider(keyMappingProvider),
                new TreeConverterProvider(keyMappingProvider),
                new EmptySortedTreeConstructorProviderImpl(keyMappingProvider)));

        supplier.get(node);
    }
}
