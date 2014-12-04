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

import com.mattunderscore.trees.tree.Tree;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Type key to support generics when constructing trees.
 * @author Matt Champion
 */
public abstract class TypeKey<T extends Tree<?, ?>> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public TypeKey() {
        final ParameterizedType concreteParameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final Type treeType = concreteParameterizedType.getActualTypeArguments()[0];
        if (treeType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) treeType;
            type = (Class<T>) parameterizedType.getRawType();
        }
        else if (treeType instanceof Class) {
            type = (Class<T>) treeType;
        }
        else {
            throw new IllegalStateException("Unable to identify tree class");
        }
    }

    public final Class<T> getType() {
        return type;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TypeKey typeKey = (TypeKey) o;

        return type.equals(typeKey.type);
    }

    @Override
    public final int hashCode() {
        return type.hashCode();
    }

    @Override
    public final String toString() {
        return "TypeKey:" + type.getName();
    }
}
