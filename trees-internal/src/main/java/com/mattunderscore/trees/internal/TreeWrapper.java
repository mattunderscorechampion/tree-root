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

package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.NodeToTreeConverter;

/**
 * Wrap any node to create a Tree.
 * @author matt on 06/09/14.
 */
public final class TreeWrapper<E, N extends Node<E>> implements Tree<E, N> {
    private final N root;

    public TreeWrapper() {
        this.root = null;
    }

    public TreeWrapper(N root) {
        this.root = root;
    }

    @Override
    public N getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    public static final class EmptyConstructor<E, N extends Node<E>> implements EmptyTreeConstructor<E, TreeWrapper<E, N>> {
        private final Class<?> forClass;
        public EmptyConstructor(Class<?> forClass) {
            this.forClass = forClass;
        }

        @Override
        public TreeWrapper<E, N> build() {
            return new TreeWrapper<E, N>();
        }

        @Override
        public Class<?> forClass() {
            return forClass;
        }
    }

    public static final class TreeConverter<E, N extends Node<E>> implements NodeToTreeConverter<E, N, TreeWrapper<E, N>> {
        private final Class<?> forClass;

        public TreeConverter(Class<?> forClass) {
            this.forClass = forClass;
        }

        @Override
        public TreeWrapper<E, N> treeFromRootNode(Node node) {
            return new TreeWrapper((N)node);
        }

        @Override
        public Class<?> forClass() {
            return forClass;
        }
    }
}
