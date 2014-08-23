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

import com.mattunderscore.trees.INode;
import com.mattunderscore.trees.ITree;
import com.mattunderscore.trees.ITreeWalker;
import com.mattunderscore.trees.utilities.FixedUncheckedList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author matt on 17/08/14.
 */
public final class BreadthFirstWalker<E, N extends INode<E>, T extends ITree<E, N>> {
    private final T tree;
    private final ITreeWalker.Visitor<E, N> visitor;

    public BreadthFirstWalker(T tree, ITreeWalker.Visitor<E, N> visitor) {
        this.tree = tree;
        this.visitor = visitor;
    }

    public void accept() {
        final N node = tree.getRoot();
        if (node == null) {
            visitor.onEmpty();
            visitor.onCompleted();
        }
        else {
            final List<N> rootLevel = new FixedUncheckedList<>(new Object[]{node});
            accept(rootLevel);
            visitor.onCompleted();
        }
    }

    private void accept(List<N> currentLevel) {
        final List<N> nextLevel = new ArrayList<>(currentLevel.size() * 2);
        for (final N node : currentLevel) {
            visitor.onNext(node);
            nextLevel.addAll((Collection<N>)node.getChildren());
        }
        accept(nextLevel);
    }
}
