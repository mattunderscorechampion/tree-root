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

package com.mattunderscore.trees.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.impl.providers.impl.NodeToRelatedTreeConverterProvider;
import com.mattunderscore.trees.selection.TreeSelector;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.spi.NodeToRelatedTreeConverter;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.iterators.PrefetchingIterator;
import com.mattunderscore.iterators.SingletonIterator;

/**
 * @author Matt Champion on 29/06/14.
 */
final class TreeSelectorFactoryImpl implements TreeSelectorFactory {
    private final NodeToRelatedTreeConverterProvider nodeToRelatedTreeConverterProvider;

    public TreeSelectorFactoryImpl(NodeToRelatedTreeConverterProvider nodeToRelatedTreeConverterProvider) {
        this.nodeToRelatedTreeConverterProvider = nodeToRelatedTreeConverterProvider;
    }

    @Override
    public <E> TreeSelector<E> newSelector(Predicate<OpenNode<? extends E, ?>> predicate) {
        return new TreeSelector<E>() {
            @Override
            public <N extends OpenNode<E, ? extends N>, T extends Tree<E, ? extends N>> Iterator<T> select(T tree) {
                final N root = tree.getRoot();
                if (predicate.test(root)) {
                    final NodeToRelatedTreeConverter<E, N, T> converter = nodeToRelatedTreeConverterProvider.get(root);
                    final T newTree = converter.treeFromRootNode(root);
                    return new SingletonIterator<>(newTree);
                }
                else {
                    return Collections.emptyIterator();
                }
            }

            @Override
            public <N extends OpenNode<E, ? extends N>, T extends Tree<E, ? extends N>, O extends OpenNode<E, O>, U extends Tree<E, O>> Iterator<U> select(T tree, Class<U> newTreeType) throws OperationNotSupportedForType {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        };
    }

    @Override
    public <E> TreeSelector<E> newSelector(TreeSelector<E> selector, Predicate<OpenNode<? extends E, ?>> predicate) {
        return new TreeSelector<E>() {
            @Override
            public <N extends OpenNode<E, ? extends N>, T extends Tree<E, ? extends N>> Iterator<T> select(T tree) {
                final Iterator<T> parents = selector.select(tree);
                return new TreeIterator<>(parents, predicate);
            }

            @Override
            public <N extends OpenNode<E,? extends N>, T extends Tree<E, ? extends N>, O extends OpenNode<E, O>, U extends Tree<E, O>> Iterator<U> select(T tree, Class<U> newTreeType) throws OperationNotSupportedForType {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        };
    }

    private final class TreeIterator<E, N extends OpenNode<E, ? extends N>, T extends Tree<E, ? extends N>> extends PrefetchingIterator<T> {
        private final Iterator<T> parents;
        private final Predicate<OpenNode<? extends E, ?>> predicate;
        private Iterator<? extends N> possibles;

        public TreeIterator(Iterator<T> parents, Predicate<OpenNode<? extends E, ?>> predicate) {
            this.parents = parents;
            this.predicate = predicate;
        }

        protected T calculateNext() {
            if (possibles == null) {
                final N next = parents.next().getRoot();
                possibles = next.childIterator();
            }

            if (possibles.hasNext()) {
                final N possible = possibles.next();
                if (predicate.test(possible)) {
                    final NodeToRelatedTreeConverter<E, N, T> converter = nodeToRelatedTreeConverterProvider.get(possible);
                    return converter.treeFromRootNode(possible);
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
