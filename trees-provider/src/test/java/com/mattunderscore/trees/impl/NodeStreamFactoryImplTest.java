package com.mattunderscore.trees.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.traversal.NodeStreamFactory;
import com.mattunderscore.trees.tree.OpenNode;

/**
 * Unit tests for NodeStreamFactoryImpl.
 * @author Matt Champion on on 30/06/2015.
 */
public final class NodeStreamFactoryImplTest {
    private static NodeStreamFactory streamFactory;
    private static LinkedTree<String> tree;

    @BeforeClass
    public static void setUp() {
        final Trees trees = new TreesImpl();
        streamFactory = trees.nodeStreams();
        final BottomUpTreeBuilder<String, MutableSettableStructuredNode<String>> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("root", builder.create("a", builder.create("c")), builder.create("b"))
            .build(LinkedTree.<String>typeKey());
    }

    @Test
    public void preOrderStream() {
        final List<? extends MutableSettableStructuredNode<String>> list = streamFactory
            .preOrderStream(tree)
            .collect(Collectors.toList());
        assertEquals(4, list.size());
        assertEquals("root", list.get(0).getElement());
        assertEquals("a", list.get(1).getElement());
        assertEquals("c", list.get(2).getElement());
        assertEquals("b", list.get(3).getElement());
    }

    @Test
    public void postOrderStream() {
        final List<? extends MutableSettableStructuredNode<String>> list = streamFactory
            .postOrderStream(tree)
            .collect(Collectors.toList());
        assertEquals("c", list.get(0).getElement());
        assertEquals("a", list.get(1).getElement());
        assertEquals("b", list.get(2).getElement());
        assertEquals("root", list.get(3).getElement());
    }

    @Test
    public void inOrderStream() {
        final List<? extends MutableSettableStructuredNode<String>> list = streamFactory
            .inOrderStream(tree)
            .collect(Collectors.toList());
        assertEquals("c", list.get(0).getElement());
        assertEquals("a", list.get(1).getElement());
        assertEquals("root", list.get(2).getElement());
        assertEquals("b", list.get(3).getElement());
    }

    @Test
    public void breadthFirstStream() {
        final List<? extends MutableSettableStructuredNode<String>> list = streamFactory
            .breadthFirstStream(tree)
            .collect(Collectors.toList());
        assertEquals("root", list.get(0).getElement());
        assertEquals("a", list.get(1).getElement());
        assertEquals("b", list.get(2).getElement());
        assertEquals("c", list.get(3).getElement());
    }

    @Test
    public void preOrderElementStream() {
        final List<? extends String> list = streamFactory
            .preOrderStream(tree)
            .map(OpenNode::getElement)
            .collect(Collectors.toList());
        assertEquals(4, list.size());
        assertEquals("root", list.get(0));
        assertEquals("a", list.get(1));
        assertEquals("c", list.get(2));
        assertEquals("b", list.get(3));
    }
}
