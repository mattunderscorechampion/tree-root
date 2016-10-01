package com.mattunderscore.trees.impl.providers.impl;

import org.junit.Test;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.impl.providers.EmptySortedTreeConstructorProvider;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link EmptySortedTreeConstructorProviderImpl}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class EmptySortedTreeConstructorProviderImplTest {
    @Test(expected = OperationNotSupportedForType.class)
    public void get() {
        final EmptySortedTreeConstructorProvider supplier = new EmptySortedTreeConstructorProviderImpl(
            KeyMappingProvider.get());

        supplier.get(Tree.class);
    }
}
