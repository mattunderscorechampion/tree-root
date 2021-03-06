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

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.impl.providers.impl.EmptyTreeConstructorProvider;
import com.mattunderscore.trees.impl.providers.impl.TreeConverterProvider;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * @author Matt Champion on 15/08/14.
 */
final class TopDownTreeRootBuilderImpl<E, N extends OpenNode<E, N>> implements TopDownTreeRootBuilder<E, N> {
    private final EmptyTreeConstructorProvider emptyTreeConstructorProvider;
    private final TreeConverterProvider treeConverterProvider;

    public TopDownTreeRootBuilderImpl(TreeConverterProvider treeConverterProvider, EmptyTreeConstructorProvider emptyTreeConstructorProvider) {
        this.treeConverterProvider = treeConverterProvider;
        this.emptyTreeConstructorProvider = emptyTreeConstructorProvider;
    }

    @Override
    public TopDownTreeBuilder<E, N> root(E e) {
        return new TopDownTreeBuilderImpl<>(treeConverterProvider, e);
    }

    @Override
    public <T extends Tree<E, N>> T build(Class<T> klass) throws OperationNotSupportedForType {
        final EmptyTreeConstructor<E, N, T> constructor = emptyTreeConstructorProvider.get(klass);
        return constructor.build();
    }

    @Override
    public <T extends Tree<E, N>> T build(TypeKey<T> type) throws OperationNotSupportedForType {
        return build(type.getTreeType());
    }
}
