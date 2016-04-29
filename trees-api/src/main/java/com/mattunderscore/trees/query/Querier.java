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

package com.mattunderscore.trees.query;

import com.mattunderscore.simple.collections.EmptySimpleCollection;
import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.trees.binary.OpenBinaryTreeNode;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Provides querying operations.
 * @author Matt Champion on 27/08/2015
 */
public interface Querier {
    /**
     * Find the height of a node.
     * @param node The node to find the height of
     * @param <E> The element type
     * @param <N> The node type
     * @return The height
     * @throws NullPointerException If the node is null
     */
    default <E, N extends OpenNode<E, N>> int height(N node) {
        if (node == null) {
            throw new NullPointerException("A null node cannot have a height");
        }

        return reduce(node, (n, childResults) -> n.isLeaf() ? 0 : Collections.max(childResults) + 1);
    }

    /**
     * Find the height of the root node of a tree.
     * @param tree The tree to find the height of
     * @param <E> The element type
     * @param <N> The node type
     * @return The height
     * @throws IllegalArgumentException If the tree is empty
     */
    default <E, N extends OpenNode<E, N>> int height(Tree<E, N> tree) {
        if (tree.isEmpty()) {
            throw new IllegalArgumentException("An empty tree does not have a height");
        }
        return height(tree.getRoot());
    }

    /**
     * Find all the paths to leaf nodes from the provided node.
     * @param node The starting node
     * @param <E> The element type
     * @param <N> The node type
     * @return A collection of lists. The list starts with the node passed in and ends with a leaf node. The other nodes
     * in the list describe transitions in the path
     * @throws NullPointerException If the node is null
     */
    <E, N extends OpenNode<E, N>> SimpleCollection<List<N>> pathsToLeaves(N node);

    /**
     * Find all the paths to leaf nodes from the root node of the tree.
     * @param tree The node
     * @param <E> The element type
     * @param <N> The node type
     * @return A collection of lists. The list starts with the node passed in and ends with a leaf node. The other nodes
     * in the list describe transitions in the path. Returns an empty collection if the tree is empty.
     */
    default <E, N extends OpenNode<E, N>> SimpleCollection<List<N>> pathsToLeaves(Tree<E, N> tree) {
        if (tree.isEmpty()) {
            return new EmptySimpleCollection<>();
        }
        return pathsToLeaves(tree.getRoot());
    }

    /**
     * Test if the subtree starting at the node is balanced.
     * @param node The node to test
     * @param <E> The element type
     * @param <N> The node type
     * @return If is balanced
     * @throws NullPointerException If the node is null
     */
    <E, N extends OpenBinaryTreeNode<E, N>> boolean isBalanced(N node);

    /**
     * Test if the trees is balanced.
     * @param tree The tree to test
     * @param <E> The element type
     * @param <N> The node type
     * @return If is balanced
     * @throws IllegalArgumentException If the tree is empty
     */
    default <E, N extends OpenBinaryTreeNode<E, N>> boolean isBalanced(Tree<E, N> tree) {
        if (tree.isEmpty()) {
            throw new IllegalArgumentException("An empty tree cannot be balanced");
        }
        return isBalanced(tree.getRoot());
    }

    /**
     * Reduce the subtree starting at a node. Perform a post order traversal of the tree. Apply the reducer to the node
     * and the results of applying the reducer to the nodes children. The reduction can be stopped early.
     * @param node The node to start the reduction at
     * @param reducer The reducer to apply
     * @param <E> The element type
     * @param <N> The node type
     * @param <R> The type of result
     * @return The result
     * @throws NullPointerException If the node is null
     */
    <E, N extends OpenNode<E, N>, R> R partialReduce(N node, BiFunction<N, Collection<R>, ReductionResult<R>> reducer);

    /**
     * Reduce the subtree starting at a node. Perform a post order traversal of the tree. Apply the reducer to the node
     * and the results of applying the reducer to the nodes children. The reduction can be stopped early.
     * @param tree The tree to reduce
     * @param reducer The reducer to apply
     * @param <E> The element type
     * @param <N> The node type
     * @param <R> The type of result
     * @return The result
     * @throws IllegalArgumentException If the tree is empty
     */
    default <E, N extends OpenNode<E, N>, R> R partialReduce(
            Tree<E, N> tree,
            BiFunction<N, Collection<R>, ReductionResult<R>> reducer) {
        if (tree.isEmpty()) {
            throw new IllegalArgumentException("An empty tree cannot be balanced");
        }

        return partialReduce(tree.getRoot(), reducer);
    }

    /**
     * Reduce the subtree starting at a node. Perform a post order traversal of the tree. Apply the reducer to the node
     * and the results of applying the reducer to the nodes children.
     * @param node The node to start the reduction at
     * @param reducer The reducer to apply
     * @param <E> The element type
     * @param <N> The node type
     * @param <R> The type of result
     * @return The result
     * @throws NullPointerException If the node is null
     */
    default <E, N extends OpenNode<E, N>, R> R reduce(N node, BiFunction<N, Collection<R>, R> reducer) {
        return partialReduce(node, new PartialToTotalReductionResultAdapter<>(reducer));
    }

    /**
     * Reduce the subtree starting at a node. Perform a post order traversal of the tree. Apply the reducer to the node
     * and the results of applying the reducer to the nodes children.
     * @param tree The tree to reduce
     * @param reducer The reducer to apply
     * @param <E> The element type
     * @param <N> The node type
     * @param <R> The type of result
     * @return The result
     * @throws IllegalArgumentException If the tree is empty
     */
    default <E, N extends OpenNode<E, N>, R> R reduce(Tree<E, N> tree, BiFunction<N, Collection<R>, R> reducer) {
        if (tree.isEmpty()) {
            throw new IllegalArgumentException("An empty tree cannot be balanced");
        }

        return partialReduce(tree.getRoot(), new PartialToTotalReductionResultAdapter<>(reducer));
    }
}
