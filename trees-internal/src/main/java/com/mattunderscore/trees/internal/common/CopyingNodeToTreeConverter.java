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

package com.mattunderscore.trees.internal.common;

import com.mattunderscore.trees.construction.NodeAppender;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder.TopDownTreeBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder.TopDownTreeBuilderAppender;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation for converting a node to a tree by copying the subtree.
 * @author Matt Champion on 27/09/14.
 */
public final class CopyingNodeToTreeConverter<E, N extends Node<E>, T extends Tree<E, N>, S extends Node<E>> implements NodeToTreeConverter<E, N, T, S> {
    private final Class<? extends Tree<E, S>> sourceClass;
    private final Class<T> targetClass;
    private final TreeBuilderFactory treeBuilderFactory;

    protected CopyingNodeToTreeConverter(Class<? extends Tree<E, S>> sourceClass, Class<T> targetClass, TreeBuilderFactory treeBuilderFactory) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        this.treeBuilderFactory = treeBuilderFactory;
    }

    @Override
    public T treeFromRootNode(S node) {
        final TopDownTreeRootBuilder<E> topDownTreeRootBuilder = treeBuilderFactory.topDownBuilder();
        final TopDownTreeBuilder<E> treeBuilder = topDownTreeRootBuilder.root(node.getElement());

        copyChildren(treeBuilder, node);
        return treeBuilder.build(targetClass);
    }

    private void copyChildren(TopDownTreeBuilderAppender<E> appender, S node) {
        for (Node<E> child : node.getChildren()) {
            final TopDownTreeBuilderAppender<E> newAppender = appender.addChild(node.getElement());
            copyChildren(newAppender, (S)child);
        }
    }

    @Override
    public Class<?> forClass() {
        return sourceClass;
    }
}
