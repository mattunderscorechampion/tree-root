/* Copyright © 2014, 2015 Matthew Champion
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

import java.util.Comparator;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.spi.IteratorRemoveHandler;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * @author Matt Champion on 05/07/2015
 */
public interface SPISupport {
    /**
     * Create an empty tree.
     * @param klass
     * @param <E>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    @SuppressWarnings("unchecked")
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> T createEmptyTree(Class<T> klass) throws OperationNotSupportedForType;

    /**
     * Create an empty tree.
     * @param klass
     * @param <E>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    @SuppressWarnings("unchecked")
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> T createEmptyTree(Class<T> klass, Comparator<E> comparator) throws OperationNotSupportedForType;

    /**
     * Create a tree containing subtrees.
     * @param klass
     * @param e
     * @param subtrees
     * @param <E>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    @SuppressWarnings("unchecked")
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> T newTreeFrom(Class<T> klass, E e, T[] subtrees) throws OperationNotSupportedForType;

    /**
     * Convert from one tree to another.
     * @param klass
     * @param sourceTree
     * @param <E>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    @SuppressWarnings("unchecked")
    <E, N extends OpenNode<E, N>, S extends OpenNode<E, S>, T extends Tree<E, N>> T convertTree(Class<T> klass, Tree<E, S> sourceTree) throws OperationNotSupportedForType;

    /**
     * Convert node to tree.
     * @param node
     * @param <E>
     * @param <N>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    @SuppressWarnings("unchecked")
    <E, N extends OpenNode<E,? extends N>, T extends Tree<E, ? extends N>, S extends OpenNode<E, ? extends S>> T nodeToTree(S node) throws OperationNotSupportedForType;

    @SuppressWarnings("unchecked")
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> IteratorRemoveHandler<E, N, T> lookupHandler(T tree);

    /**
     * @param key A key
     * @return The concrete type used by the key
     */
    @SuppressWarnings("unchecked")
    <T> Class<? extends T> lookupConcreteType(Class<T> key);
}
