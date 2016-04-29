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
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.OpenStructuralNode;
import com.mattunderscore.trees.utilities.ComparableComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Querier to get the height of a node or tree.
 * @author Matt Champion on 27/04/16
 */
/*package*/ final class HeightQuerier<E, N extends OpenNode<E, N>> {

    public int height(N node) {
        if (node.isLeaf()) {
            return 0;
        }

        final Stack<TraversalState<E, N>> parents = new Stack<>();
        TraversalState<E, N> nextNode = new TraversalState<>(node);
        final Stack<Collection<TraversalState<E, N>>> children = new Stack<>();
        int lastHeight = 0;

        while (!parents.isEmpty() || nextNode != null) {
            if (nextNode != null) {
                final TraversalState<E, N> state = nextNode;
                parents.push(state);
                if (state.children.hasNext()) {
                    nextNode = state.children.next();
                    final List<TraversalState<E, N>> newChildren = new ArrayList<>();
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
                final TraversalState<E, N> parentState = parents.peek();
                if (parentState.children.hasNext()) {
                    nextNode = parentState.children.next();
                    children.peek().add(nextNode);
                    // GO RIGHT
                    continue;
                }

                // GO UP
                if (!parentState.children.hasNext()) {
                    parents.pop();

                    final Optional<Integer> height = children
                            .pop()
                            .stream()
                            .map(state -> state.height)
                            .collect(Collectors.maxBy(ComparableComparator.get()));

                    if (height.isPresent()) {
                        parentState.height = height.get() + 1;
                        lastHeight = parentState.height;
                    }
                    else {
                        lastHeight = 0;
                    }
                }

            }
        }

        return lastHeight;
    }

    public static final class TraversalState<E, N extends OpenNode<E, N>> {
        private final N node;
        private final Iterator<TraversalState<E, N>> children;
        private int height = 0;

        @SuppressWarnings("unchecked")
        public TraversalState(N node) {
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
                extends ConvertingIterator<TraversalState<E, N>, N> {
            public ToTraversalStateIterator(Iterator<? extends N> delegate) {
                super(delegate);
            }

            @Override
            protected TraversalState<E, N> convert(N n) {
                return new TraversalState<>(n);
            }
        }
    }
}
