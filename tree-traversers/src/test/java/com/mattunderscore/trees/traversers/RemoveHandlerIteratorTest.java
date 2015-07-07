package com.mattunderscore.trees.traversers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.spi.IteratorRemoveHandler;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link RemoveHandlerIterator}.
 * @author Matt Champion on 07/07/2015.
 */
public final class RemoveHandlerIteratorTest {
    @Mock
    private Tree<String, Node<String>> tree;
    @Mock
    private Node<String> node;
    @Mock
    private IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void supported0() {
        when(handler.isSupported()).thenReturn(true);
        final TestIterator iterator = new TestIterator(tree, handler);

        assertTrue(iterator.isRemoveSupported());
    }

    @Test
    public void supported1() {
        when(handler.isSupported()).thenReturn(false);
        final TestIterator iterator = new TestIterator(tree, handler);

        assertFalse(iterator.isRemoveSupported());
    }

    @Test
    public void remove() {
        when(handler.isSupported()).thenReturn(true);
        final TestIterator iterator = new TestIterator(tree, handler);

        iterator.remove(node);
        verify(handler).remove(tree, node);
    }

    private static final class TestIterator extends RemoveHandlerIterator<String, Node<String>, Tree<String, Node<String>>> {

        public TestIterator(Tree<String, Node<String>> tree, IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler) {
            super(tree, handler);
        }

        @Override
        protected Node<String> calculateNext() throws NoSuchElementException {
            return null;
        }
    }
}
