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

package com.mattunderscore.trees.construction;

import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Builder used to create the an empty tree or a non-empty tree.
 * @author Matt Champion on 07/08/14.
 */
public interface BottomUpTreeBuilder<E, N extends OpenNode<E, N>> extends BaseTreeBuilder<E, N, Tree<E, N>> {

    /**
     * @param e the root node
     * @return a new builder that creates a tree containing a single node
     */
    BottomUpTreeBuilder<E, N> create(E e);

    /**
     * @param e the root node
     * @param builder a builder for a subtree
     * @return a new builder that creates a tree containing a single node
     */
    BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder);

    /**
     * @param e the root node
     * @param builder0 a builder for a subtree
     * @param builder1 a builder for a subtree
     * @return a new builder that creates a tree containing a single node
     */
    BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder0, BottomUpTreeBuilder<E, N> builder1);

    /**
     * @param e the root node
     * @param builder0 a builder for a subtree
     * @param builder1 a builder for a subtree
     * @param builder2 a builder for a subtree
     * @return a new builder that creates a tree containing a single node
     */
    BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder0, BottomUpTreeBuilder<E, N> builder1, BottomUpTreeBuilder<E, N> builder2);

    /**
     * @param e the root node
     * @param builder0 a builder for a subtree
     * @param builder1 a builder for a subtree
     * @param builder2 a builder for a subtree
     * @param builder3 a builder for a subtree
     * @return a new builder that creates a tree containing a single node
     */
    BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder0, BottomUpTreeBuilder<E, N> builder1, BottomUpTreeBuilder<E, N> builder2, BottomUpTreeBuilder<E, N> builder3);

    /**
     * @param e the root node
     * @param builder0 a builder for a subtree
     * @param builder1 a builder for a subtree
     * @param builder2 a builder for a subtree
     * @param builder3 a builder for a subtree
     * @param builder4 a builder for a subtree
     * @return a new builder that creates a tree containing a single node
     */
    BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder0, BottomUpTreeBuilder<E, N> builder1, BottomUpTreeBuilder<E, N> builder2, BottomUpTreeBuilder<E, N> builder3, BottomUpTreeBuilder<E, N> builder4);

    /**
     * @param e the root node
     * @param builders builders for subtrees
     * @return a new builder that creates a tree containing the element as the root and the trees returned by the
     * builders as children
     */
    @SuppressWarnings("unchecked")
    BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N>... builders);
}
