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

package com.mattunderscore.trees.impl;

import java.util.Iterator;

import com.mattunderscore.trees.traversers.BreadthFirstIterator;
import com.mattunderscore.trees.traversers.InOrderIterator;
import com.mattunderscore.trees.traversers.NodeToElementIterators;
import com.mattunderscore.trees.traversers.PostOrderIterator;
import com.mattunderscore.trees.traversers.PreOrderIterator;
import com.mattunderscore.trees.spi.IteratorRemoveHandler;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation of {@link com.mattunderscore.trees.traversal.TreeIteratorFactory}.
 * @author Matt Champion on 10/09/14.
 */
public final class TreeIteratorFactoryImpl implements TreeIteratorFactory {
    private final SPISupport support;

    public TreeIteratorFactoryImpl(SPISupport support) {
        this.support = support;
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, ? extends N>> Iterator<N> preOrderIterator(T tree) {
        final IteratorRemoveHandler<E, N, T> handler = support.lookupHandler(tree);
        return new PreOrderIterator<>(tree, handler);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, ? extends N>> Iterator<N> inOrderIterator(T tree) {
        final IteratorRemoveHandler<E, N, T> handler = support.lookupHandler(tree);
        return new InOrderIterator<>(tree, handler);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, ? extends N>> Iterator<N> postOrderIterator(T tree) {
        final IteratorRemoveHandler<E, N, T> handler = support.lookupHandler(tree);
        return new PostOrderIterator<>(tree, handler);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, ? extends N>> Iterator<N> breadthFirstIterator(T tree) {
        final IteratorRemoveHandler<E, N, T> handler = support.lookupHandler(tree);
        return new BreadthFirstIterator<>(tree, handler);
    }

    @Override
    public <E, N extends Node<E>> Iterator<E> preOrderElementsIterator(Tree<E, N> tree) {
        return new NodeToElementIterators<>(new PreOrderIterator<>(tree, support.lookupHandler(tree)));
    }

    @Override
    public <E, N extends Node<E>> Iterator<E> inOrderElementsIterator(Tree<E, N> tree) {
        return new NodeToElementIterators<>(new InOrderIterator<>(tree, support.lookupHandler(tree)));
    }

    @Override
    public <E, N extends Node<E>> Iterator<E> postOrderElementsIterator(Tree<E, N> tree) {
        return new NodeToElementIterators<>(new PostOrderIterator<>(tree, support.lookupHandler(tree)));
    }

    @Override
    public <E, N extends Node<E>> Iterator<E> breadthFirstElementsIterator(Tree<E, N> tree) {
        return new NodeToElementIterators<>(new BreadthFirstIterator<>(tree, support.lookupHandler(tree)));
    }
}
