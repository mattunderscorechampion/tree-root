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

import com.mattunderscore.trees.*;
import net.jcip.annotations.NotThreadSafe;

/**
 * @author matt on 15/08/14.
 */
@NotThreadSafe
final class TopDownTreeBuilderImpl<E> implements TopDownTreeRootBuilder.TopDownTreeBuilder<E> {
    private final SPISupport helper;
    private final LinkedTree<E> tree;

    public TopDownTreeBuilderImpl(SPISupport helper, E root) {
        this.helper = helper;
        tree = new LinkedTree<E>(root);
    }

    @Override
    public <T extends Tree<E, Node<E>>> T build(Class<T> klass) throws OperationNotSupportedForType {
        return helper.convertTree(klass, tree);
    }

    @Override
    public TopDownTreeRootBuilder.TopDownTreeBuilderAppender<E> addChild(E e) {
        return new Appender<>(tree.addChild(e));
    }

    @NotThreadSafe
    private static final class Appender<S> implements TopDownTreeRootBuilder.TopDownTreeBuilderAppender<S> {
        private final MutableNode<S> root;

        public Appender(MutableNode<S> root) {
            this.root = root;
        }

        @Override
        public TopDownTreeRootBuilder.TopDownTreeBuilderAppender<S> addChild(S e) {
            return new Appender<>(root.addChild(e));
        }
    }
}
