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

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.jcip.annotations.NotThreadSafe;

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;

/**
 * Mutable binary tree node implementation.
 * @author Matt Champion on 06/09/14.
 */
@NotThreadSafe
public final class MutableBinaryTreeNodeImpl<E> implements MutableBinaryTreeNode<E> {
    private final E element;
    private MutableBinaryTreeNodeImpl<E> left;
    private MutableBinaryTreeNodeImpl<E> right;

    public MutableBinaryTreeNodeImpl(E element) {
        this.element = element;
        left = null;
        right = null;
    }

    public MutableBinaryTreeNodeImpl(E element, MutableBinaryTreeNodeImpl<E> left, MutableBinaryTreeNodeImpl<E> right) {
        this.element = element;
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

    /*package*/ MutableBinaryTreeNodeImpl<E> setInternalRight(MutableBinaryTreeNodeImpl<E> right) {
        this.right = right;
        return right;
    }

    /*package*/ MutableBinaryTreeNodeImpl<E> setInternalLeft(MutableBinaryTreeNodeImpl<E> left) {
        this.left = left;
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
    public E getElement() {
        return element;
    }

    @Override
    public int getNumberOfChildren() {
        if (left == null && right == null) {
            return 0;
        }
        else if (left != null && right != null) {
            return 2;
        }
        else {
            return 1;
        }
    }

    @Override
    public Iterator<? extends MutableBinaryTreeNode<E>> childIterator() {
        return new ChildIterator();
    }

    @Override
    public Iterator<? extends MutableBinaryTreeNode<E>> childStructuralIterator() {
        return new ChildStructuralIterator();
    }

    @Override
    public MutableBinaryTreeNodeImpl<E> getChild(int nChild) {
        if (left != null && nChild == 0) {
            return left;
        }
        else if (left == null && nChild == 0 && right != null) {
            return null;
        }
        else if (right != null && nChild == 1) {
            return right;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }

    @NotThreadSafe
    private final class ChildIterator implements Iterator<MutableBinaryTreeNode<E>> {
        private int pos = -1;

        public ChildIterator() {
        }

        @Override
        public boolean hasNext() {
            return (pos == -1 && (left != null || right != null)) || (pos == 0 && right != null);
        }

        @Override
        public MutableBinaryTreeNode<E> next() {
            if (pos == -1 && left != null) {
                pos = 0;
                return left;
            }
            else if (pos == -1 && right != null) {
                pos = 1;
                return right;
            }
            else if (pos == 0 && right != null) {
                pos = 1;
                return right;
            }
            else {
                pos = 2;
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            if (pos == 0) {
                left = null;
            }
            else if (pos == 1) {
                right = null;
            }
            else {
                throw new IllegalStateException();
            }
        }
    }

    @NotThreadSafe
    private final class ChildStructuralIterator implements Iterator<MutableBinaryTreeNode<E>> {
        private int pos = -1;

        public ChildStructuralIterator() {
        }

        @Override
        public boolean hasNext() {
            return (pos == -1 && (left != null || right != null)) || (pos == 0 && right != null);
        }

        @Override
        public MutableBinaryTreeNode<E> next() {
            if (pos == -1) {
                pos = 0;
                return left;
            }
            else if (pos == 0) {
                pos = 1;
                return right;
            }
            else {
                pos = 2;
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            if (pos == 0) {
                left = null;
            }
            else if (pos == 1) {
                right = null;
            }
            else {
                throw new IllegalStateException();
            }
        }
    }
}
