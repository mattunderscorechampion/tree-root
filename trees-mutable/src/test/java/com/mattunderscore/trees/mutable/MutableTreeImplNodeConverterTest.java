package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.impl.SPISupportImpl;
import com.mattunderscore.trees.impl.TreesImpl;

/**
 * Unit tests for {@link MutableTreeImplNodeConverter}.
 * @author Matt Champion on 04/07/2015.
 */
public class MutableTreeImplNodeConverterTest {

    @Test
    public void key() {
        final MutableTreeImplNodeConverter<String, MutableSettableNode<String>> constructor =
            new MutableTreeImplNodeConverter<>();
        assertEquals(MutableTreeImpl.class, constructor.forClass());
    }

    @Test
    public void convert() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String, MutableSettableNode<String>> builder = trees.treeBuilders().bottomUpBuilder();
        final MutableTree<String, MutableSettableNode<String>> tree = builder.create("root",
            builder.create("left"),
            builder.create("right"))
            .build(MutableTreeImpl.typeKey());

        final MutableTreeImplNodeConverter<String, MutableSettableNode<String>> constructor =
            new MutableTreeImplNodeConverter<>();
        constructor.setSupport(new SPISupportImpl());

        final MutableTree<String, MutableSettableNode<String>> newTree = constructor.treeFromRootNode(tree.getRoot());
        assertFalse(newTree.isEmpty());
        assertEquals("root", newTree.getRoot().getElement());
        assertFalse(newTree.getRoot().isLeaf());
    }
}
