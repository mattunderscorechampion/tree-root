package com.mattunderscore.trees.binary;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.construction.TypeKey;

/**
 * Unit tests for {@link MutableBinaryTree}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class MutableBinaryTreeTest {
    @Test
    public void typeKey() {
        final TypeKey<MutableBinaryTree<String, MutableBinaryTreeNode<String>>> key = MutableBinaryTree.typeKey();

        assertEquals(MutableBinaryTree.class, key.getTreeType());
    }
}
