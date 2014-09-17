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

package com.mattunderscore.trees.common.selectors;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.iterators.EmptyIterator;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A node selector that extends from the nodes of other selectors.
 * @param <E> The element type
 * @author Matt Champion on 17/09/14.
 */
public abstract class ExtendingNodeSelector<E> implements NodeSelector<E> {
    private final NodeSelector<E> baseSelector;

    public ExtendingNodeSelector(NodeSelector<E> baseSelector) {
        this.baseSelector = baseSelector;
    }

    @Override
    public final <N extends Node<E>> Iterator<N> select(Tree<E, N> tree) throws OperationNotSupportedForType {
        final Iterator<N> iterator = baseSelector.select(tree);
        return new ConvertingIterator<>(iterator);
    }

    /**
     * Abstract method that is used to take one node returned by the base selector and return an iterator over the
     * extended selection.
     * @param nodeToExtendFrom The node to extend from
     * @param <N> The node type
     * @return An iterator of the extended nodes
     */
    protected abstract <N extends Node<E>> Iterator<N> getExtendingIterator(N nodeToExtendFrom);

    /**
     * Iterator that converts from nodes returned to the base iterator to the nodes to return from this selector.
     * @param <N> The node type
     */
    private final class ConvertingIterator<N extends Node<E>> extends PrefetchingIterator<N> {
        private final Iterator<N> nodeIterator;
        private Iterator<N> passedOfIterator;

        private ConvertingIterator(Iterator<N> iterator) {
            this.nodeIterator = iterator;
            passedOfIterator = new EmptyIterator<>();
        }

        @Override
        protected N calculateNext() throws NoSuchElementException {
            if (passedOfIterator.hasNext()) {
                return passedOfIterator.next();
            }
            else {
                N nextChild = null;
                do {
                    final N nextNode = nodeIterator.next();
                    passedOfIterator = getExtendingIterator(nextNode);
                    if (passedOfIterator.hasNext()) {
                        nextChild = passedOfIterator.next();
                    }
                }
                while (nextChild == null);
                return nextChild;
            }
        }
    }
}
