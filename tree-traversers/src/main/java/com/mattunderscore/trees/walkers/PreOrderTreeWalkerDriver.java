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

package com.mattunderscore.trees.walkers;

import java.util.Iterator;

import com.mattunderscore.trees.traversal.TreeWalker;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Driver for the in-order internal iteration of a tree walker.
 *
 * @author Matt Champion on 31/01/15
 */
public final class PreOrderTreeWalkerDriver {
    public <E, N extends OpenNode<E, N>> void traverseTree(Tree<E, N> tree, TreeWalker<N> walker) {
        walker.onStarted();
        if (tree.isEmpty()) {
            walker.onCompleted();
        }
        else {
            final N node = tree.getRoot();
            accept(node, walker);
            walker.onCompleted();
        }
    }

    private <E, N extends OpenNode<E, N>> void accept(N node, TreeWalker<N> walker) {
        walker.onNode(node);
        final Iterator<? extends N> iterator = node.childIterator();
        if (iterator.hasNext()) {
            walker.onNodeChildrenStarted(node);
            while (iterator.hasNext()) {
                final N child = iterator.next();
                accept(child, walker);

                if (iterator.hasNext()) {
                    walker.onNodeChildrenRemaining(node);
                }
            }
            walker.onNodeChildrenCompleted(node);
        }
        else {
            walker.onNodeNoChildren(node);
        }
    }
}
