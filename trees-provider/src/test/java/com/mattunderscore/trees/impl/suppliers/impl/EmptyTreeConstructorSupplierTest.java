package com.mattunderscore.trees.impl.suppliers.impl;

import org.junit.Test;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link EmptyTreeConstructorSupplier}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class EmptyTreeConstructorSupplierTest {
    @Test(expected = OperationNotSupportedForType.class)
    public void get() {
        final EmptyTreeConstructorSupplier supplier = new EmptyTreeConstructorSupplier(
            KeyMappingSupplier.get());

        supplier.get(Tree.class);
    }
}
