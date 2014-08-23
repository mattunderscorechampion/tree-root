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

package com.mattunderscore.trees.common.traversers;

import com.mattunderscore.trees.INode;
import com.mattunderscore.trees.ITree;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;
import net.jcip.annotations.NotThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author matt on 22/08/14.
 */
@NotThreadSafe
public final class InOrderIterator<E , N extends INode<E>, T extends ITree<E, N>> extends PrefetchingIterator<N> {
    private final Stack<State<E, N>> parents = new Stack<>();
    private N current;

    public InOrderIterator(T tree) {
        current = tree.getRoot();
    }

    @Override
    protected N calculateNext() throws NoSuchElementException {
        while (!parents.isEmpty() || current != null) {
            if (current != null) {
                final State<E, N> state = new State<>(current);
                parents.push(state);
                if (state.iterator.hasNext()) {
                    current = state.iterator.next();
                }
                else {
                    current = null;
                }
            }
            else {
                final State<E, N> state = parents.pop();
                if (state.iterator.hasNext()) {
                    current = state.iterator.next();
                    if (state.iterator.hasNext()) {
                        parents.push(state);
                    }
                }
                return state.node;
            }
        }
        throw new NoSuchElementException();
    }

    private static final class State<E, N extends INode<E>> {
        private final N node;
        private final Iterator<N> iterator;

        public State(N node) {
            this.node = node;
            this.iterator = (Iterator<N>)node.getChildren().iterator();
        }
    }
}
