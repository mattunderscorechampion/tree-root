package com.mattunderscore.tree.root.querying;

import com.mattunderscore.iterators.ConvertingIterator;
import com.mattunderscore.trees.query.PostOrderPartialTreeReducer;
import com.mattunderscore.trees.query.ReductionResult;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.OpenStructuralNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Driver for post order partial reduction.
 * @author Matt Champion on 29/04/16
 */
/*package*/ final class PostOrderPartialReducerDriver {
    public <E, N extends OpenNode<E, N>, R> R reduce(N node, PostOrderPartialTreeReducer<E, N, R> reducer) {
        return tryReduce(node, reducer).result();
    }

    private <E, N extends OpenNode<E, N>, R> ReductionResult<R> tryReduce(N node, PostOrderPartialTreeReducer<E, N, R> reducer) {
        if (node.isLeaf()) {
            return reducer.applyToNode(node, Collections.emptyList());
        }

        final Stack<TraversalState<E, N, R>> parents = new Stack<>();
        TraversalState<E, N, R> nextNode = new TraversalState<>(node);
        final Stack<Collection<TraversalState<E, N, R>>> children = new Stack<>();
        ReductionResult<R> reductionResult = null;

        while (!parents.isEmpty() || nextNode != null) {
            if (nextNode != null) {
                final TraversalState<E, N, R> state = nextNode;
                parents.push(state);
                if (state.children.hasNext()) {
                    nextNode = state.children.next();
                    final List<TraversalState<E, N, R>> newChildren = new ArrayList<>();
                    newChildren.add(nextNode);
                    children.push(newChildren);
                    // GO LEFT
                }
                else {
                    // Leaf
                    nextNode = null;
                    children.push(new ArrayList<>());
                }
            }
            else {
                final TraversalState<E, N, R> parentState = parents.peek();
                if (parentState.children.hasNext()) {
                    nextNode = parentState.children.next();
                    children.peek().add(nextNode);
                    // GO RIGHT
                    continue;
                }

                // GO UP
                if (!parentState.children.hasNext()) {
                    parents.pop();

                    final List<R> childResults = children
                            .pop()
                            .stream()
                            .filter(state -> state.hasResult)
                            .map(state -> state.result)
                            .collect(Collectors.toList());

                    reductionResult = reducer.applyToNode(parentState.node, childResults);
                    if (!reductionResult.shouldContinue()) {
                        return reductionResult;
                    }
                    else if (reductionResult.hasResult()) {
                        parentState.result = reductionResult.result();
                        parentState.hasResult = true;
                    }
                }

            }
        }

        return reductionResult;
    }

    private static final class TraversalState<E, N extends OpenNode<E, N>, R> {
        private final N node;
        private final Iterator<TraversalState<E, N, R>> children;
        private R result = null;
        private boolean hasResult = false;

        @SuppressWarnings("unchecked")
        private TraversalState(N node) {
            this.node = node;
            if (node instanceof OpenStructuralNode) {
                final OpenStructuralNode structuralNode = (OpenStructuralNode) node;
                this.children = new ToTraversalStateIterator<>(structuralNode.childStructuralIterator());
            }
            else {
                this.children = new ToTraversalStateIterator<>(node.childIterator());
            }
        }

        private final class ToTraversalStateIterator<E, N extends OpenNode<E, N>>
                extends ConvertingIterator<TraversalState<E, N, R>, N> {
            public ToTraversalStateIterator(Iterator<? extends N> delegate) {
                super(delegate);
            }

            @Override
            protected TraversalState<E, N, R> convert(N n) {
                return new TraversalState<>(n);
            }
        }
    }
}
