package com.mattunderscore.trees.binary;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.construction.TypeKey;

/**
 * Unit tests for {@link BinaryTree}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class BinaryTreeTest {
    @Test
    public void typeKey() {
        final TypeKey<BinaryTree<String, BinaryTreeNode<String>>> key = BinaryTree.typeKey();

        assertEquals(BinaryTree.class, key.getTreeType());
    }
}
