package com.mattunderscore.trees.linked.tree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link NodeConverter}.
 *
 * @author Matt Champion on 24/08/2015
 */
public final class NodeConverterTest {
    @Test
    public void treeFromRootNode() {
        LinkedTree<String> child = new LinkedTree<>("child");
        LinkedTree<String> root = new LinkedTree<>("root", new LinkedTree[] { child });

        final NodeConverter<String> converter = new NodeConverter<>();
        final LinkedTree<String> tree = converter.treeFromRootNode(root);
        assertEquals("root", tree.getRoot().getElement());
        assertEquals("child", tree.getRoot().getChild(0).getElement());
        assertEquals(1, tree.getRoot().getNumberOfChildren());
    }

    @Test
    public void forClass() {
        final NodeConverter<String> converter = new NodeConverter<>();
        assertEquals(LinkedTree.class, converter.forClass());
    }
}
