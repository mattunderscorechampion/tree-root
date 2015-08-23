package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.construction.TypeKey;

/**
 * Unit tests for {@link MutableTree}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class MutableTreeTest {
    @Test
    public void typeKey() {
        final TypeKey<MutableTree<String, MutableNode<String>>> key = MutableTree.typeKey();

        assertEquals(MutableTree.class, key.getTreeType());
    }
}
