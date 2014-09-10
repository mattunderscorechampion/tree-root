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

package com.mattunderscore.trees.common.traversers;

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.traversal.TreeIterators;

import java.util.Iterator;

/**
 * Implementation of {@link com.mattunderscore.trees.traversal.TreeIterators}.
 * @author matt on 10/09/14.
 */
public final class TreeIteratorsImpl implements TreeIterators {
    @Override
    public <E, N extends Node<E>> Iterator<N> preOrderIterator(Tree<E, N> tree) {
        return new PreOrderIterator<>(tree);
    }

    @Override
    public <E, N extends Node<E>> Iterator<N> inOrderIterator(Tree<E, N> tree) {
        return new InOrderIterator<>(tree);
    }

    @Override
    public <E, N extends Node<E>> Iterator<N> postOrderIterator(Tree<E, N> tree) {
        return new PostOrderIterator<>(tree);
    }

    @Override
    public <E, N extends Node<E>> Iterator<N> breadthFirstIterator(Tree<E, N> tree) {
        return new BreadthFirstIterator<>(tree);
    }

    @Override
    public <E, N extends Node<E>> Iterator<E> preOrderElementsIterator(Tree<E, N> tree) {
        return new NodeToElementIterators<>(new PreOrderIterator<>(tree));
    }

    @Override
    public <E, N extends Node<E>> Iterator<E> inOrderElementsIterator(Tree<E, N> tree) {
        return new NodeToElementIterators<>(new InOrderIterator<>(tree));
    }

    @Override
    public <E, N extends Node<E>> Iterator<E> postOrderElementsIterator(Tree<E, N> tree) {
        return new NodeToElementIterators<>(new PostOrderIterator<>(tree));
    }

    @Override
    public <E, N extends Node<E>> Iterator<E> breadthFirstElementsIterator(Tree<E, N> tree) {
        return new NodeToElementIterators<>(new BreadthFirstIterator<>(tree));
    }
}
