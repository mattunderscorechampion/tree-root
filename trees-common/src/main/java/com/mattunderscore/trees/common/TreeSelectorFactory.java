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
import com.mattunderscore.trees.utilities.iterators.EmptyIterator;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;
import com.mattunderscore.trees.utilities.iterators.SingletonIterator;

import java.util.Iterator;

/**
 * @author matt on 29/06/14.
 */
public final class TreeSelectorFactory {
    private final TreeHelper helper = new TreeHelper();

    /**
     * Create a tree selector for the root node (entire tree).
     * @param matcher
     * @param <E>
     * @return
     */
    public <E> TreeSelector newSelector(final NodeMatcher<E> matcher) {
        return new TreeSelector<E>() {
            @Override
            public Iterator<Tree<E>> select(Tree<E> tree) {
                final Node<E> root = tree.getRoot();
                if (matcher.matches(root)) {
                    return new SingletonIterator<>((Tree<E>)helper.treeFromRootNode(root));
                }
                else {
                    return EmptyIterator.get();
                }
            }
        };
    }

    /**
     * Create a node selector for the children of another node selector.
     * @param selector
     * @param matcher
     * @return
     */
    public <E> TreeSelector<E> newSelector(final TreeSelector<E> selector, final NodeMatcher<E> matcher) {
        return new TreeSelector<E>() {
            @Override
            public Iterator<Tree<E>> select(Tree<E> tree) {
                final Iterator<Tree<E>> parents = selector.select(tree);
                return new TreeIterator<>(parents, matcher);
            }
        };
    }

    private final class TreeIterator<E> extends PrefetchingIterator<Tree<E>> {
        private final Iterator<Tree<E>> parents;
        private final NodeMatcher<E> matcher;
        private Iterator<Node<E>> possibles;

        public TreeIterator(Iterator<Tree<E>> parents, NodeMatcher<E> matcher) {
            this.parents = parents;
            this.matcher = matcher;
        }

        protected Tree<E> calculateNext() {
            if (possibles == null) {
                final Node<E> next = parents.next().getRoot();
                possibles = next.getChildren().iterator();
            }

            if (possibles.hasNext()) {
                final Node<E> possible = possibles.next();
                if (matcher.matches(possible)) {
                    return helper.treeFromRootNode(possible);
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
