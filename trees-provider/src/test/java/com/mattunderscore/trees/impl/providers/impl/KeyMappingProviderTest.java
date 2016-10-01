package com.mattunderscore.trees.impl.providers.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.spi.KeyMapping;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link KeyMappingProvider}.
 *
 * @author Matt Champion on 25/08/2015
 */
public final class KeyMappingProviderTest {

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
        final KeyMappingProvider supplier = KeyMappingProvider.get();

        assertEquals(Tree.class, supplier.get(Tree.class));
    }

    @Test
    public void getFakes() {
        final KeyMappingProvider supplier = new KeyMappingProvider(loader);

        assertEquals(FakeTree.class, supplier.get(Tree.class));
    }

    public static final class FakeTree implements Tree<String, Node<String>> {
        @Override
        public Node<String> getRoot() {
            return null;
        }
    }
}
