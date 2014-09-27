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
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.selection.TreeSelector;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.iterators.EmptyIterator;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;
import com.mattunderscore.trees.utilities.iterators.SingletonIterator;

import java.util.Iterator;

/**
 * @author Matt Champion on 29/06/14.
 */
final class TreeSelectorFactoryImpl implements TreeSelectorFactory {
    private final SPISupport helper;

    public TreeSelectorFactoryImpl(SPISupport helper) {
        this.helper = helper;
    }

    @Override
    public <E> TreeSelector<E> newSelector(final NodeMatcher<E> matcher) throws OperationNotSupportedForType {
        return new TreeSelector<E>() {
            @Override
            public <N extends Node<E>, T extends Tree<E, N>> Iterator<T> select(T tree) {
                final Node<E> root = tree.getRoot();
                if (matcher.matches(root)) {
                    return new SingletonIterator<>(helper.<E, N, T, Node<E>>nodeToTree(root));
                }
                else {
                    return EmptyIterator.get();
                }
            }

            @Override
            public <N extends Node<E>, T extends Tree<E, N>, O extends Node<E>, U extends Tree<E, O>> Iterator<U> select(T tree, Class<U> newTreeType) throws OperationNotSupportedForType {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        };
    }

    @Override
    public <E> TreeSelector<E> newSelector(final TreeSelector<E> selector, final NodeMatcher<E> matcher) throws OperationNotSupportedForType {
        return new TreeSelector<E>() {
            @Override
            public <N extends Node<E>, T extends Tree<E, N>> Iterator<T> select(T tree) {
                final Iterator<T> parents = selector.select(tree);
                return new TreeIterator<>(parents, matcher);
            }

            @Override
            public <N extends Node<E>, T extends Tree<E, N>, O extends Node<E>, U extends Tree<E, O>> Iterator<U> select(T tree, Class<U> newTreeType) throws OperationNotSupportedForType {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        };
    }

    private final class TreeIterator<E, N extends Node<E>, T extends Tree<E, N>> extends PrefetchingIterator<T> {
        private final Iterator<T> parents;
        private final NodeMatcher matcher;
        private Iterator<N> possibles;

        public TreeIterator(Iterator<T> parents, NodeMatcher matcher) {
            this.parents = parents;
            this.matcher = matcher;
        }

        protected T calculateNext() {
            if (possibles == null) {
                final N next = parents.next().getRoot();
                final SimpleCollection<N> children = (SimpleCollection<N>)next.getChildren();
                possibles = children.iterator();
            }

            if (possibles.hasNext()) {
                final N possible = possibles.next();
                if (matcher.matches(possible)) {
                    return helper.<E, N, T, Node<E>>nodeToTree(possible);
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
}
