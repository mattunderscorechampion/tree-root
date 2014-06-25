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

package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.NodeMatcher;
import com.mattunderscore.trees.NodeSelector;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.internal.iterators.EmptyIterator;
import com.mattunderscore.trees.internal.iterators.PrefetchingIterator;
import com.mattunderscore.trees.internal.iterators.SingletonIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author matt on 25/06/14.
 */
public class NodeSelectorFactory {

    public <E> NodeSelector newSelector(final NodeMatcher<E> matcher) {
        return new NodeSelector<E>() {
            @Override
            public Iterator<Node<E>> select(Tree<E> tree) {
                final Node<E> root = tree.getRoot();
                if (matcher.matches(root)) {
                    return new SingletonIterator<>(root);
                }
                else {
                    return new EmptyIterator<Node<E>>();
                }
            }
        };
    }

    public <E> NodeSelector newSelector(final NodeMatcher<E> matcher, final NodeSelector<E> selector) {
        return new NodeSelector<E>() {
            @Override
            public Iterator<Node<E>> select(Tree<E> tree) {
                final Iterator<Node<E>> parents = selector.select(tree);
                return new NodeIterator<>(parents, matcher);
            }
        };
    }

    private static class NodeIterator<E> extends PrefetchingIterator<Node<E>> {
        private final Iterator<Node<E>> parents;
        private final NodeMatcher<E> matcher;
        private Iterator<Node<E>> possibles;

        public NodeIterator(Iterator<Node<E>> parents, NodeMatcher<E> matcher) {
            this.parents = parents;
            this.matcher = matcher;
        }

        protected Node<E> calculateNext() {
            if (possibles == null) {
                final Node<E> next = parents.next();
                possibles = next.getChildren().iterator();
            }

            if (possibles.hasNext()) {
                final Node<E> possible = possibles.next();
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
}
