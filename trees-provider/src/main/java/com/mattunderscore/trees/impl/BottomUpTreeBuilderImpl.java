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

package com.mattunderscore.trees.impl;

import java.lang.reflect.Array;

import net.jcip.annotations.Immutable;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.impl.providers.impl.EmptyTreeConstructorProvider;
import com.mattunderscore.trees.impl.providers.impl.KeyMappingProvider;
import com.mattunderscore.trees.impl.providers.impl.TreeConstructorProvider;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * @author Matt Champion on 13/08/14.
 */
@Immutable
final class BottomUpTreeBuilderImpl<E, N extends OpenNode<E, N>> implements BottomUpTreeBuilder<E, N> {
    private final TreeConstructorProvider treeConstructorProvider;
    private final EmptyTreeConstructorProvider emptyTreeConstructorProvider;
    private final KeyMappingProvider keyMappingProvider;
    private final E root;
    private final BottomUpTreeBuilder<E, N>[] children;

    @SuppressWarnings("unchecked")
    public BottomUpTreeBuilderImpl(TreeConstructorProvider treeConstructorProvider, EmptyTreeConstructorProvider emptyTreeConstructorProvider, KeyMappingProvider keyMappingProvider) {
        this(treeConstructorProvider, emptyTreeConstructorProvider, keyMappingProvider, null, new BottomUpTreeBuilder[0]);
    }

    @SuppressWarnings("unchecked")
    private BottomUpTreeBuilderImpl(TreeConstructorProvider treeConstructorProvider, EmptyTreeConstructorProvider emptyTreeConstructorProvider, KeyMappingProvider keyMappingProvider, E e) {
        this(treeConstructorProvider, emptyTreeConstructorProvider, keyMappingProvider, e, new BottomUpTreeBuilder[0]);
    }

    private BottomUpTreeBuilderImpl(TreeConstructorProvider treeConstructorProvider, EmptyTreeConstructorProvider emptyTreeConstructorProvider, KeyMappingProvider keyMappingProvider, E e, BottomUpTreeBuilder<E, N>[] builders) {
        this.treeConstructorProvider = treeConstructorProvider;
        this.emptyTreeConstructorProvider = emptyTreeConstructorProvider;
        this.keyMappingProvider = keyMappingProvider;
        root = e;
        children = builders;
    }

    @Override
    public BottomUpTreeBuilder<E, N> create(E e) {
        return new BottomUpTreeBuilderImpl<>(treeConstructorProvider, emptyTreeConstructorProvider, keyMappingProvider, e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder) {
        return new BottomUpTreeBuilderImpl<>(
            treeConstructorProvider,
            emptyTreeConstructorProvider,
            keyMappingProvider,
            e,
            new BottomUpTreeBuilder[] { builder });
    }

    @SuppressWarnings("unchecked")
    @Override
    public BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder0, BottomUpTreeBuilder<E, N> builder1) {
        return new BottomUpTreeBuilderImpl<>(
            treeConstructorProvider,
            emptyTreeConstructorProvider,
            keyMappingProvider,
            e,
            new BottomUpTreeBuilder[] {
                builder0,
                builder1
            });
    }

    @SuppressWarnings("unchecked")
    @Override
    public BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder0, BottomUpTreeBuilder<E, N> builder1, BottomUpTreeBuilder<E, N> builder2) {
        return new BottomUpTreeBuilderImpl<>(
            treeConstructorProvider,
            emptyTreeConstructorProvider,
            keyMappingProvider,
            e,
            new BottomUpTreeBuilder[] {
                builder0,
                builder1,
                builder2
            });
    }

    @SuppressWarnings("unchecked")
    @Override
    public BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder0, BottomUpTreeBuilder<E, N> builder1, BottomUpTreeBuilder<E, N> builder2, BottomUpTreeBuilder<E, N> builder3) {
        return new BottomUpTreeBuilderImpl<>(
            treeConstructorProvider,
            emptyTreeConstructorProvider,
            keyMappingProvider,
            e,
            new BottomUpTreeBuilder[] {
                builder0,
                builder1,
                builder2,
                builder3
            });
    }

    @SuppressWarnings("unchecked")
    @Override
    public BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N> builder0, BottomUpTreeBuilder<E, N> builder1, BottomUpTreeBuilder<E, N> builder2, BottomUpTreeBuilder<E, N> builder3, BottomUpTreeBuilder<E, N> builder4) {
        return new BottomUpTreeBuilderImpl<>(
            treeConstructorProvider,
            emptyTreeConstructorProvider,
            keyMappingProvider,
            e,
            new BottomUpTreeBuilder[] {
                builder0,
                builder1,
                builder2,
                builder3,
                builder4
            });
    }

    @Override
    @SafeVarargs
    public final BottomUpTreeBuilder<E, N> create(E e, BottomUpTreeBuilder<E, N>... builders) {
        return new BottomUpTreeBuilderImpl<>(
            treeConstructorProvider,
            emptyTreeConstructorProvider,
            keyMappingProvider,
            e,
            builders);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Tree<E, N>> T build(Class<T> klass) throws OperationNotSupportedForType {
        if (root == null) {
            final EmptyTreeConstructor<E, N, T> constructor = emptyTreeConstructorProvider.get(klass);
            return constructor.build();
        }
        else {
            final T[] subtrees = (T[])Array.newInstance(keyMappingProvider.get(klass), children.length);
            for (int i = 0; i < children.length; i++) {
                subtrees[i] = children[i].build(klass);
            }
            final TreeConstructor<E, N, T> constructor = treeConstructorProvider.get(klass);
            return constructor.build(root, subtrees);
        }
    }

    @Override
    public <T extends Tree<E, N>> T build(TypeKey<T> type) throws OperationNotSupportedForType {
        return build(type.getTreeType());
    }
}
