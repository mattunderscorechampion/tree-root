package com.mattunderscore.trees.linked.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link EmptyConstructor}.
 *
 * @author Matt Champion on 24/08/2015
 */
public final class EmptyConstructorTest {
    @Test
    public void build() {
        final EmptyConstructor<String> constructor = new EmptyConstructor<>();
        final LinkedTree<String> tree = constructor.build();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void forClass() {
        final EmptyConstructor<String> constructor = new EmptyConstructor<>();
        assertEquals(LinkedTree.class, constructor.forClass());
    }
}
