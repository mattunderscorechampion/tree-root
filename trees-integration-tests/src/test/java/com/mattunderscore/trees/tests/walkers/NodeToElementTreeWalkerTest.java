package com.mattunderscore.trees.tests.walkers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mattunderscore.trees.common.walkers.NodeToElementTreeWalker;
import com.mattunderscore.trees.traversal.TreeWalker;
import com.mattunderscore.trees.tree.Node;

public class NodeToElementTreeWalkerTest {
    @Mock
    private TreeWalker<String> walker;

    @Mock
    private Node<String> node;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(node.getElement()).thenReturn("hello");
    }

    @Test
    public void onStarted() throws Exception {
        final NodeToElementTreeWalker<String, Node<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onStarted();
        Mockito.verify(walker).onStarted();
    }

    @Test
    public void onNode() throws Exception {
        final NodeToElementTreeWalker<String, Node<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNode(node);
        Mockito.verify(walker).onNode("hello");
    }

    @Test
    public void onNodeChildrenStarted() throws Exception {
        final NodeToElementTreeWalker<String, Node<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNodeChildrenStarted(node);
        Mockito.verify(walker).onNodeChildrenStarted("hello");
    }

    @Test
    public void onNodeChildrenRemaining() throws Exception {
        final NodeToElementTreeWalker<String, Node<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNodeChildrenRemaining(node);
        Mockito.verify(walker).onNodeChildrenRemaining("hello");
    }

    @Test
    public void onNodeChildrenCompleted() throws Exception {
        final NodeToElementTreeWalker<String, Node<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNodeChildrenCompleted(node);
        Mockito.verify(walker).onNodeChildrenCompleted("hello");
    }

    @Test
    public void onNodeNoChildren() throws Exception {
        final NodeToElementTreeWalker<String, Node<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onNodeNoChildren(node);
        Mockito.verify(walker).onNodeNoChildren("hello");
    }

    @Test
    public void onCompleted() throws Exception {
        final NodeToElementTreeWalker<String, Node<String>> elementWalker = new NodeToElementTreeWalker<>(walker);
        elementWalker.onCompleted();
        Mockito.verify(walker).onCompleted();
    }
}