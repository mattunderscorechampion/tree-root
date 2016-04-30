package com.mattunderscore.trees.query;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

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

    @Mock
    private BiFunction<BinaryTreeNode<String>, Collection<String>, ReductionResult<String>> partialReducer;

    @Mock
    private BiFunction<BinaryTreeNode<String>, Collection<String>, String> reducer;

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

        querier.isBalanced(tree);

        verify(querier).isBalanced(node);
    }

    @Test
    public void treePerfectlyBalanced() {
        final Querier querier = spy(new TestQuerier());

        querier.isPerfectlyBalanced(tree);

        verify(querier).isPerfectlyBalanced(node);
    }

    @Test
    public void treePartialReduce() {
        final Querier querier = spy(new TestQuerier());

        querier.partialReduce(tree, partialReducer);

        verify(querier).partialReduce(node, partialReducer);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void treeReduce() {
        final Querier querier = spy(new TestQuerier());

        querier.reduce(tree, reducer);

        verify(querier).partialReduce(eq(node), isA(PartialToTotalReductionResultAdapter.class));
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
        public <E, N extends OpenBinaryTreeNode<E, N>> boolean isPerfectlyBalanced(N node) {
            return false;
        }

        @Override
        public <E, N extends OpenNode<E, N>, R> R partialReduce(N node, BiFunction<N, Collection<R>, ReductionResult<R>> reducer) {
            return null;
        }
    }
}
