package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link MutableTree}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class MutableTreeTest {
    @Test
    public void typeKey() {
        final TypeKey<Tree<String, Node<String>>> key = Tree.typeKey();

        assertEquals(Tree.class, key.getTreeType());
    }
}
