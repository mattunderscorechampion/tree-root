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

import com.mattunderscore.trees.binary.OpenBinaryTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Matt Champion on 29/04/16
 */
/*package*/ final class IsBalancedQuerier<E, N extends OpenBinaryTreeNode<E, N>> {

    public boolean isBalanced(N node) {
        if (node.isLeaf()) {
            return true;
        }

        final Stack<TraversalState<E, N>> parents = new Stack<>();
        TraversalState<E, N> nextNode = new TraversalState<>(node);

        while (!parents.isEmpty() || nextNode != null) {
            if (nextNode != null) {
                final TraversalState<E, N> state = nextNode;
                parents.push(state);
                if (nextNode.node.getLeft() != null) {
                    nextNode.left = new TraversalState<>(nextNode.node.getLeft());
                    nextNode = nextNode.left;
                    final List<TraversalState<E, N>> newChildren = new ArrayList<>();
                    newChildren.add(nextNode);
                    // GO LEFT
                }
                else {
                    // Leaf
                    nextNode = null;
                }
            }
            else {
                final TraversalState<E, N> parentState = parents.peek();
                if (parentState.node.getRight() != null && parentState.right == null) {
                    parentState.right = new TraversalState<>(parentState.node.getRight());
                    nextNode = parentState.right;
                    // GO RIGHT
                    continue;
                }

                // GO UP
                parents.pop();

                if (parentState.node.isLeaf()) {
                    // No children, is a leaf
                    parentState.height = 0;
                    continue;
                }

                final int leftHeight = parentState.left != null ? parentState.left.height : 0;
                final int rightHeight = parentState.right != null ? parentState.right.height : 0;

                final int heightDifference = Math.abs(leftHeight - rightHeight);

                if (heightDifference > 0) {
                    return false;
                }

                parentState.height = Math.max(leftHeight, rightHeight) + 1;
            }
        }

        return true;
    }

    public static final class TraversalState<E, N extends OpenBinaryTreeNode<E, N>> {
        private final N node;
        private int height = 0;
        private TraversalState<E, N> left;
        private TraversalState<E, N> right;

        @SuppressWarnings("unchecked")
        public TraversalState(N node) {
            this.node = node;
        }
    }
}
