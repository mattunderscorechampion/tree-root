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

package com.mattunderscore.trees.mutable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.mattunderscore.trees.impl.SPISupport;
import com.mattunderscore.trees.impl.SPISupportAwareComponent;
import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Abstract copying node converter that captures the source and target types from the supertype and delegates to a
 * copying node converter.
 * @author Matt Champion
 */
public abstract class AbstractCopyingNodeConverter<E, N extends OpenNode<E, N>, T extends Tree<E, N>, S extends OpenNode<E, S>> implements NodeToTreeConverter<E, N, T, S>, SPISupportAwareComponent {
    private final DelegateCopyingNodeToTreeConverter<E, ? extends N, T, S> delegateConverter;

    public AbstractCopyingNodeConverter() {
        final Class<S> sourceType;
        final Class<T> targetType;
        final ParameterizedType concreteParameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final Type nodeType = concreteParameterizedType.getActualTypeArguments()[3];
        if (nodeType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) nodeType;
            sourceType = (Class<S>) parameterizedType.getRawType();
        }
        else if (nodeType instanceof Class) {
            sourceType = (Class<S>) nodeType;
        }
        else {
            throw new IllegalStateException("Unable to identify tree class");
        }

        final Type treeType = concreteParameterizedType.getActualTypeArguments()[2];
        if (treeType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) treeType;
            targetType = (Class<T>) parameterizedType.getRawType();
        }
        else if (treeType instanceof Class) {
            targetType = (Class<T>) treeType;
        }
        else {
            throw new IllegalStateException("Unable to identify tree class");
        }

        delegateConverter = new DelegateCopyingNodeToTreeConverter<>(sourceType, targetType);
    }

    @Override
    public final T treeFromRootNode(S node) {
        return delegateConverter.treeFromRootNode(node);
    }

    @Override
    public final Class<S> forClass() {
        return delegateConverter.forClass();
    }

    @Override
    public final void setSupport(SPISupport support) {
        delegateConverter.setSupport(support);
    }
}
