package com.mattunderscore.trees.binary;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link BinaryTree}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class BinaryTreeTest {
    @Test
    public void typeKey() {
        final TypeKey<Tree<String, Node<String>>> key = Tree.typeKey();

        assertEquals(Tree.class, key.getTreeType());
    }
}
