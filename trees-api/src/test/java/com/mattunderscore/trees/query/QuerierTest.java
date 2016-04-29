package com.mattunderscore.trees.query;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.trees.binary.BinaryTreeNode;
import com.mattunderscore.trees.binary.OpenBinaryTreeNode;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link Querier}.
 *
 * @author Matt Champion on 29/08/2015
 */
public final class QuerierTest {
    @Mock
    private Tree<String, BinaryTreeNode<String>> tree;

    @Mock
    private BinaryTreeNode<String> node;

    @Before
    public void setUp() {
        initMocks(this);

        when(tree.getRoot()).thenReturn(node);
    }

    @Test
    public void treeHeight() {
        final Querier querier = spy(new TestQuerier());

        querier.height(tree);

        verify(querier).height(node);
    }

    @Test
    public void treePaths() {
        final Querier querier = spy(new TestQuerier());

        querier.pathsToLeaves(tree);

        verify(querier).pathsToLeaves(node);
    }

    @Test
    public void treeBalanced() {
        final Querier querier = spy(new TestQuerier());

        querier.pathsToLeaves(tree);

        verify(querier).pathsToLeaves(node);
    }

    private static class TestQuerier implements Querier {
        @Override
        public <E, N extends OpenNode<E, N>> int height(N node) {
            return 0;
        }

        @Override
        public <E, N extends OpenNode<E, N>> SimpleCollection<List<N>> pathsToLeaves(N node) {
            return null;
        }

        @Override
        public <E, N extends OpenBinaryTreeNode<E, N>> boolean isBalanced(N node) {
            return false;
        }

        @Override
        public <E, N extends OpenNode<E, N>, R> R reduce(N node, PostOrderPartialTreeReducer<E, N, R> reducer) {
            return null;
        }
    }
}
