/* Copyright © 2015 Matthew Champion
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

import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mattunderscore.trees.sorted.SortingTree;
import com.mattunderscore.trees.traversal.NodeStreamFactory;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation of {@link NodeStreamFactory}.
 * @author Matt Champion on 30/06/2015
 */
public final class NodeStreamFactoryImpl implements NodeStreamFactory {
    private TreeIteratorFactory iterators;

    public NodeStreamFactoryImpl(TreeIteratorFactory iterators) {
        this.iterators = iterators;
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> Stream<N> preOrderStream(T tree) {
        final Spliterator<N> spliterator = spliterator(iterators.preOrderIterator(tree));
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> Stream<N> inOrderStream(T tree) {
        final Spliterator<N> spliterator = spliterator(iterators.inOrderIterator(tree));
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> Stream<N> postOrderStream(T tree) {
        final Spliterator<N> spliterator = spliterator(iterators.postOrderIterator(tree));
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> Stream<N> breadthFirstStream(T tree) {
        final Spliterator<N> spliterator = spliterator(iterators.breadthFirstIterator(tree));
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends SortingTree<E, N>> Stream<N> sortedStream(T tree) {
        final Spliterator<N> spliterator = spliterator(tree);
        return StreamSupport.stream(spliterator, false);
    }

    private <E, N extends OpenNode<E, N>> Spliterator<N> spliterator(Iterator<N> iterator) {
        return Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);
    }

    private <E, N extends OpenNode<E, N>, T extends SortingTree<E, N>> Spliterator<N> spliterator(T tree) {
        return new SortedTreeNodeSpliterator<>(iterators.inOrderIterator(tree), tree.getComparator());
    }

    /**
     * An ordered, sorted spliterator for sorting trees.
     * @param <E> The element type
     * @param <N> The node type
     */
    private static class SortedTreeNodeSpliterator<E, N extends OpenNode<E, N>> extends AbstractSpliterator<N> {
        private final Iterator<N> iterator;
        private final Comparator<E> comparator;

        public SortedTreeNodeSpliterator(Iterator<N> iterator, Comparator<E> comparator) {
            super(Long.MAX_VALUE, Spliterator.ORDERED | Spliterator.SORTED);
            this.iterator = iterator;
            this.comparator = comparator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super N> action) {
            if (iterator.hasNext()) {
                action.accept(iterator.next());
                return true;
            }
            return false;
        }

        @Override
        public Comparator<N> getComparator() {
            return (o1, o2) -> comparator.compare(o1.getElement(), o2.getElement());
        }
    }
}
