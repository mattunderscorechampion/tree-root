package com.mattunderscore.trees.impl.suppliers.impl;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Unit tests for {@link RotatorSupplier}.
 *
 * @author Matt Champion on 22/08/2015
 */
public final class RotatorSupplierTest {
    @Mock
    private MutableBinaryTreeNode<String> node;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void noneFoundLeft() {
        final RotatorSupplier supplier = new RotatorSupplier(new KeyMappingSupplier());
        supplier.get(node, RotationDirection.LEFT);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void noneFoundRight() {
        final RotatorSupplier supplier = new RotatorSupplier(new KeyMappingSupplier());
        supplier.get(node, RotationDirection.RIGHT);
    }
}
