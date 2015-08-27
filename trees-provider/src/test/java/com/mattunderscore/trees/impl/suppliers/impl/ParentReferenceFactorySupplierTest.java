package com.mattunderscore.trees.impl.suppliers.impl;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.impl.suppliers.RootReferenceFactorySupplier;

/**
 * Unit tests for {@link RootReferenceFactorySupplierImpl}.
 *
 * @author Matt Champion on 22/08/2015
 */
public final class ParentReferenceFactorySupplierTest {
    @Mock
    private MutableBinaryTreeNode<String> node;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void noneFound() {
        final RootReferenceFactorySupplier supplier = new RootReferenceFactorySupplierImpl(KeyMappingSupplier.get());
        supplier.get(node);
    }
}
