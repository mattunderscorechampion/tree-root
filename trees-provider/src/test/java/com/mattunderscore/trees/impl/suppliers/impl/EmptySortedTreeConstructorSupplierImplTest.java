package com.mattunderscore.trees.impl.suppliers.impl;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.impl.suppliers.EmptySortedTreeConstructorSupplier;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link EmptySortedTreeConstructorSupplierImpl}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class EmptySortedTreeConstructorSupplierImplTest {
    @Test(expected = OperationNotSupportedForType.class)
    public void get() {
        final EmptySortedTreeConstructorSupplier supplier = new EmptySortedTreeConstructorSupplierImpl(
            new KeyMappingSupplier());

        supplier.get(Tree.class);
    }
}
