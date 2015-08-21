package com.mattunderscore.trees.binary.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.binary.MutableBinaryTree;

/**
 * Unit tests for {@link MutableBinaryTreeImplKeyMapping}.
 *
 * @author Matt Champion on 21/08/2015
 */
public final class MutableBinaryTreeImplKeyMappingTest {

    @Test
    public void concreteClass() {
        final MutableBinaryTreeImplKeyMapping mapping = new MutableBinaryTreeImplKeyMapping();
        assertEquals(MutableBinaryTreeImpl.class, mapping.getConcreteClass());
    }

    @Test
    public void forClass() {
        final MutableBinaryTreeImplKeyMapping mapping = new MutableBinaryTreeImplKeyMapping();
        assertEquals(MutableBinaryTree.class, mapping.forClass());
    }
}
