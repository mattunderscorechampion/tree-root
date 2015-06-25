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

import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;

/**
 * Iterator over the children of nodes provided by an iterator and filtered by a matcher.
 * @param <E> The element type
 * @param <N> The node type
 */
final class NodeChildrenIterator<E, N extends OpenNode<E, ? extends N>> extends PrefetchingIterator<N> {
    private final Iterator<? extends N> parents;
    private final NodeMatcher<E> matcher;
    private Iterator<? extends N> possibles;

    public NodeChildrenIterator(Iterator<? extends N> parents, NodeMatcher<E> matcher) {
        this.parents = parents;
        this.matcher = matcher;
    }

    protected N calculateNext() {
        if (possibles == null) {
            final N next = parents.next();
            possibles = next.childIterator();
        }

        if (possibles.hasNext()) {
            final N possible = possibles.next();
            if (matcher.matches(possible)) {
                return possible;
            } else {
                return calculateNext();
            }
        }
        else {
            possibles = null;
            return calculateNext();
        }
    }
}
