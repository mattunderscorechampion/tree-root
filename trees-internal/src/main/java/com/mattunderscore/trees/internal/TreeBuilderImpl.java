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
import com.mattunderscore.trees.TreeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author matt on 21/06/14.
 */
public class TreeBuilderImpl<E> implements TreeBuilder<E> {
    private NodeAppenderImpl<E> root;

    @Override
    public NodeAppender<E> root(E e) throws IllegalStateException {
        if (root == null) {
            root = new NodeAppenderImpl<E>(e);
            return root;
        }
        else {
            throw new IllegalStateException("Root already set");
        }
    }

    @Override
    public Tree build() {
        if (root == null) {
            throw new IllegalStateException("Root not set");
        }
        else {
            return root.createTree();
        }
    }

    private final class NodeAppenderImpl<R> implements NodeAppender<R> {
        private final R root;
        private final List<NodeAppenderImpl> children = new ArrayList<>();

        public NodeAppenderImpl(R root) {
            this.root = root;
        }

        @Override
        public NodeAppender addChild(R e) {
            final NodeAppenderImpl<R> child = new NodeAppenderImpl<>(e);
            children.add(child);
            return child;
        }

        private TreeNodeImpl createTree() {
            final TreeNodeImpl[] subTrees = new TreeNodeImpl[children.size()];
            for (int i = 0; i < subTrees.length; i++) {
                subTrees[i] = children.get(i).createTree();
            }
            final List<Node<R>> childNodes = new FixedUncheckedList<Node<R>>(subTrees);
            return new TreeNodeImpl<>(root, childNodes);
        }
    }
}
