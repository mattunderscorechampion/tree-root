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

package com.mattunderscore.trees.binary.mutable;

import java.util.Collections;
import java.util.Iterator;

import net.jcip.annotations.NotThreadSafe;

import com.mattunderscore.iterators.ArrayIterator;
import com.mattunderscore.iterators.SingletonIterator;
import com.mattunderscore.trees.base.NonSettableNode;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;

/**
 * Mutable binary tree node implementation.
 * @author Matt Champion on 06/09/14.
 */
@NotThreadSafe
public final class MutableBinaryTreeNodeImpl<E> extends NonSettableNode<E, MutableBinaryTreeNode<E>> implements MutableBinaryTreeNode<E> {
    private MutableBinaryTreeNodeImpl<E> left;
    private MutableBinaryTreeNodeImpl<E> right;
    @SuppressWarnings("unchecked")
    private final MutableBinaryTreeNodeImpl<E>[] children = new MutableBinaryTreeNodeImpl[2];

    public MutableBinaryTreeNodeImpl(E element) {
        super(element);
        left = null;
        right = null;
    }

    public MutableBinaryTreeNodeImpl(E element, MutableBinaryTreeNodeImpl<E> left, MutableBinaryTreeNodeImpl<E> right) {
        super(element);
        setInternalLeft(left);
        setInternalRight(right);
    }

    @Override
    public MutableBinaryTreeNode<E> setLeft(E element) {
        return setInternalLeft(new MutableBinaryTreeNodeImpl<>(element));
    }

    @Override
    public MutableBinaryTreeNode<E> setRight(E element) {
        return setInternalRight(new MutableBinaryTreeNodeImpl<>(element));
    }

    private MutableBinaryTreeNodeImpl<E> setInternalRight(MutableBinaryTreeNodeImpl<E> right) {
        this.right = right;
        children[1] = right;
        return right;
    }

    private MutableBinaryTreeNodeImpl<E> setInternalLeft(MutableBinaryTreeNodeImpl<E> left) {
        this.left = left;
        children[0] = left;
        return left;
    }

    @Override
    public MutableBinaryTreeNode<E> getLeft() {
        return left;
    }

    @Override
    public MutableBinaryTreeNode<E> getRight() {
        return right;
    }

    @Override
    public int getNumberOfChildren() {
        if (children[0] == null && children[1] == null) {
            return 0;
        }
        else if (children[0] != null && children[1] != null) {
            return 2;
        }
        else {
            return 1;
        }
    }

    @Override
    public Iterator<MutableBinaryTreeNodeImpl<E>> childIterator() {
        if (children[0] == null && children[1] == null) {
            return Collections.emptyIterator();
        }
        else if (children[0] != null && children[1] != null) {
            return ArrayIterator.create(children);
        }
        else if (children[0] != null) {
            return new SingletonIterator<>(children[0]);
        }
        else {
            return new SingletonIterator<>(children[1]);
        }
    }

    @Override
    public Iterator<? extends MutableBinaryTreeNodeImpl<E>> childStructuralIterator() {
        return ArrayIterator.create(children);
    }

    @Override
    public MutableBinaryTreeNodeImpl<E> getChild(int nChild) {
        if (nChild >= getNumberOfChildren()) {
            throw new IndexOutOfBoundsException();
        }
        else {
            return children[nChild];
        }
    }

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }
}
