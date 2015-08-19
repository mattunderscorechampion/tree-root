package com.mattunderscore.trees.binary.mutable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link EmptyConstructor}.
 * @author matt on 19/08/15.
 */
public final class EmptyConstructorTest {

    @Test
    public void build() {
        final EmptyConstructor<String> constructor = new EmptyConstructor<>();
        assertTrue(constructor.build() instanceof MutableBinaryTreeImpl);
    }

    @Test
    public void forClass() {
        final EmptyConstructor<String> constructor = new EmptyConstructor<>();
        assertEquals(MutableBinaryTreeImpl.class, constructor.forClass());
    }
}