/* Copyright © 2016 Matthew Champion
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of mattunderscore.com nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL MATTHEW CHAMPION BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.mattunderscore.tree.root.querying;

import com.mattunderscore.iterators.ConvertingIterator;
import com.mattunderscore.trees.query.ReductionResult;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.OpenStructuralNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Driver for post order partial reduction.
 * @author Matt Champion on 29/04/16
 */
/*package*/ final class PostOrderPartialReducerDriver {
    public <E, N extends OpenNode<E, N>, R> R reduce(N node, BiFunction<N, Collection<R>, ReductionResult<R>> reducer) {
        return tryReduce(node, reducer).result();
    }

    private <E, N extends OpenNode<E, N>, R> ReductionResult<R> tryReduce(
            N node,
            BiFunction<N, Collection<R>, ReductionResult<R>> reducer) {

        if (node.isLeaf()) {
            return reducer.apply(node, Collections.emptyList());
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

                    reductionResult = reducer.apply(parentState.node, childResults);
                    if (!reductionResult.shouldContinue()) {
                        return reductionResult;
                    }
                    else if (reductionResult.hasResult()) {
                        final R result = reductionResult.result();
                        parentState.result = result;
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
