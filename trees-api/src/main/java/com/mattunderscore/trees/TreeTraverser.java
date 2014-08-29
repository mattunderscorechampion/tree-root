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

package com.mattunderscore.trees;

import java.util.Iterator;

/**
 * @author matt on 08/08/14.
 */
public interface TreeTraverser {

    /**
     * @param tree
     * @param <E>
     * @param <T>
     * @return An {@link Iterator} that returns nodes in pre-order
     */
    <E, T extends Node<E>> Iterator<T> preOrderIterator(Tree<E, T> tree);

    /**
     * @param tree
     * @param <E>
     * @param <T>
     * @return An {@link Iterator} that returns nodes in in-order
     */
    <E, T extends Node<E>> Iterator<T> inOrderIterator(Tree<E, T> tree);

    /**
     * @param tree
     * @param <E>
     * @param <T>
     * @return An {@link Iterator} that returns nodes in post-order
     */
    <E, T extends Node<E>> Iterator<T> postOrderIterator(Tree<E, T> tree);

    /**
     * @param tree
     * @param <E>
     * @param <T>
     * @return An {@link Iterator} that returns nodes in breadth first order
     */
    <E, T extends Node<E>> Iterator<T> breadthFirstIterator(Tree<E, T> tree);
}
