package com.mattunderscore.trees.impl.providers.impl;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.impl.providers.RotatorProvider;
import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Unit tests for {@link RotatorProviderImpl}.
 *
 * @author Matt Champion on 22/08/2015
 */
public final class RotatorProviderTest {
    @Mock
    private MutableBinaryTreeNode<String> node;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void noneFoundLeft() {
        final RotatorProvider supplier = new RotatorProviderImpl(KeyMappingProvider.get());
        supplier.get(node, RotationDirection.LEFT);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void noneFoundRight() {
        final RotatorProvider supplier = new RotatorProviderImpl(KeyMappingProvider.get());
        supplier.get(node, RotationDirection.RIGHT);
    }
}
