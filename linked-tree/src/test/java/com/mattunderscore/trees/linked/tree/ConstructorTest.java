package com.mattunderscore.trees.linked.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Unit tests for {@link Constructor}.
 *
 * @author Matt Champion on 24/08/2015
 */
public final class ConstructorTest {
    @Test
    public void build() {
        final Constructor<String> constructor = new Constructor<>();
        final LinkedTree<String> tree = constructor.build("a");
        assertEquals("a", tree.getElement());
        assertEquals(0, tree.getNumberOfChildren());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void forClass() {
        final Constructor<String> constructor = new Constructor<>();
        assertEquals(LinkedTree.class, constructor.forClass());
    }
}
