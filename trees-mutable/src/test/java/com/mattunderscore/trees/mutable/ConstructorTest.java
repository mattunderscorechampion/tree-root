package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Unit tests for {@link Constructor}.
 * @author Matt Champion on 04/07/2015.
 */
public class ConstructorTest {

    @Test
    public void key() {
        final Constructor<String> constructor = new Constructor<>();
        assertEquals(MutableTreeImpl.class, constructor.forClass());
    }

    @Test
    public void build() {
        final Constructor<String> constructor = new Constructor<>();
        final MutableTree<String, MutableSettableNode<String>> tree = constructor.build("a");
        assertEquals("a", tree.getRoot().getElement());
        assertFalse(tree.isEmpty());
    }
}
