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

import java.util.Iterator;

import com.mattunderscore.trees.base.FixedNode;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.iterators.CastingArrayIterator;
import com.mattunderscore.trees.utilities.iterators.EmptyIterator;
import com.mattunderscore.trees.utilities.iterators.SingletonIterator;

/**
 * Binary tree node implementation.
 * @author Matt Champion on 06/09/14.
 */
public final class BinaryTreeNodeImpl<E> extends FixedNode<E, BinaryTreeNode<E>> implements BinaryTreeNode<E> {
    private final BinaryTreeNodeImpl<E> left;
    private final BinaryTreeNodeImpl<E> right;

    public BinaryTreeNodeImpl(E element) {
        super(element);
        left = null;
        right = null;
    }

    public BinaryTreeNodeImpl(E element, BinaryTreeNodeImpl<E> left, BinaryTreeNodeImpl<E> right) {
        super(element);
        this.left = left;
        this.right = right;
    }

    @Override
    public BinaryTreeNodeImpl<E> getLeft() {
        return left;
    }

    @Override
    public BinaryTreeNodeImpl<E> getRight() {
        return right;
    }

    @Override
    public int getNumberOfChildren() {
        if (left == null && right == null) {
            return 0;
        }
        else if (left == null || right == null) {
            return 1;
        }
        else {
            return 2;
        }
    }

    @Override
    public Iterator<BinaryTreeNodeImpl<E>> childIterator() {
        if (left == null && right == null) {
            return new EmptyIterator<>();
        }
        else if (left != null && right == null) {
            return new SingletonIterator<>(left);
        }
        else if (left == null) {
            return new SingletonIterator<>(right);
        }
        else {
            return CastingArrayIterator.unsafeCreate(new BinaryTreeNodeImpl[] {left, right});
        }
    }

    @Override
    public Iterator<BinaryTreeNodeImpl<E>> childStructuralIterator() {
        return CastingArrayIterator.unsafeCreate(new BinaryTreeNodeImpl[] {left, right});
    }

    @Override
    public BinaryTreeNodeImpl<E> getChild(int nChild) {
        if (nChild == 1) {
            return right;
        }
        else if (nChild == 0) {
            return left;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }

    public static final class EmptyConstructor<E> implements EmptyTreeConstructor<E, BinaryTreeNode<E>, BinaryTreeWrapper<E, BinaryTreeNode<E>>> {

        @Override
        public BinaryTreeWrapper<E, BinaryTreeNode<E>> build() {
            return new BinaryTreeWrapper<>();
        }

        @Override
        public Class<? extends Tree> forClass() {
            return BinaryTreeWrapper.class;
        }
    }

    public static final class BinaryTreeConstructor<E> implements TreeConstructor<E, BinaryTreeNode<E>, BinaryTreeWrapper<E, BinaryTreeNode<E>>> {

        @Override
        public BinaryTreeWrapper<E, BinaryTreeNode<E>> build(E e, BinaryTreeWrapper<E, BinaryTreeNode<E>>[] subtrees) {
            if (subtrees.length > 2) {
                throw new IllegalStateException("A binary tree cannot have more than two children");
            }

            BinaryTreeNodeImpl<E> left = null;
            BinaryTreeNodeImpl<E> right = null;

            if (subtrees.length > 0) {
                left = (BinaryTreeNodeImpl<E>)subtrees[0].getRoot();
            }
            if (subtrees.length > 1) {
                right = (BinaryTreeNodeImpl<E>)subtrees[1].getRoot();
            }

            final BinaryTreeNodeImpl<E> root = new BinaryTreeNodeImpl<>(e, left, right);
            return new BinaryTreeWrapper<>(root);
        }

        @Override
        public Class<? extends Tree> forClass() {
            return BinaryTreeWrapper.class;
        }
    }

    public static <E> TypeKey<BinaryTree<E, BinaryTreeNode<E>>> typeKey() {
        return new TypeKey<BinaryTree<E, BinaryTreeNode<E>>>() {};
    }
}
