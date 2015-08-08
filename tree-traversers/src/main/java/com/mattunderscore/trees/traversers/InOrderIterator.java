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

package com.mattunderscore.trees.traversers;

import java.util.NoSuchElementException;
import java.util.Stack;

import net.jcip.annotations.NotThreadSafe;

import com.mattunderscore.trees.spi.IteratorRemoveHandler;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * @author Matt Champion on 22/08/14.
 */
@NotThreadSafe
public final class InOrderIterator<E , N extends OpenNode<E, N>, T extends Tree<E, N>> extends RemoveHandlerIterator<E, N, T> {
    private final Stack<TraversalState<E, N>> parents = new Stack<>();
    private N current;

    public InOrderIterator(T tree, IteratorRemoveHandler<E, N, T> handler) {
        super(tree, handler);
        current = tree.getRoot();
    }

    @Override
    protected N calculateNext() throws NoSuchElementException {
        while (!parents.isEmpty() || current != null) {
            if (current != null) {
                final TraversalState<E, N> state = new TraversalState<>(current);
                parents.push(state);
                if (state.hasNextChild()) {
                    current = state.nextChild();
                }
                else {
                    current = null;
                }
            }
            else {
                final TraversalState<E, N> state = parents.peek();
                if (state.hasNextChild()) {
                    current = state.nextChild();
                }
                if (!state.hasNextChild()) {
                    parents.pop();
                }
                return state.getNode();
            }
        }
        throw new NoSuchElementException();
    }
}
