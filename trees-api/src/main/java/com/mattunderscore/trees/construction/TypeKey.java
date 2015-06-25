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
    private final Class<T> treeType;
    private final Class<?> elementType;
    private final Class<?> nodeType;

    @SuppressWarnings("unchecked")
    public TypeKey() {
        final ParameterizedType concreteParameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final Type treeType = concreteParameterizedType.getActualTypeArguments()[0];
        if (treeType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) treeType;
            this.treeType = (Class<T>) parameterizedType.getRawType();

            final Type[] treeTypeArguments = parameterizedType.getActualTypeArguments();
            if (treeTypeArguments.length == 2) {
                final Type elementType = treeTypeArguments[0];
                if (elementType instanceof ParameterizedType) {
                    final ParameterizedType parameterizedElementType = (ParameterizedType) elementType;
                    this.elementType = (Class<?>) parameterizedElementType.getRawType();
                }
                else if (elementType instanceof Class) {
                    this.elementType = (Class<?>) elementType;
                }
                else {
                    this.elementType = null;
                }

                final Type nodeType = treeTypeArguments[1];
                if (nodeType instanceof ParameterizedType) {
                    final ParameterizedType parameterizedNodeType = (ParameterizedType) nodeType;
                    this.nodeType = (Class<?>) parameterizedNodeType.getRawType();
                }
                else if (nodeType instanceof Class) {
                    this.nodeType = (Class<?>) nodeType;
                }
                else {
                    this.nodeType = null;
                }
            }
            else {
                this.elementType = null;
                this.nodeType = null;
            }
        }
        else if (treeType instanceof Class) {
            this.treeType = (Class<T>) treeType;

            this.elementType = null;
            this.nodeType = null;
        }
        else {
            throw new IllegalStateException("Unable to identify tree class");
        }
    }

    public final Class<T> getTreeType() {
        return treeType;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        else if (o == null || !(o instanceof TypeKey)) {
            return false;
        }
        else {
            final TypeKey typeKey = (TypeKey) o;
            return treeType.equals(typeKey.treeType) &&
                ((elementType == null && typeKey.elementType == null) ||
                    (elementType != null && elementType.equals(typeKey.elementType))) &&
                ((nodeType == null && typeKey.nodeType == null) ||
                    (nodeType != null && nodeType.equals(typeKey.nodeType)));
        }
    }

    @Override
    public final int hashCode() {
        int result = treeType.hashCode();
        result = result * 31 + (elementType == null ? 0 : elementType.hashCode());
        result = result * 31 + (nodeType == null ? 0 : nodeType.hashCode());
        return result;
    }

    @Override
    public final String toString() {
        if (elementType == null && nodeType == null) {
            return "TypeKey: " + treeType.getName();
        }
        else if (nodeType == null) {
            return "TypeKey: " + treeType.getName() + " : unknown : " + elementType.getName();
        }
        else if (elementType == null) {
            return "TypeKey: " + treeType.getName() + " : " + nodeType.getName();
        }
        else {
            return "TypeKey: " + treeType.getName() + " : " + nodeType.getName() + " : " + elementType.getName();
        }
    }
}
