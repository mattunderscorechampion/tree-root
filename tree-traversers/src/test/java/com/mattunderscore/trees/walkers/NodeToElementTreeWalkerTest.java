package com.mattunderscore.trees.walkers;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mattunderscore.trees.mutable.ClosedMutableSettableStructuredNode;
import com.mattunderscore.trees.traversal.TreeWalker;

public class NodeToElementTreeWalkerTest {
    @Mock
    private TreeWalker<String> walker;

    @Mock
    private ClosedMutableSettableStructuredNode<String> node;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(node.getElement()).thenReturn("hello");
    }

    @Test
    public void onStarted() throws Exception {
        final NodeToElementTreeWalker<String, ClosedMutableSettableStructuredNode<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onStarted();
        Mockito.verify(walker).onStarted();
    }

    @Test
    public void onNode() throws Exception {
        final NodeToElementTreeWalker<String, ClosedMutableSettableStructuredNode<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNode(node);
        Mockito.verify(walker).onNode("hello");
    }

    @Test
    public void onNodeChildrenStarted() throws Exception {
        final NodeToElementTreeWalker<String, ClosedMutableSettableStructuredNode<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNodeChildrenStarted(node);
        Mockito.verify(walker).onNodeChildrenStarted("hello");
    }

    @Test
    public void onNodeChildrenRemaining() throws Exception {
        final NodeToElementTreeWalker<String, ClosedMutableSettableStructuredNode<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNodeChildrenRemaining(node);
        Mockito.verify(walker).onNodeChildrenRemaining("hello");
    }

    @Test
    public void onNodeChildrenCompleted() throws Exception {
        final NodeToElementTreeWalker<String, ClosedMutableSettableStructuredNode<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNodeChildrenCompleted(node);
        Mockito.verify(walker).onNodeChildrenCompleted("hello");
    }

    @Test
    public void onNodeNoChildren() throws Exception {
        final NodeToElementTreeWalker<String, ClosedMutableSettableStructuredNode<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNodeNoChildren(node);
        Mockito.verify(walker).onNodeNoChildren("hello");
    }

    @Test
    public void onCompleted() throws Exception {
        final NodeToElementTreeWalker<String, ClosedMutableSettableStructuredNode<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onCompleted();
        Mockito.verify(walker).onCompleted();
    }
}