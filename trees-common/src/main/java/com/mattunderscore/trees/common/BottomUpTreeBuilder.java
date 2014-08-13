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

import com.mattunderscore.trees.IBottomUpTreeBuilder;
import com.mattunderscore.trees.INode;
import com.mattunderscore.trees.ITree;

import java.lang.reflect.Array;

/**
 * @author matt on 13/08/14.
 */
public class BottomUpTreeBuilder<E> implements IBottomUpTreeBuilder<E> {
    private static final TreeHelper helper = new TreeHelper();

    private final E root;
    private final IBottomUpTreeBuilder<E>[] children;

    public BottomUpTreeBuilder() {
        root = null;
        children = new IBottomUpTreeBuilder[0];
    }

    private BottomUpTreeBuilder(E e) {
        root = e;
        children = new IBottomUpTreeBuilder[0];
    }

    private BottomUpTreeBuilder(E e, IBottomUpTreeBuilder[] builders) {
        root = e;
        children = builders;
    }

    @Override
    public IBottomUpTreeBuilder<E> create(E e) {
        return new BottomUpTreeBuilder<>(e);
    }

    @Override
    public IBottomUpTreeBuilder<E> create(E e, IBottomUpTreeBuilder<E>... builders) {
        return new BottomUpTreeBuilder<>(e, builders);
    }

    @Override
    public <N extends INode<E>, T extends ITree<E, N>> T build(Class<T> klass) {
        if (root == null) {
            return helper.emptyTree(klass);
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
