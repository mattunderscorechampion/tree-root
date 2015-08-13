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

package com.mattunderscore.trees.spi.impl;

import java.util.Iterator;

import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.spi.NodeToRelatedTreeConverter;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Abstract implementation for converting a node to a tree by copying the subtree.
 *
 * @author Matt Champion on 24/06/15.
 */
public abstract class AbstractNodeToRelatedTreeConverter<E, N extends OpenNode<E, N>, T extends Tree<E, N>> implements NodeToRelatedTreeConverter<E, N, T> {
    private final Class<N> targetNodeClass;
    private final Class<T> targetTreeClass;

    @SuppressWarnings("unchecked")
    public AbstractNodeToRelatedTreeConverter(Class<?> targetNodeClass, Class<?> targetTreeClass) {
        this.targetNodeClass = (Class<N>) targetNodeClass;
        this.targetTreeClass = (Class<T>) targetTreeClass;
    }

    @Override
    public T treeFromRootNode(N node) {
        final TopDownTreeRootBuilder<E, N> topDownTreeRootBuilder = getBuilder();
        final TopDownTreeRootBuilder.TopDownTreeBuilder<E, N> treeBuilder =
            topDownTreeRootBuilder.root(node.getElement());

        copyChildren(treeBuilder, node);
        return treeBuilder.build(targetTreeClass);
    }

    private void copyChildren(TopDownTreeRootBuilder.TopDownTreeBuilderAppender<E> appender, N node) {
        final Iterator<? extends N> iterator = node.childIterator();
        while (iterator.hasNext()) {
            final N child = iterator.next();
            final TopDownTreeRootBuilder.TopDownTreeBuilderAppender<E> newAppender =
                appender.addChild(child.getElement());
            copyChildren(newAppender, child);
        }
    }

    @Override
    public Class<? extends N> forClass() {
        return targetNodeClass;
    }

    /**
     * @return A builder for the root of the target tree
     */
    protected abstract TopDownTreeRootBuilder<E, N> getBuilder();
}
