package com.mattunderscore.trees.impl.providers.impl;

import org.junit.Test;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link TreeConverterProvider}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class TreeConverterProviderTest {

    @Test(expected = OperationNotSupportedForType.class)
    public void get() {
        final TreeConverterProvider supplier = new TreeConverterProvider(
            KeyMappingProvider.get());

        supplier.get(Tree.class);
    }
}
