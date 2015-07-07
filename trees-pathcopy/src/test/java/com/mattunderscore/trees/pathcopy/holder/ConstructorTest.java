package com.mattunderscore.trees.pathcopy.holder;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Test for {@link Constructor}.
 * @author Matt Champion on 07/07/2015.
 */
public class ConstructorTest {

    @Test
    public void key() {
        final Constructor<String> constructor = new Constructor<>();
        assertEquals(PathCopyTree.class, constructor.forClass());
    }

    @Test
    public void build() {
        final Constructor<String> constructor = new Constructor<>();
        final PathCopyTree<String> tree = constructor.build("root", new PathCopyTree[] {});
        assertEquals("root", tree.getRoot().getElement());
        assertTrue(tree.getRoot().isLeaf());
        assertFalse(tree.isEmpty());
        assertTrue(tree instanceof PathCopyTree);
    }
}