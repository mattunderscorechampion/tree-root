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

package com.mattunderscore.trees.traversal;

import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Factory for walkers over the node and elements of trees.
 * @author Matt Champion on 23/08/14.
 */
public interface TreeWalkerFactory {

    /**
     * Traverse the tree in preorder.
     * @param tree The tree
     * @param walker The walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkPreOrder(T tree, Walker<N> walker);

    /**
     * Traverse the tree in order.
     * @param tree The tree
     * @param walker The walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkInOrder(T tree, Walker<N> walker);

    /**
     * Traverse the tree in post order.
     * @param tree The tree
     * @param walker The walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkPostOrder(T tree, Walker<N> walker);

    /**
     * Traverse the tree in breadth first order.
     * @param tree The tree
     * @param walker The walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkBreadthFirst(T tree, Walker<N> walker);

    /**
     * Traverse the tree elements in preorder.
     * @param tree The tree
     * @param walker The walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsPreOrder(T tree, Walker<E> walker);

    /**
     * Traverse the tree elements in order.
     * @param tree The tree
     * @param walker The walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsInOrder(T tree, Walker<E> walker);

    /**
     * Traverse the tree elements in post order.
     * @param tree The tree
     * @param walker The walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsPostOrder(T tree, Walker<E> walker);

    /**
     * Traverse the tree elements in breadth first order.
     * @param tree The tree
     * @param walker The walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsBreadthFirst(T tree, Walker<E> walker);

    /**
     * Traverse the tree in preorder.
     * @param tree The tree
     * @param walker The tree walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkPreOrder(T tree, TreeWalker<N> walker);

    /**
     * Traverse the tree elements in preorder.
     * @param tree The tree
     * @param walker The tree walker
     * @param <E> Element type
     * @param <N> Node type
     * @param <T> Tree type
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> void walkElementsPreOrder(T tree, TreeWalker<E> walker);

}
