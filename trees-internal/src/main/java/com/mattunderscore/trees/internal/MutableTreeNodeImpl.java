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

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.collections.FixedUncheckedSimpleCollection;

import net.jcip.annotations.GuardedBy;

import java.util.Iterator;

/**
 * Initial attempt at thread safety is base on copy on mutation. When a child node is added or removed a shallow copy
 * of the children is made with the modification present and the new child collection replaces the existing one. Any
 * iterators accessing the old child collection see the previous state.
 * <p>The problem is that modifications to grandchildren can still be seen because of the shallow copy. The problem with
 * this is the modifications can be observed out of order. The modification to the grandchildren is made after the
 * parent but seen first.</p>
 * @author Matt Champion on 15/07/14.
 */
public final class MutableTreeNodeImpl<E> implements MutableTree<E, MutableNode<E>>, MutableNode<E> {
    @GuardedBy("this")
    private SimpleCollection<MutableNode<E>> elementList;
    private E element;

    public MutableTreeNodeImpl(E element) {
        elementList = new FixedUncheckedSimpleCollection<>(new Object[0]);
        synchronized (this) {
            this.element = element;
        }
    }

    private MutableTreeNodeImpl(E element, SimpleCollection<MutableNode<E>> childList) {
        this.element = element;
        synchronized (this) {
            elementList = childList;
        }
    }

    @Override
    public MutableNode<E> addChild(E e) {
        if (e == null) {
            throw new NullPointerException("You cannot add a child to an empty tree");
        }
        final MutableTreeNodeImpl<E> child = new MutableTreeNodeImpl<E>(e);
        synchronized (this) {
            final SimpleCollection<MutableNode<E>> oldList = elementList;
            final int size = oldList.size();
            final Object[] newArray = new Object[size + 1];
            int i = 0;
            final Iterator<MutableNode<E>> iterator = oldList.structuralIterator();
            while (iterator.hasNext()) {
                newArray[i] = iterator.next();
                i++;
            }
            newArray[size] = child;
            elementList = new FixedUncheckedSimpleCollection<>(newArray);
        }
        return child;
    }

    @Override
    public boolean removeChild(MutableNode<E> child) {
        if (child == null) {
            return false;
        }
        synchronized (this) {
            final SimpleCollection<MutableNode<E>> oldList = elementList;
            final int size = oldList.size();
            final Object[] searchArray = new Object[size];
            int i = 0;
            int j = 0;
            final Iterator<MutableNode<E>> iterator = oldList.structuralIterator();
            while (iterator.hasNext()) {
                final MutableNode<E> currentNode = iterator.next();
                if (child != currentNode) {
                    searchArray[j] = currentNode;
                    j++;
                }
                i++;
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
                elementList = new FixedUncheckedSimpleCollection<>(newArray);
                return true;
            }
        }
    }

    @Override
    public MutableNode<E> getRoot() {
        if (isEmpty()) {
            return null;
        }
        else {
            return this;
        }
    }

    @Override
    public boolean isEmpty() {
        return element == null;
    }

    @Override
    public E getElement() {
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<E> getElementClass() {
        return (Class<E>)element.getClass();
    }

    @Override
    public synchronized SimpleCollection<MutableNode<E>> getChildren() {
        return elementList;
    }

    @Override
    public synchronized boolean isLeaf() {
        return elementList.size() == 0;
    }

    @Override
    public synchronized MutableNode<E> setRoot(E root) {
        element = root;
        return this;
    }

    public final static class Constructor<E> implements TreeConstructor<E, MutableTree<E, MutableNode<E>>> {

        @Override
        public MutableTree<E, MutableNode<E>> build(E e, MutableTree<E, MutableNode<E>>... subtrees) {
            return new MutableTreeNodeImpl(e, new FixedUncheckedSimpleCollection<E>(subtrees));
        }

        @Override
        public Class<?> forClass() {
            return MutableTree.class;
        }
    }

    public final static class EmptyConstructor<E> implements EmptyTreeConstructor<E, MutableTree<E, MutableNode<E>>> {

        @Override
        public MutableTree<E, MutableNode<E>> build() {
            return new MutableTreeNodeImpl(null, new FixedUncheckedSimpleCollection<>(new Object[0]));
        }

        @Override
        public Class<?> forClass() {
            return MutableTree.class;
        }
    }

    public static final class Converter<E> implements TreeConverter<E, MutableTreeNodeImpl<E>> {

        @Override
        public MutableTreeNodeImpl<E> build(Tree<E, ? extends Node<E>> sourceTree) {
            final Node<E> root = sourceTree.getRoot();
            final MutableTreeNodeImpl<E> newTree = new MutableTreeNodeImpl<>(root.getElement());
            for (final Node<E> child : root.getChildren()) {
                duplicate(newTree, child);
            }
            return newTree;
        }

        @Override
        public Class<?> forClass() {
            return MutableTree.class;
        }

        private void duplicate(MutableTreeNodeImpl<E> newParent, Node<E> sourceChild) {
            final MutableTreeNodeImpl<E> newChild = (MutableTreeNodeImpl<E>) newParent.addChild(sourceChild.getElement());
            for (final Node<E> child : sourceChild.getChildren()) {
                duplicate(newChild, child);
            }
        }
    }

    public static final class NodeConverter<E> implements NodeToTreeConverter<E, MutableNode<E>, MutableTree<E, MutableNode<E>>, MutableNode<E>> {

        @Override
        public MutableTree<E, MutableNode<E>> treeFromRootNode(MutableNode<E> node) {
            return (MutableTreeNodeImpl<E>)node;
        }

        @Override
        public Class<?> forClass() {
            return MutableTreeNodeImpl.class;
        }
    }
}
