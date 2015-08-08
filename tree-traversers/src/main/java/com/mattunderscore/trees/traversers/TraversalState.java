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

package com.mattunderscore.trees.traversers;

import java.util.Iterator;

import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.OpenStructuralNode;

/**
 * Traversal state.
 * <p>
 * Keeps track of the children of a node that have not yet been traversed.
 *
 * @author Matt Champion on 08/08/2015
 */
public final class TraversalState<E, N extends OpenNode<E, N>> {
    private final N node;
    private final Iterator<? extends N> children;

    @SuppressWarnings("unchecked")
    public TraversalState(N node) {
        this.node = node;
        if (node instanceof OpenStructuralNode) {
            final OpenStructuralNode structuralNode = (OpenStructuralNode) node;
            this.children = structuralNode.childStructuralIterator();
        }
        else {
            this.children = node.childIterator();
        }
    }

    /**
     * @return The current node
     */
    public N getNode() {
        return node;
    }

    /**
     * @return If the node has any untraversed children
     */
    public boolean hasNextChild() {
        return children.hasNext();
    }

    /**
     * @return The next child to traverse
     */
    public N nextChild() {
        return children.next();
    }
}
