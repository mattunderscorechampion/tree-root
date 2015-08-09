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

package com.mattunderscore.trees.walkers;

import java.util.Stack;

import net.jcip.annotations.Immutable;

import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.traversers.TraversalState;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Driver for walkers that traverses the tree in post-order.
 * @author Matt Champion on 17/08/14.
 */
@Immutable
public final class PostOrderWalkerDriver {

    public PostOrderWalkerDriver() {
    }

    public <E, N extends OpenNode<E, N>> void traverseTree(Tree<E, N> tree, Walker<N> walker) {
        if (tree.isEmpty()) {
            walker.onEmpty();
            walker.onCompleted();
        }
        else {
            final Stack<TraversalState<E, N>> parents = new Stack<>();
            N current = tree.getRoot();

            while (!parents.isEmpty() || current != null) {
                if (current != null) {
                    final TraversalState<E, N> state = new TraversalState<>(current);
                    parents.push(state);
                    if (state.hasNextChild()) {
                        current = state.nextChild();
                    }
                    else {
                        // Leaf
                        current = null;
                    }
                }
                else {
                    final TraversalState<E, N> state = parents.peek();
                    if (state.hasNextChild()) {
                        current = state.nextChild();
                        continue;
                    }

                    if (!state.hasNextChild()) {
                        parents.pop();
                    }

                    if (!walker.onNext(state.getNode())) {
                        return;
                    }
                }
            }

            walker.onCompleted();
        }
    }
}
