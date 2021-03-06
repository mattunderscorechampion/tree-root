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

import com.mattunderscore.trees.tree.OpenNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represent a path in reverse order from a leaf back to the node.
 */
/*package*/ final class BackPath<E, N extends OpenNode<E, N>> {
    private final BackPath<E, N> parent;
    private final N node;

    BackPath(BackPath<E, N> parent, N node) {
        this.parent = parent;
        this.node = node;
    }

    /**
     * @return The parent back path
     */
    public BackPath<E, N> getParent() {
        return parent;
    }

    /**
     * @return The node
     */
    public N getNode() {
        return node;
    }

    /**
     * Turn a back path into a path. Effectively reverse order.
     */
    public List<N> toPath() {
        BackPath<E, N>  currentPath = this;
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
}
