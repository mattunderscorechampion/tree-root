package com.mattunderscore.trees.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void isEmpty() {
        final FakeTree tree = new FakeTree();
        assertTrue(tree.isEmpty());
    }

    private static final class FakeTree implements Tree<String, Node<String>> {
        @Override
        public Node<String> getRoot() {
            return null;
        }
    }
}
