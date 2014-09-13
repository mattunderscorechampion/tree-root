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

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

import java.util.Iterator;

/**
 * @author Matt Champion on 08/09/14.
 */
public final class MutableBinaryTreeImpl<E> extends TreeWrapper<E, MutableBinaryTreeNode<E>> {
    public MutableBinaryTreeImpl() {
        super();
    }

    public MutableBinaryTreeImpl(MutableBinaryTreeNode<E> root) {
        super(root);
    }

    public MutableBinaryTreeImpl(E root) {
        super(new MutableBinaryTreeNodeImpl<E>(root));
    }

    public static final class NodeConverter<E> implements NodeToTreeConverter<E, MutableBinaryTreeNode<E>, MutableBinaryTreeImpl<E>> {

        @Override
        public MutableBinaryTreeImpl<E> treeFromRootNode(MutableBinaryTreeNode<E> node) {
            return new MutableBinaryTreeImpl<>(node);
        }

        @Override
        public Class<?> forClass() {
            return MutableBinaryTreeImpl.class;
        }
    }

    public static final class EmptyConstructor<E> implements EmptyTreeConstructor<E, MutableBinaryTreeImpl<E>> {
        @Override
        public MutableBinaryTreeImpl<E> build() {
            return new MutableBinaryTreeImpl<>();
        }

        @Override
        public Class<?> forClass() {
            return MutableBinaryTreeImpl.class;
        }
    }

    public static final class Constructor<E> implements TreeConstructor<E, MutableBinaryTreeImpl<E>> {

        @Override
        public MutableBinaryTreeImpl<E> build(E e, MutableBinaryTreeImpl<E>[] subtrees) {
            if (subtrees.length > 2) {
                throw new IllegalStateException("A binary tree cannot have more than two children");
            }

            MutableBinaryTreeNode<E> left = null;
            MutableBinaryTreeNode<E> right = null;

            if (subtrees.length > 0) {
                left = subtrees[0].getRoot();
            }
            if (subtrees.length > 1) {
                right = subtrees[1].getRoot();
            }

            final MutableBinaryTreeNodeImpl<E> root = new MutableBinaryTreeNodeImpl<E>(e, left, right);
            return new MutableBinaryTreeImpl<E>(root);
        }

        @Override
        public Class<?> forClass() {
            return MutableBinaryTreeImpl.class;
        }
    }

    public static final class Converter<E> implements TreeConverter<E, MutableBinaryTreeImpl<E>> {
        @Override
        public MutableBinaryTreeImpl<E> build(Tree<E, ? extends Node<E>> sourceTree) {
            final Node<E> root = sourceTree.getRoot();
            final MutableBinaryTreeImpl<E> tree = new MutableBinaryTreeImpl<E>(duplicate(root));
            return tree;
        }

        @Override
        public Class<?> forClass() {
            return MutableBinaryTreeImpl.class;
        }

        private MutableBinaryTreeNodeImpl<E> duplicate(Node<E> sourceChild) {
            final Iterator<? extends Node<E>> children = sourceChild.getChildren().iterator();
            if (children.hasNext()) {
                final Node<E> left = children.next();
                Node<E> right = null;
                if (children.hasNext()) {
                    right = children.next();
                }
                if (children.hasNext()) {
                    throw new IllegalStateException("A binary tree can only have two children");
                }
                final MutableBinaryTreeNodeImpl newLeft = duplicate(left);
                MutableBinaryTreeNodeImpl newRight = null;
                if (right != null) {
                    newRight = duplicate(right);
                }
                return new MutableBinaryTreeNodeImpl<E>(sourceChild.getElement(), newLeft, newRight);
            }
            else {
                return new MutableBinaryTreeNodeImpl<E>(sourceChild.getElement());
            }
        }
    }
}
