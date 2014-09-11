/* Copyright © 2014 Matthew Champion
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

package com.mattunderscore.trees.common;

import com.mattunderscore.trees.*;
import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.utilities.iterators.EmptyIterator;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;
import com.mattunderscore.trees.utilities.iterators.SingletonIterator;

import java.util.Iterator;

/**
 * Factory for node selectors.
 * @author matt on 25/06/14.
 */
final class NodeSelectorFactoryImpl implements NodeSelectorFactory {
    private final SPISupport helper;

    public NodeSelectorFactoryImpl(SPISupport helper) {
        this.helper = helper;
    }

    @Override
    public <E> NodeSelector<E> newSelector(final NodeMatcher<E> matcher) {
        return new NodeSelector<E>() {
            @Override
            public <T extends Node<E>> Iterator<T> select(Tree<E, T> tree) {
                final T root = tree.getRoot();
                if (matcher.matches(root)) {
                    return new SingletonIterator<>(root);
                }
                else {
                    return EmptyIterator.get();
                }
            }
        };
    }

    @Override
    public <E> NodeSelector<E> newSelector(final NodeSelector<E> selector, final NodeMatcher<E> matcher) {
        return new NodeSelector<E>() {
            @Override
            public <T extends Node<E>> Iterator<T> select(Tree<E, T> tree) {
                final Iterator<T> parents = selector.select(tree);
                return new NodeIterator<>(parents, matcher);
            }
        };
    }

    @Override
    public <E> NodeSelector<E> newSelector(final NodeSelector<E> baseSelector, final NodeSelector<E> extensionSelector) {
        return new NodeSelector<E>() {
            @Override
            public <N extends Node<E>> Iterator<N> select(Tree<E, N> tree) {
                final Iterator<N> startingPoints = baseSelector.select(tree);
                return new AsNodeIterator<>(startingPoints, extensionSelector, helper);
            }
        };
    }

    private static final class NodeIterator<E, N extends Node<E>> extends PrefetchingIterator<N> {
        private final Iterator<N> parents;
        private final NodeMatcher matcher;
        private Iterator<N> possibles;

        public NodeIterator(Iterator<N> parents, NodeMatcher matcher) {
            this.parents = parents;
            this.matcher = matcher;
        }

        protected N calculateNext() {
            if (possibles == null) {
                final N next = parents.next();
                final SimpleCollection<N> children = (SimpleCollection<N>)next.getChildren();
                possibles = children.iterator();
            }

            if (possibles.hasNext()) {
                final N possible = possibles.next();
                if (matcher.matches(possible)) {
                    return possible;
                } else {
                    return calculateNext();
                }
            }
            else {
                possibles = null;
                return calculateNext();
            }
        }
    }

    private static final class AsNodeIterator<E, N extends Node<E>> extends PrefetchingIterator<N> {
        private final Iterator<N> startingPoints;
        private final NodeSelector selector;
        private final SPISupport helper;
        private Iterator<N> currentEndPoints;

        public AsNodeIterator(Iterator<N> startingPoints, NodeSelector selector, SPISupport helper) {
            this.startingPoints = startingPoints;
            this.selector = selector;
            this.helper = helper;
        }

        protected N calculateNext() {
            if (currentEndPoints == null) {
                final Tree<E, N> tree = helper.nodeToTree(startingPoints.next());
                currentEndPoints = selector.select(tree);
            }

            if (currentEndPoints.hasNext()) {
                return currentEndPoints.next();
            }
            else {
                currentEndPoints = null;
                return calculateNext();
            }
        }
    }
}
