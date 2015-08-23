package com.mattunderscore.trees.tree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.construction.TypeKey;

/**
 * Unit tests for {@link Tree}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class TreeTest {
    @Test
    public void typeKey() {
        final TypeKey<Tree<String, Node<String>>> key = Tree.typeKey();

        assertEquals(Tree.class, key.getTreeType());
    }
}
