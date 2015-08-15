package com.mattunderscore.trees.binary.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.spi.TreeImplementation;

/**
 * Unit tests for {@link Implementation}.
 *
 * @author Matt Champion on 15/08/2015
 */
public final class ImplementationTest {
    @Test
    public void forClass() {
        final TreeImplementation implementation = new Implementation();
        assertEquals(BinarySearchTree.class, implementation.forClass());
    }
}
