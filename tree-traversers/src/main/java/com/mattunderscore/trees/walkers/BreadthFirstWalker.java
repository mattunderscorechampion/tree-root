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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;
import net.jcip.annotations.Immutable;

/**
 * @author Matt Champion on 17/08/14.
 */
@Immutable
public final class BreadthFirstWalker {
    private static final int ESTIMATED_GROWTH_RATE = 2;

    public BreadthFirstWalker() {
    }

    public <E, N extends OpenNode<E, N>> void traverseTree(Tree<E, N> tree, Walker<N> walker) {
        if (tree.isEmpty()) {
            walker.onEmpty();
            walker.onCompleted();
        }
        else {
            List<N> currentLevel = new ArrayList<>(1);
            currentLevel.add(tree.getRoot());
            while (true) {
                final List<N> nextLevel = new ArrayList<>(currentLevel.size() * ESTIMATED_GROWTH_RATE);

                // Traverse current level
                for (final N node : currentLevel) {
                    if (!walker.onNext(node)) {
                        // Stop if walker
                        return;
                    }

                    // Add children to next level
                    final Iterator<? extends N> iterator = node.childIterator();
                    while (iterator.hasNext()) {
                        nextLevel.add(iterator.next());
                    }
                }

                if (nextLevel.size() == 0) {
                    // Reached the first empty level, finished
                    walker.onCompleted();
                    return;
                }
                else {
                    currentLevel = nextLevel;
                }
            }
        }
    }
}
