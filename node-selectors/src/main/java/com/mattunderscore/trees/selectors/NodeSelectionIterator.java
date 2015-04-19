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

package com.mattunderscore.trees.selectors;

import java.util.Iterator;

import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;
import com.mattunderscore.trees.wrappers.SimpleTreeWrapper;

/**
 * Iterator over the selected nodes of nodes provided by an iterator and filtered by a selector.
 * @param <E> The element type
 * @param <N> The node type
 */
final class NodeSelectionIterator<E, N extends Node<E>> extends PrefetchingIterator<N> {
    private final Iterator<N> startingPoints;
    private final NodeSelector<E> selector;
    private Iterator<N> currentEndPoints;

    public NodeSelectionIterator(Iterator<N> startingPoints, NodeSelector<E> selector) {
        this.startingPoints = startingPoints;
        this.selector = selector;
    }

    protected N calculateNext() {
        if (currentEndPoints == null) {
            // Creating a tree using the simple tree wrapper does not correctly preserve the properties of the tree
            // but as this is a read only selection/traversal operation no properties should be violated.
            // Additionally it should not be permitted to return copies of the nodes you are selecting as they are
            // not found in the tree you are selecting from violating the principle of least surprise..
            final Tree<E, N> tree = new SimpleTreeWrapper<>(startingPoints.next());
            currentEndPoints = selector.select(tree);
        }

        if (currentEndPoints.hasNext()) {
            return currentEndPoints.next();
        }
        else {
            currentEndPoints = null;
            return calculateNext();
        }
    }
}
