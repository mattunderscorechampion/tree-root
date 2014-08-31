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

package com.mattunderscore.trees.common;

import com.mattunderscore.trees.BottomUpTreeBuilder;
import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.Tree;
import net.jcip.annotations.Immutable;

import java.lang.reflect.Array;

/**
 * @author matt on 13/08/14.
 */
@Immutable
final class BottomUpTreeBuilderImpl<E> implements BottomUpTreeBuilder<E> {
    private final SPISupport helper;
    private final E root;
    private final BottomUpTreeBuilder<E>[] children;

    public BottomUpTreeBuilderImpl(SPISupport helper) {
        this(helper, null, new BottomUpTreeBuilder[0]);
    }

    private BottomUpTreeBuilderImpl(SPISupport helper, E e) {
        this(helper, e, new BottomUpTreeBuilder[0]);
    }

    private BottomUpTreeBuilderImpl(SPISupport helper, E e, BottomUpTreeBuilder[] builders) {
        this.helper = helper;
        root = e;
        children = builders;
    }

    @Override
    public BottomUpTreeBuilder<E> create(E e) {
        return new BottomUpTreeBuilderImpl<>(helper, e);
    }

    @Override
    public BottomUpTreeBuilder<E> create(E e, BottomUpTreeBuilder<E>... builders) {
        return new BottomUpTreeBuilderImpl<>(helper, e, builders);
    }

    @Override
    public <N extends Node<E>, T extends Tree<E, N>> T build(Class<T> klass) throws OperationNotSupportedForType {
        if (root == null) {
            return helper.createEmptyTree(klass);
        }
        else {
            final T[] subtrees = (T[])Array.newInstance(klass, children.length);
            for (int i = 0; i < children.length; i++) {
                subtrees[i] = children[i].build(klass);
            }
            return helper.<E, N, T>newTreeFrom(klass, root, subtrees);
        }
    }
}
