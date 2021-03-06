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

package com.mattunderscore.trees.selection;

import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.tree.Tree;

import java.util.Iterator;

/**
 * Selects a subtree.
 * @author Matt Champion on 08/08/14.
 * @param <E> The type of the elements in the node
 */
public interface TreeSelector<E> {

    /**
     * @param tree The tree to select from
     * @param <N> The type of nodes in the tree
     * @param <T> The type of the tree
     * @return An {@link java.util.Iterator} over the selected subtrees
     * @throws OperationNotSupportedForType if the type of the tree can not be selected
     */
    <N extends OpenNode<E, ? extends N>, T extends Tree<E, ? extends N>> Iterator<T> select(T tree) throws OperationNotSupportedForType;

    /**
     * @param tree The tree to select from
     * @param newTreeType The class of the new subtree
     * @param <N> The type of nodes in the tree
     * @param <T> The type of the tree
     * @param <O> The type of nodes in the subtree
     * @param <U> The type of the subtree
     * @return An {@link java.util.Iterator} over the selected subtrees
     * @throws OperationNotSupportedForType if the type of the tree can not be selected
     */
    <N extends OpenNode<E, ? extends N>, T extends Tree<E, ? extends N>, O extends OpenNode<E, O>, U extends Tree<E, O>> Iterator<U> select(T tree, Class<U> newTreeType) throws OperationNotSupportedForType;
}
