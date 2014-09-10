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

package com.mattunderscore.trees.common;

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.common.walkers.*;
import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.traversal.TreeWalkers;
import net.jcip.annotations.Immutable;

/**
 * @author matt on 23/08/14.
 */
@Immutable
public final class TreeWalkersImpl implements TreeWalkers {
    private final BreadthFirstWalker breadthFirstWalker;
    private final InOrderWalker inOrderWalker;
    private final PostOrderWalker postOrderWalker;
    private final PreOrderWalker preOrderWalker;

    public TreeWalkersImpl() {
        breadthFirstWalker = new BreadthFirstWalker();
        inOrderWalker = new InOrderWalker();
        postOrderWalker = new PostOrderWalker();
        preOrderWalker = new PreOrderWalker();
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, N>> void walkPreOrder(T tree, Walker<N> walker) {
        preOrderWalker.accept(tree, walker);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, N>> void walkInOrder(T tree, Walker<N> walker) {
        inOrderWalker.accept(tree, walker);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, N>> void walkPostOrder(T tree, Walker<N> walker) {
        postOrderWalker.accept(tree, walker);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, N>> void walkBreadthFirst(T tree, Walker<N> walker) {
        breadthFirstWalker.accept(tree, walker);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, N>> void walkElementsPreOrder(T tree, Walker<E> walker) {
        final Walker<N> nodeWalker = new NodeToElementWalker<>(walker);
        preOrderWalker.accept(tree, nodeWalker);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, N>> void walkElementsInOrder(T tree, Walker<E> walker) {
        final Walker<N> nodeWalker = new NodeToElementWalker<>(walker);
        inOrderWalker.accept(tree, nodeWalker);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, N>> void walkElementsPostOrder(T tree, Walker<E> walker) {
        final Walker<N> nodeWalker = new NodeToElementWalker<>(walker);
        postOrderWalker.accept(tree, nodeWalker);
    }

    @Override
    public <E, N extends Node<E>, T extends Tree<E, N>> void walkElementsBreadthFirst(T tree, Walker<E> walker) {
        final Walker<N> nodeWalker = new NodeToElementWalker<>(walker);
        breadthFirstWalker.accept(tree, nodeWalker);
    }
}
