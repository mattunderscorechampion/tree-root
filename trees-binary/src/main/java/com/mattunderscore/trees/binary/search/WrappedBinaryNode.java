/* Copyright © 2015 Matthew Champion
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

package com.mattunderscore.trees.binary.search;

import java.util.Iterator;

import com.mattunderscore.trees.binary.ClosedBinaryTreeNode;
import com.mattunderscore.trees.binary.ClosedMutableBinaryTreeNode;
import com.mattunderscore.trees.utilities.iterators.ConvertingIterator;

/**
 * Wrap a ClosedMutableBinaryTreeNode as a ClosedBinaryTreeNode.
 * @author Matt Champion on 22/06/2015
 */
public final class WrappedBinaryNode<E> implements ClosedBinaryTreeNode<E> {
    private final ClosedMutableBinaryTreeNode<E> delegateNode;

    public WrappedBinaryNode(ClosedMutableBinaryTreeNode<E> node) {
        delegateNode = node;
    }

    @Override
    public ClosedBinaryTreeNode<E> getLeft() {
        final ClosedMutableBinaryTreeNode<E> node = delegateNode.getLeft();
        if (node != null) {
            return new WrappedBinaryNode<>(node);
        }
        return null;
    }

    @Override
    public ClosedBinaryTreeNode<E> getRight() {
        final ClosedMutableBinaryTreeNode<E> node = delegateNode.getRight();
        if (node != null) {
            return new WrappedBinaryNode<>(node);
        }
        return null;
    }

    @Override
    public Iterator<? extends ClosedBinaryTreeNode<E>> childStructuralIterator() {
        return new WrappingIterator<>(delegateNode.childStructuralIterator());
    }

    @Override
    public ClosedBinaryTreeNode<E> getChild(int nChild) {
        final ClosedMutableBinaryTreeNode<E> node = delegateNode.getChild(nChild);
        if (node != null) {
            return new WrappedBinaryNode<>(node);
        }
        return null;
    }

    @Override
    public E getElement() {
        return delegateNode.getElement();
    }

    @Override
    public int getNumberOfChildren() {
        return delegateNode.getNumberOfChildren();
    }

    @Override
    public Iterator<? extends ClosedBinaryTreeNode<E>> childIterator() {
        return new WrappingIterator<>(delegateNode.childIterator());
    }

    private static final class WrappingIterator<E> extends ConvertingIterator<ClosedBinaryTreeNode<E>, ClosedMutableBinaryTreeNode<E>> {
        protected WrappingIterator(Iterator<? extends ClosedMutableBinaryTreeNode<E>> delegate) {
            super(delegate);
        }

        @Override
        protected ClosedBinaryTreeNode<E> convert(ClosedMutableBinaryTreeNode<E> node) {
            if (node != null) {
                return new WrappedBinaryNode<>(node);
            }
            return null;
        }
    }
}
