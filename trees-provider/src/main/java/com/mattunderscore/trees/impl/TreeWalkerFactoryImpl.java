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

import net.jcip.annotations.Immutable;

import com.mattunderscore.trees.traversal.TreeWalker;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;
import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.walkers.BreadthFirstWalker;
import com.mattunderscore.trees.walkers.InOrderWalker;
import com.mattunderscore.trees.walkers.NodeToElementTreeWalker;
import com.mattunderscore.trees.walkers.NodeToElementWalker;
import com.mattunderscore.trees.walkers.PostOrderWalker;
import com.mattunderscore.trees.walkers.PreOrderTreeWalkerDriver;
import com.mattunderscore.trees.walkers.PreOrderWalker;

/**
 * @author Matt Champion on 23/08/14.
 */
@Immutable
public final class TreeWalkerFactoryImpl implements TreeWalkerFactory {
    private final BreadthFirstWalker breadthFirstWalker;
    private final InOrderWalker inOrderWalker;
    private final PostOrderWalker postOrderWalker;
    private final PreOrderWalker preOrderWalker;
    private final PreOrderTreeWalkerDriver preOrderTreeWalkerDriver;

    public TreeWalkerFactoryImpl() {
        breadthFirstWalker = new BreadthFirstWalker();
        inOrderWalker = new InOrderWalker();
        postOrderWalker = new PostOrderWalker();
        preOrderWalker = new PreOrderWalker();
        preOrderTreeWalkerDriver = new PreOrderTreeWalkerDriver();
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkPreOrder(T tree, Walker<N> walker) {
        preOrderWalker.traverseTree(tree, walker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkInOrder(T tree, Walker<N> walker) {
        inOrderWalker.traverseTree(tree, walker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkPostOrder(T tree, Walker<N> walker) {
        postOrderWalker.traverseTree(tree, walker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkBreadthFirst(T tree, Walker<N> walker) {
        breadthFirstWalker.traverseTree(tree, walker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsPreOrder(T tree, Walker<E> walker) {
        final Walker<N> nodeWalker = new NodeToElementWalker<>(walker);
        preOrderWalker.traverseTree(tree, nodeWalker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsInOrder(T tree, Walker<E> walker) {
        final Walker<N> nodeWalker = new NodeToElementWalker<>(walker);
        inOrderWalker.traverseTree(tree, nodeWalker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsPostOrder(T tree, Walker<E> walker) {
        final Walker<N> nodeWalker = new NodeToElementWalker<>(walker);
        postOrderWalker.traverseTree(tree, nodeWalker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsBreadthFirst(T tree, Walker<E> walker) {
        final Walker<N> nodeWalker = new NodeToElementWalker<>(walker);
        breadthFirstWalker.traverseTree(tree, nodeWalker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkPreOrder(T tree, TreeWalker<N> walker) {
        preOrderTreeWalkerDriver.traverseTree(tree, walker);
    }

    @Override
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsPreOrder(T tree, TreeWalker<E> walker) {
        final TreeWalker<N> nodeWalker = new NodeToElementTreeWalker<>(walker);
        preOrderTreeWalkerDriver.traverseTree(tree, nodeWalker);
    }
}
