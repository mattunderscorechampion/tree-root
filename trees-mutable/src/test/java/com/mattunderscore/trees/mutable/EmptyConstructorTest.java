package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link EmptyConstructor}.
 * @author Matt Champion on 04/07/2015.
 */
public class EmptyConstructorTest {

    @Test
    public void key() {
        final EmptyConstructor<String> constructor = new EmptyConstructor<>();
        assertEquals(MutableTreeImpl.class, constructor.forClass());
    }

    @Test
    public void build() {
        final EmptyConstructor<String> constructor = new EmptyConstructor<>();
        final MutableTreeImpl<String> tree = constructor.build();
        assertTrue(tree.isEmpty());
    }
}
