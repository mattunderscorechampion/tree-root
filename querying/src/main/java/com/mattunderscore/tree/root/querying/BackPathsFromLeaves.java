/* Copyright © 2016 Matthew Champion
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

package com.mattunderscore.tree.root.querying;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.simple.collections.WrappingSimpleCollection;
import com.mattunderscore.trees.tree.OpenNode;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Querier to get the back paths from the leaves of a node to some other node.
 * @author Matt Champion on 27/04/16
 */
/*package*/ final class BackPathsFromLeaves {

    private BackPathsFromLeaves() {
    }

    public static <E, N extends OpenNode<E, N>> Set<BackPath<E, N>> backPathsFromLeavesOf(N node) {
        if (node == null) {
            throw new NullPointerException("Null has no paths");
        }

        final Stack<BackPath<E, N>> parents = new Stack<>();
        final Set<BackPath<E, N>> backPaths = new HashSet<>();

        BackPath<E, N> current = new BackPath<>(null, node);
        parents.push(current);

        // Preorder traversal of tree constructing back paths
        while (!parents.isEmpty()) {
            final BackPath<E, N> n = current;
            final N[] reversed = (N[]) Array.newInstance(n.getNode().getClass(), n.getNode().getNumberOfChildren());
            final Iterator<? extends N> childIterator = n.getNode().childIterator();
            for (int i = n.getNode().getNumberOfChildren() - 1; i >= 0; i--) {
                reversed[i] = childIterator.next();
            }
            for (final N child : reversed) {
                parents.push(new BackPath<>(n, child));
            }
            do {
                current = parents.pop();
            } while (current == null);
            if (current.getNode().isLeaf()) {
                backPaths.add(current);
            }
        }

        return backPaths;
    }
}
