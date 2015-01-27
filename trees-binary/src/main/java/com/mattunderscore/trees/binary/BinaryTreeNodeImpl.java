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

package com.mattunderscore.trees.binary;

import com.mattunderscore.trees.base.FixedNode;
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.collections.FixedUncheckedSimpleCollection;

/**
 * Binary tree node implementation.
 * @author Matt Champion on 06/09/14.
 */
public final class BinaryTreeNodeImpl<E> extends FixedNode<E> implements BinaryTreeNode<E> {
    private final BinaryTreeNodeImpl<E> left;
    private final BinaryTreeNodeImpl<E> right;
    private final Object[] children;

    public BinaryTreeNodeImpl(E element) {
        super(element);
        left = null;
        right = null;
        children = new Object[0];
    }

    public BinaryTreeNodeImpl(E element, BinaryTreeNodeImpl<E> left, BinaryTreeNodeImpl<E> right) {
        super(element);
        this.left = left;
        this.right = right;
        if (left != null && right != null) {
            children = new Object[2];
            children[0] = left;
            children[1] = right;
        }
        else if (left != null) {
            children = new Object[1];
            children[0] = left;
        }
        else if (right != null) {
            children = new Object[2];
            children[0] = null;
            children[1] = right;
        }
        else {
            children = new Object[0];
        }
    }

    @Override
    public BinaryTreeNode<E> getLeft() {
        return left;
    }

    @Override
    public BinaryTreeNode<E> getRight() {
        return right;
    }

    @Override
    public SimpleCollection<? extends BinaryTreeNodeImpl<E>> getChildren() {
        return new FixedUncheckedSimpleCollection<>(children);
    }

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }

    public static final class EmptyConstructor<E> implements EmptyTreeConstructor<E, BinaryTreeWrapper<E, BinaryTreeNodeImpl<E>>> {

        @Override
        public BinaryTreeWrapper<E, BinaryTreeNodeImpl<E>> build() {
            return new BinaryTreeWrapper<>();
        }

        @Override
        public Class<? extends Tree> forClass() {
            return BinaryTree.class;
        }
    }

    public static final class BinaryTreeConstructor<E> implements TreeConstructor<E, Tree<E, BinaryTreeNodeImpl<E>>> {

        @Override
        public BinaryTreeWrapper<E, BinaryTreeNodeImpl<E>> build(E e, Tree<E, BinaryTreeNodeImpl<E>>[] subtrees) {
            if (subtrees.length > 2) {
                throw new IllegalStateException("A binary tree cannot have more than two children");
            }

            BinaryTreeNodeImpl<E> left = null;
            BinaryTreeNodeImpl<E> right = null;

            if (subtrees.length > 0) {
                left = subtrees[0].getRoot();
            }
            if (subtrees.length > 1) {
                right = subtrees[1].getRoot();
            }

            final BinaryTreeNodeImpl<E> root = new BinaryTreeNodeImpl<>(e, left, right);
            return new BinaryTreeWrapper<>(root);
        }

        @Override
        public Class<? extends Tree> forClass() {
            return BinaryTree.class;
        }
    }

    public static <E> TypeKey<BinaryTree<E, BinaryTreeNode<E>>> typeKey() {
        return new TypeKey<BinaryTree<E, BinaryTreeNode<E>>>() {};
    }
}
