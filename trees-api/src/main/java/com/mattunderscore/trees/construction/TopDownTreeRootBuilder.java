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
 * Builder used to create the root of a new tree or an empty tree.
 * @author Matt Champion on 07/08/14.
 */
public interface TopDownTreeRootBuilder<E, N extends OpenNode<E, N>> extends BaseTreeBuilder<E, N, Tree<E, N>> {

    /**
     * @param e root element
     * @return Builder for a non-empty tree
     */
    TopDownTreeBuilder<E, N> root(E e);

    /**
     * Builder used to create a non-empty tree.
     * @param <S> The element type of the tree that will be built
     */
    interface TopDownTreeBuilder<S, U extends OpenNode<S, U>> extends BaseTreeBuilder<S,U, Tree<S, U>>, TopDownTreeBuilderAppender<S> {
    }

    /**
     * Appended to add a new child to the tree.
     * @param <S> The element type of the tree that will be built
     */
    interface TopDownTreeBuilderAppender<S> extends NodeAppender<S, TopDownTreeBuilderAppender<S>> {
    }
}
