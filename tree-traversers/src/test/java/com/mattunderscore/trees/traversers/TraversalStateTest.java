package com.mattunderscore.trees.traversers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.traversers.TraversalState;
import com.mattunderscore.trees.tree.Node;

/**
 * Unit tests for {@link TraversalState}.
 *
 * @author Matt Champion on 08/08/2015
 */
public final class TraversalStateTest {
    @Mock
    private Node<String> node;
    @Mock
    private Iterator children;
    @Mock
    private MutableSettableStructuredNode<String> structuralNode;
    @Mock
    private Iterator structuralChildren;

    @Before
    public void setUp() {
        initMocks(this);

        when(node.childIterator()).thenReturn(children);
        when(structuralNode.childStructuralIterator()).thenReturn(structuralChildren);
    }


    @Test
    public void getNode() {
        final TraversalState<String, Node<String>> state = new TraversalState<>(node);
        assertEquals(node, state.getNode());
    }

    @Test
    public void hasNextChild() {
        final TraversalState<String, Node<String>> state = new TraversalState<>(node);
        state.hasNextChild();
        verify(children).hasNext();
    }

    @Test
    public void nextChild() {
        final TraversalState<String, Node<String>> state = new TraversalState<>(node);
        state.nextChild();
        verify(children).next();
    }

    @Test
    public void getStructuralNode() {
        final TraversalState<String, MutableSettableStructuredNode<String>> state = new TraversalState<>(structuralNode);
        assertEquals(structuralNode, state.getNode());
    }

    @Test
    public void hasNextStructuralChild() {
        final TraversalState<String, MutableSettableStructuredNode<String>> state = new TraversalState<>(structuralNode);
        state.hasNextChild();
        verify(structuralChildren).hasNext();
    }

    @Test
    public void nextStructuralChild() {
        final TraversalState<String, MutableSettableStructuredNode<String>> state = new TraversalState<>(structuralNode);
        state.nextChild();
        verify(structuralChildren).next();
    }
}
