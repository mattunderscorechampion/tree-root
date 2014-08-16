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

import com.mattunderscore.trees.IMutableNode;
import com.mattunderscore.trees.IMutableTree;
import com.mattunderscore.trees.INode;
import com.mattunderscore.trees.ITree;
import com.mattunderscore.trees.spi.IEmptyTreeConstructor;
import com.mattunderscore.trees.spi.INodeToTreeConverter;
import com.mattunderscore.trees.spi.ITreeConstructor;
import com.mattunderscore.trees.spi.ITreeConverter;
import com.mattunderscore.trees.utilities.FixedUncheckedList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author matt on 15/07/14.
 */
public final class MutableTreeNodeImpl<E> implements IMutableTree<E, IMutableNode<E>>, IMutableNode<E> {
    private volatile List<IMutableNode<E>> elementList;
    private final E element;

    public MutableTreeNodeImpl(E element) {
        elementList = new FixedUncheckedList<>(new Object[0]);
        this.element = element;
    }

    private MutableTreeNodeImpl(E element, List<IMutableNode<E>> childList) {
        this.element = element;
        synchronized (this) {
            elementList = childList;
        }
    }

    @Override
    public IMutableNode<E> addChild(E e) {
        if (e == null) {
            throw new NullPointerException("You cannot add a child to an empty tree");
        }
        final MutableTreeNodeImpl child = new MutableTreeNodeImpl(e);
        synchronized (this) {
            final List<IMutableNode<E>> oldList = elementList;
            final int size = oldList.size();
            final Object[] newArray = new Object[size + 1];
            for (int i = 0; i < size; i++) {
                newArray[i] = oldList.get(i);
            }
            newArray[size] = child;
            elementList = new FixedUncheckedList<IMutableNode<E>>(newArray);
        }
        return child;
    }

    @Override
    public boolean removeChild(IMutableNode<E> child) {
        if (child == null) {
            return false;
        }
        synchronized (this) {
            final List<IMutableNode<E>> oldList = elementList;
            final int size = oldList.size();
            final Object[] searchArray = new Object[size];
            int i = 0;
            int j = 0;
            for (; i < size; i++) {
                final IMutableNode<E> currentNode = oldList.get(i);
                if (child != currentNode) {
                    searchArray[j] = currentNode;
                    j++;
                }
            }
            if (j == i) {
                // Nothing removed
                return false;
            }
            else {
                final int newSize = size - 1;
                final Object[] newArray = new Object[newSize];
                for (int k = 0; k < newSize; k++) {
                    newArray[k] = searchArray[k];
                }
                elementList = new FixedUncheckedList<>(newArray);
                return true;
            }
        }
    }

    @Override
    public IMutableNode<E> getRoot() {
        return this;
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
    public synchronized Collection<IMutableNode<E>> getChildren() {
        return Collections.unmodifiableList(elementList);
    }

    public final static class Constructor<E> implements ITreeConstructor<E, MutableTreeNodeImpl<E>> {

        @Override
        public MutableTreeNodeImpl<E> build(E e, MutableTreeNodeImpl<E>... subtrees) {
            return new MutableTreeNodeImpl(e, new FixedUncheckedList<>(subtrees));
        }

        @Override
        public Class<?> forClass() {
            return IMutableTree.class;
        }
    }

    public final static class EmptyConstructor<E> implements IEmptyTreeConstructor<E, IMutableTree<E, IMutableNode<E>>> {

        @Override
        public IMutableTree<E, IMutableNode<E>> build() {
            return new MutableTreeNodeImpl(null, Collections.<INode<E>>emptyList());
        }

        @Override
        public Class<?> forClass() {
            return IMutableTree.class;
        }
    }

    public static final class Converter<E> implements ITreeConverter<E, MutableTreeNodeImpl<E>> {

        @Override
        public MutableTreeNodeImpl<E> build(ITree<E, ? extends INode<E>> sourceTree) {
            final INode<E> root = sourceTree.getRoot();
            final MutableTreeNodeImpl<E> newTree = new MutableTreeNodeImpl<>(root.getElement());
            for (final INode<E> child : root.getChildren()) {
                duplicate(newTree, child);
            }
            return newTree;
        }

        @Override
        public Class<?> forClass() {
            return IMutableTree.class;
        }

        private void duplicate(MutableTreeNodeImpl<E> newParent, INode<E> sourceChild) {
            final MutableTreeNodeImpl<E> newChild = (MutableTreeNodeImpl<E>) newParent.addChild(sourceChild.getElement());
            for (final INode<E> child : sourceChild.getChildren()) {
                duplicate(newChild, child);
            }
        }
    }

    public static final class NodeConverter<E> implements INodeToTreeConverter<E, IMutableNode<E>, IMutableTree<E, IMutableNode<E>>> {

        @Override
        public IMutableTree<E, IMutableNode<E>> treeFromRootNode(IMutableNode<E> node) {
            return (MutableTreeNodeImpl<E>)node;
        }

        @Override
        public Class<?> forClass() {
            return MutableTreeNodeImpl.class;
        }
    }
}
