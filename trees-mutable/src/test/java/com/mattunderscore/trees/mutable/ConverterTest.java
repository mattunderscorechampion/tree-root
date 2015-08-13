package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;

/**
 * Unit tests for {@link Converter}.
 *
 * @author Matt Champion on 10/08/2015
 */
public final class ConverterTest {
    private LinkedTree<String> tree;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        final Trees trees = new TreesImpl();

        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder =
            trees.treeBuilders().bottomUpBuilder();

        tree = builder.create("a",
            builder.create("b"),
            builder.create("c",
                builder.create("d"))).build(LinkedTree.typeKey());
    }

    @Test
    public void convert() {
        final Converter<String> converter = new Converter<>();

        final MutableTreeImpl<String> mutableTree = converter.build(tree);

        assertEquals("a", mutableTree.getRoot().getElement());
        final Iterator<MutableTreeImpl<String>> rootChildIterator = mutableTree.getRoot().childIterator();
        assertEquals("b", rootChildIterator.next().getElement());
        final MutableTreeImpl<String> child = rootChildIterator.next();
        assertFalse(rootChildIterator.hasNext());
        assertEquals("c", child.getElement());
        final Iterator<MutableTreeImpl<String>> childChildIterator = child.childIterator();
        assertEquals("d", childChildIterator.next().getElement());
        assertFalse(childChildIterator.hasNext());
    }

    @Test
    public void forClass() {
        final Converter<String> converter = new Converter<>();

        assertEquals(MutableTreeImpl.class, converter.forClass());
    }
}
