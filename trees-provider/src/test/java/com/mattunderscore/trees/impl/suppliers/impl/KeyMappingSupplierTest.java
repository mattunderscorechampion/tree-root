package com.mattunderscore.trees.impl.suppliers.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.spi.KeyMapping;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link KeyMappingSupplier}.
 *
 * @author Matt Champion on 25/08/2015
 */
public final class KeyMappingSupplierTest {

    @Mock
    private Iterable<KeyMapping> loader;
    @Mock
    private KeyMapping mapping;

    @Before
    public void setUp() {
        initMocks(this);

        final List<KeyMapping> mappings = new ArrayList<>();
        mappings.add(mapping);
        mappings.add(mapping);
        when(loader.iterator()).thenReturn(mappings.iterator());
        when(mapping.forClass()).thenReturn(Tree.class);
        when(mapping.getConcreteClass()).thenReturn(FakeTree.class);
    }

    @Test
    public void getReal() {
        final KeyMappingSupplier supplier = KeyMappingSupplier.get();

        assertEquals(Tree.class, supplier.get(Tree.class));
    }

    @Test
    public void getFakes() {
        final KeyMappingSupplier supplier = new KeyMappingSupplier(loader);

        assertEquals(FakeTree.class, supplier.get(Tree.class));
    }

    public static final class FakeTree implements Tree<String, Node<String>> {
        @Override
        public Node<String> getRoot() {
            return null;
        }
    }
}
