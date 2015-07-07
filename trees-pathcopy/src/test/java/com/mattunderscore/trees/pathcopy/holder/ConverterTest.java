package com.mattunderscore.trees.pathcopy.holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Test for {@link Converter}.
 * @author Matt Champion on 07/07/2015.
 */
public final class ConverterTest {

    @Test
    public void key() {
        final Converter<String> converter = new Converter<>();
        assertEquals(PathCopyTree.class, converter.forClass());
    }

    @Test
    public void build() {
        final Tree<String, Node<String>> tree = getTree();
        final Converter<String> converter = new Converter<>();
        final PathCopyTree<String> pathCopyTree = converter.build(tree);
        assertEquals("root", pathCopyTree.getRoot().getElement());
        assertTrue(pathCopyTree.getRoot().isLeaf());
        assertFalse(pathCopyTree.isEmpty());
    }

    private Tree<String, Node<String>> getTree() {
        final Trees trees = new TreesImpl();
        return trees.treeBuilders().bottomUpBuilder().create("root").build(LinkedTree.class);
    }
}