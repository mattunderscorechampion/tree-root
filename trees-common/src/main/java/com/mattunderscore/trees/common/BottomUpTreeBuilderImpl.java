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
import com.mattunderscore.trees.INode;
import com.mattunderscore.trees.ITree;
import com.mattunderscore.trees.Tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author matt on 06/08/14.
 */
public final class BottomUpTreeBuilderImpl<E> implements BottomUpTreeBuilder<E> {
    private final TreeHelper helper = new TreeHelper();
    private final Tree<E> tree;

    private BottomUpTreeBuilderImpl() {
        tree = null;
    }

    private BottomUpTreeBuilderImpl(E e) {
        tree = new LinkedTree<>(e);
    }

    private BottomUpTreeBuilderImpl(E root, BottomUpTreeBuilderImpl[] children)
    {
        tree = null;
    }

    @Override
    public BottomUpTreeBuilder<E> create(E e) {
        return new BottomUpTreeBuilderImpl<>(e);
    }

    @Override
    public BottomUpTreeBuilder<E> create(E e, BottomUpTreeBuilder<E>... trees) {
        return  new BottomUpTreeBuilderImpl<>(e, (BottomUpTreeBuilderImpl<E>[])trees);
    }

    @Override
    public <T extends ITree<E, INode<E>>> T build(Class<T> klass) {
        return helper.treeFrom(klass, tree);
    }
}
