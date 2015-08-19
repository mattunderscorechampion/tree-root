package com.mattunderscore.trees.binary.mutable;

import com.mattunderscore.trees.spi.TreeImplementation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link Implementation}.
 *
 * @author Matt Champion on 15/08/2015
 */
public final class ImplementationTest {
    @Test
    public void forClass() {
        final TreeImplementation implementation = new Implementation();
        assertEquals(MutableBinaryTreeImpl.class, implementation.forClass());
    }
}
