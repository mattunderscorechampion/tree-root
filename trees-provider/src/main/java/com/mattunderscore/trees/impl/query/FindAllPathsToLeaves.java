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

package com.mattunderscore.trees.impl.query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.simple.collections.WrappingSimpleCollection;
import com.mattunderscore.trees.tree.OpenNode;

/**
 * Find all the paths from a node that end at a leaf.
 * @author Matt Champion on 27/08/2015
 */
public final class FindAllPathsToLeaves {
    private FindAllPathsToLeaves() {
    }

    public static <E, N extends OpenNode<E, N>> SimpleCollection<List<N>> paths(N startingNode) {
        final Stack<BackPath<E, N>> parents = new Stack<>();
        final Set<BackPath<E, N>> backPaths = new HashSet<>();

        BackPath<E, N> current = new BackPath<>(null, startingNode);
        parents.push(current);

        // Preorder traversal of tree constructing back paths
        while (!parents.isEmpty()) {
            final BackPath<E, N> n = current;
            final N[] reversed = (N[]) Array.newInstance(n.node.getClass(), n.node.getNumberOfChildren());
            final Iterator<? extends N> childIterator = n.node.childIterator();
            for (int i = n.node.getNumberOfChildren() - 1; i >= 0; i--) {
                reversed[i] = childIterator.next();
            }
            for (final N child : reversed) {
                parents.push(new BackPath<>(n, child));
            }
            do {
                current = parents.pop();
            } while (current == null);
            if (current.node.isLeaf()) {
                backPaths.add(current);
            }
        }

        final Set<List<N>> paths = new HashSet<>();
        for (final BackPath<E, N> backPath : backPaths) {
            paths.add(toPath(backPath));
        }

        return new WrappingSimpleCollection<>(paths);
    }

    /**
     * Turn a back path into a path. Effectively reverse order.
     */
    private static <E, N extends OpenNode<E, N>> List<N> toPath(BackPath<E, N> backPath) {
        BackPath<E, N>  currentPath = backPath;
        final Stack<N> nodes = new Stack<>();
        nodes.push(currentPath.node);
        while (currentPath.parent != null) {
            currentPath = currentPath.parent;
            nodes.push(currentPath.node);
        }

        final List<N> path = new ArrayList<>();
        while (!nodes.isEmpty()) {
            path.add(nodes.pop());
        }

        return path;
    }

    /**
     * Represent a path in reverse order from a leaf back to the node.
     */
    private static final class BackPath<E, N extends OpenNode<E, N>> {
        private final BackPath<E, N> parent;
        private final N node;

        private BackPath(BackPath<E, N> parent, N node) {
            this.parent = parent;
            this.node = node;
        }
    }
}
