package com.mattunderscore.trees.impl.providers.impl;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.impl.providers.RootReferenceFactoryProvider;

/**
 * Unit tests for {@link RootReferenceFactoryProviderImpl}.
 *
 * @author Matt Champion on 22/08/2015
 */
public final class ParentReferenceFactoryProviderTest {
    @Mock
    private MutableBinaryTreeNode<String> node;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void noneFound() {
        final RootReferenceFactoryProvider supplier = new RootReferenceFactoryProviderImpl(KeyMappingProvider.get());
        supplier.get(node);
    }
}
