package com.mattunderscore.trees.impl.suppliers.impl;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;

/**
 * Unit tests for {@link RootReferenceFactorySupplier}.
 *
 * @author Matt Champion on 22/08/2015
 */
public final class RootReferenceFactorySupplierTest {
    @Mock
    private MutableBinaryTreeNode<String> node;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void noneFound() {
        final RootReferenceFactorySupplier supplier = new RootReferenceFactorySupplier(new KeyMappingSupplier());
        supplier.get(node);
    }
}
