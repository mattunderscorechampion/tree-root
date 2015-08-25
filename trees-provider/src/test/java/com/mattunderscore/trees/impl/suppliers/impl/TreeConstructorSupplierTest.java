package com.mattunderscore.trees.impl.suppliers.impl;

import org.junit.Test;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link TreeConstructorSupplier}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class TreeConstructorSupplierTest {
    @Test(expected = OperationNotSupportedForType.class)
    public void get() {
        final TreeConstructorSupplier supplier = new TreeConstructorSupplier(
            KeyMappingSupplier.get());

        supplier.get(Tree.class);
    }
}
