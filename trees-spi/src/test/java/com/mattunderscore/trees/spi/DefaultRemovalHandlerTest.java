package com.mattunderscore.trees.spi;

import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public final class DefaultRemovalHandlerTest {

    @Test
    public void testIsSupported() {
        final IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler = new DefaultRemovalHandler<>();
        assertFalse(handler.isSupported());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        final IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler = new DefaultRemovalHandler<>();
        handler.remove(null, null);
    }

    @Test
    public void testForClass() throws Exception {
        final IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler = new DefaultRemovalHandler<>();
        assertNull(handler.forClass());
    }
}