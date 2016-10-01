package com.mattunderscore.trees.impl.providers.impl;

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
 * Unit tests for {@link IteratorRemoveHandlerProvider}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class IteratorRemoveHandlerProviderTest {
    @Mock
    private Tree<String, Node<String>> tree;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void get() {
        final IteratorRemoveHandlerProvider supplier = new IteratorRemoveHandlerProvider(
            KeyMappingProvider.get());

        final IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler = supplier.get(tree);
        assertEquals(DefaultRemovalHandler.class, handler.getClass());
    }
}
