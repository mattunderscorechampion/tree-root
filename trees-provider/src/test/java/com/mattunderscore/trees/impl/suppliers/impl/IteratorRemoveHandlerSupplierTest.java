package com.mattunderscore.trees.impl.suppliers.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.spi.DefaultRemovalHandler;
import com.mattunderscore.trees.spi.IteratorRemoveHandler;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link IteratorRemoveHandlerSupplier}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class IteratorRemoveHandlerSupplierTest {
    @Mock
    private Tree<String, Node<String>> tree;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void get() {
        final IteratorRemoveHandlerSupplier supplier = new IteratorRemoveHandlerSupplier(
            new KeyMappingSupplier());

        final IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler = supplier.get(tree);
        assertEquals(DefaultRemovalHandler.class, handler.getClass());
    }
}
