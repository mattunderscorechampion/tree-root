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

package com.mattunderscore.trees.traversal;

import java.util.stream.Stream;

import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Factory for streams.
 * @author Matt Champion on 29/06/2015
 */
public interface NodeStreamFactory {

    /**
     * Create a stream of nodes from a tree. Following a pre-order traversal pattern.
     * @param tree A tree
     * @param <E> The type of elements
     * @param <N> The type of nodes
     * @return A steam of nodes
     */
    <E, N extends OpenNode<E, N>> Stream<N> preOrderStream(Tree<E, N> tree);

    /**
     * Create a stream of nodes from a tree. Following a in-order traversal pattern.
     * @param tree A tree
     * @param <E> The type of elements
     * @param <N> The type of nodes
     * @return A steam of nodes
     */
    <E, N extends OpenNode<E, N>> Stream<N> inOrderStream(Tree<E, N> tree);

    /**
     * Create a stream of nodes from a tree. Following a post-order traversal pattern.
     * @param tree A tree
     * @param <E> The type of elements
     * @param <N> The type of nodes
     * @return A steam of nodes
     */
    <E, N extends OpenNode<E, N>> Stream<N> postOrderStream(Tree<E, N> tree);

    /**
     * Create a stream of nodes from a tree. Following a breadth-first-order traversal pattern.
     * @param tree A tree
     * @param <E> The type of elements
     * @param <N> The type of nodes
     * @return A steam of nodes
     */
    <E, N extends OpenNode<E, N>> Stream<N> breadthFirstStream(Tree<E, N> tree);
}
