package com.mattunderscore.trees.spi.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder.TopDownTreeBuilder;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;


/**
 * Unit tests for {@link AbstractNodeToTreeConverter}.
 * @author Matt Champion on 12/07/2015.
 */
public final class AbstractNodeToTreeConverterTest {
    @Mock
    private TopDownTreeRootBuilder<String, Node<String>> rootBuilder;
    @Mock
    private TopDownTreeBuilder<String, Node<String>> builder;
    @Mock
    private Node<String> root;
    @Mock
    private Node<String> left;
    @Mock
    private Node<String> right;
    @Mock
    private Node<String> bottom;
    @Mock
    private Tree<String, Node<String>> tree;
    @Mock
    private Tree<String, Node<String>> convertedTree;

    @Before
    public void setUp() {
        initMocks(this);

        final List<Node<String>> depth1 = new ArrayList<>();
        depth1.add(left);
        depth1.add(right);
        final List<Node<String>> depth2 = new ArrayList<>();
        depth2.add(bottom);

        when(tree.getRoot()).thenReturn(root);
        when(root.childIterator()).thenAnswer(invocationOnMock -> depth1.iterator());
        when(root.getElement()).thenReturn("root");
        when(root.getNumberOfChildren()).thenReturn(2);
        when(root.isLeaf()).thenReturn(false);
        when(left.childIterator()).thenAnswer(invocationOnMock -> Collections.emptyIterator());
        when(left.getElement()).thenReturn("left");
        when(left.getNumberOfChildren()).thenReturn(0);
        when(left.isLeaf()).thenReturn(true);
        when(right.childIterator()).thenAnswer(invocationOnMock -> depth2.iterator());
        when(right.getElement()).thenReturn("right");
        when(right.getNumberOfChildren()).thenReturn(1);
        when(right.isLeaf()).thenReturn(false);
        when(bottom.childIterator()).thenAnswer(invocationOnMock -> Collections.emptyIterator());
        when(bottom.getElement()).thenReturn("bottom");
        when(bottom.getNumberOfChildren()).thenReturn(0);
        when(bottom.isLeaf()).thenReturn(true);

        when(rootBuilder.root(isA(String.class))).thenReturn(builder);
        when(builder.addChild(isA(String.class))).thenReturn(builder);
        when(builder.build(Tree.class)).thenReturn(convertedTree);
    }

    @Test
    public void treeFromRootNode() {
        final Converter converter = new Converter(Node.class, Tree.class, rootBuilder);
        final Tree<String, Node<String>> newTree = converter.treeFromRootNode(root);

        verify(rootBuilder).root("root");
        verify(builder).addChild("left");
        verify(builder).addChild("right");
        verify(builder).addChild("bottom");

        verify(builder).build(Tree.class);

        assertSame(newTree, convertedTree);
    }

    @Test
    public void forClass() {
        final Converter converter = new Converter(Node.class, Tree.class, rootBuilder);
        assertEquals(Node.class, converter.forClass());
    }

    private final class Converter extends AbstractNodeToTreeConverter<String, Node<String>, Tree<String, Node<String>>> {
        private final TopDownTreeRootBuilder<String, Node<String>> builder;

        public Converter(Class targetNodeClass, Class targetTreeClass, TopDownTreeRootBuilder<String, Node<String>> builder) {
            super(targetNodeClass, targetTreeClass);
            this.builder = builder;
        }

        @Override
        protected TopDownTreeRootBuilder<String, Node<String>> getBuilder() {
            return builder;
        }
    }
}
