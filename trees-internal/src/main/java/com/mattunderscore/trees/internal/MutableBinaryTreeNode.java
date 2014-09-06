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

import com.mattunderscore.trees.BinaryTreeNode;
import com.mattunderscore.trees.utilities.FixedUncheckedList;

import java.util.Collection;

/**
 * @author matt on 06/09/14.
 */
public final class MutableBinaryTreeNode<E> implements BinaryTreeNode<E> {
    private final E element;
    private MutableBinaryTreeNode<E> left;
    private MutableBinaryTreeNode<E> right;
    private final Object[] children = new Object[2];

    public MutableBinaryTreeNode(E element) {
        this.element = element;
        left = null;
        right = null;
    }

    public MutableBinaryTreeNode(E element, MutableBinaryTreeNode<E> left, MutableBinaryTreeNode<E> right) {
        this.element = element;
        setInternalLeft(left);
        setInternalRight(right);
    }

    public void setLeft(E element) {
        setInternalLeft(new MutableBinaryTreeNode<E>(element));
    }

    public void setRight(E element) {
        setInternalRight(new MutableBinaryTreeNode<E>(element));
    }

    private synchronized void setInternalRight(MutableBinaryTreeNode<E> right) {
        this.right = right;
        children[1] = right;
    }

    private synchronized void setInternalLeft(MutableBinaryTreeNode<E> left) {
        this.left = left;
        children[0] = left;
    }

    @Override
    public synchronized MutableBinaryTreeNode<E> getLeft() {
        return left;
    }

    @Override
    public synchronized MutableBinaryTreeNode<E> getRight() {
        return right;
    }

    @Override
    public E getElement() {
        return element;
    }

    @Override
    public Class<E> getElementClass() {
        return (Class<E>)element.getClass();
    }

    @Override
    public synchronized Collection<? extends MutableBinaryTreeNode<E>> getChildren() {
        return new FixedUncheckedList<>(children);
    }

    @Override
    public synchronized boolean isLeaf() {
        return left == null && right == null;
    }
}
