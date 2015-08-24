package com.mattunderscore.trees.linked.tree;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Collections;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link Converter}.
 *
 * @author Matt Champion on 24/08/2015
 */
public final class ConverterTest {
    @Mock
    private Tree<String, Node<String>> tree;
    @Mock
    private Node<String> root;
    @Mock
    private Node<String> child;

    @Before
    public void setUp() {
        initMocks(this);
        when(tree.getRoot()).thenReturn(root);
        when(root.getElement()).thenReturn("root");
        when(child.getElement()).thenReturn("child");
        final Iterator childIterator = Collections.singleton(child).iterator();
        when(root.childIterator()).thenReturn(childIterator);
        final Iterator emptyIterator = Collections.emptySet().iterator();
        when(child.childIterator()).thenReturn(emptyIterator);
    }

    @Test
    public void treeFromRootNode() {
        final Converter<String> converter = new Converter<>();
        final LinkedTree<String> newTree = converter.build(tree);
        assertEquals("root", newTree.getRoot().getElement());
        assertEquals("child", newTree.getRoot().getChild(0).getElement());
        assertEquals(1, newTree.getRoot().getNumberOfChildren());
    }

    @Test
    public void forClass() {
        final Converter<String> converter = new Converter<>();
        assertEquals(LinkedTree.class, converter.forClass());
    }
}
