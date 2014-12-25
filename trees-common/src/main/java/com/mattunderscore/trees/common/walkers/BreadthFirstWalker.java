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

package com.mattunderscore.trees.common.walkers;

import java.util.ArrayList;
import java.util.List;

import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import net.jcip.annotations.Immutable;

/**
 * @author Matt Champion on 17/08/14.
 */
@Immutable
public final class BreadthFirstWalker {

    public BreadthFirstWalker() {
    }

    public <E, N extends Node<E>, T extends Tree<E, N>> void accept(T tree, Walker<N> walker) {
        if (tree.isEmpty()) {
            walker.onEmpty();
            walker.onCompleted();
        }
        else {
            final N node = tree.getRoot();
            final List<N> rootLevel = new ArrayList<>(1);
            rootLevel.add(node);
            try {
                accept(rootLevel, walker);
                walker.onCompleted();
            }
            catch (Done done) {
                done.printStackTrace();
            }
        }
    }

    private <E, N extends Node<E>, T extends Tree<E, N>> void accept(List<N> currentLevel, Walker<N> walker) throws Done {
        final List<N> nextLevel = new ArrayList<>(currentLevel.size() * 2);
        for (final N node : currentLevel) {
            if (!walker.onNext(node)) {
                throw new Done();
            }
            for (final Node<E> child : node.getChildren()) {
                nextLevel.add((N) child);
            }
        }

        if (nextLevel.size() > 0) {
            accept(nextLevel, walker);
        }
    }
}
