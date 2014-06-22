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

/**
 * Constructs trees from the top down.
 * @author matt on 21/06/14.
 */
public interface TreeBuilder<E> {

    /**
     * Sets the root node. Can only be called once.
     * @param e The element to set as root
     * @return A {@link NodeAppender} for the root node
     * @throws IllegalStateException If called more than once
     */
    NodeAppender<E> root(E e) throws IllegalStateException;

    /**
     * @return The tree
     * @throws IllegalStateException If called before root
     */
    Tree<E> build() throws IllegalStateException;

    /**
     * Each {@link NodeAppender} is associated with a node of the tree. It allows children to be added to that node.
     */
    public interface NodeAppender<R> {

        /**
         * Append a child node to the position
         * @param e The element to add as a child
         * @return A {@link NodeAppender} for the child node
         */
        NodeAppender<R> addChild(R e);
    }
}
