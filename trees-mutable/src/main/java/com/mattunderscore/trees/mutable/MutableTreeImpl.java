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

package com.mattunderscore.trees.mutable;

import java.util.Arrays;
import java.util.Iterator;

import net.jcip.annotations.GuardedBy;

import com.mattunderscore.trees.base.AbstractSettableNode;
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.utilities.collections.FixedUncheckedSimpleCollection;

/**
 * Initial attempt at thread safety is base on copy on mutation. When a child node is added or removed a shallow copy
 * of the children is made with the modification present and the new child collection replaces the existing one. Any
 * iterators accessing the old child collection see the previous state.
 * <p>The problem is that modifications to grandchildren can still be seen because of the shallow copy. The problem with
 * this is the modifications can be observed out of order. The modification to the grandchildren is made after the
 * parent but seen first.</p>
 * @author Matt Champion on 15/07/14.
 */
public final class MutableTreeImpl<E> extends AbstractSettableNode<E, MutableSettableNode<E>> implements MutableTree<E, MutableSettableNode<E>>, MutableSettableNode<E> {
    @GuardedBy("this")
    private SimpleCollection<MutableTreeImpl<E>> childList;

    public MutableTreeImpl(E element) {
        super(element);
        childList = new FixedUncheckedSimpleCollection<>(new Object[0]);
    }

    MutableTreeImpl(E element, SimpleCollection<MutableTreeImpl<E>> childList) {
        super(element);
        this.childList = childList;
    }

    @Override
    public MutableTreeImpl<E> addChild(E e) {
        if (e == null) {
            throw new NullPointerException("You cannot add a child to an empty tree");
        }
        final MutableTreeImpl<E> child = new MutableTreeImpl<>(e);
        synchronized (this) {
            final SimpleCollection<MutableTreeImpl<E>> oldList = childList;
            final int size = oldList.size();
            final Object[] newArray = new Object[size + 1];
            int i = 0;
            final Iterator<MutableTreeImpl<E>> iterator = oldList.structuralIterator();
            while (iterator.hasNext()) {
                newArray[i] = iterator.next();
                i++;
            }
            newArray[size] = child;
            childList = new FixedUncheckedSimpleCollection<>(newArray);
        }
        return child;
    }

    @Override
    public boolean removeChild(MutableSettableNode<E> child) {
        if (child == null) {
            return false;
        }
        synchronized (this) {
            final SimpleCollection<MutableTreeImpl<E>> oldList = childList;
            final int size = oldList.size();
            final Object[] searchArray = new Object[size];
            int i = 0;
            int j = 0;
            final Iterator<MutableTreeImpl<E>> iterator = oldList.structuralIterator();
            while (iterator.hasNext()) {
                final MutableSettableNode<E> currentNode = iterator.next();
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
                final Object[] newArray = Arrays.copyOf(searchArray, newSize);
                childList = new FixedUncheckedSimpleCollection<>(newArray);
                return true;
            }
        }
    }

    @Override
    public MutableTreeImpl<E> getRoot() {
        if (isEmpty()) {
            return null;
        }
        else {
            return this;
        }
    }

    @Override
    public boolean isEmpty() {
        return elementReference.get() == null;
    }

    @Override
    public MutableTreeImpl<E> setRoot(E root) {
        elementReference.set(root);
        return this;
    }

    /**
     * Construct a TypeKey for a specific element type.
     * @param <E> The element type
     * @return The type key
     */
    public static <E> TypeKey<MutableTreeImpl<E>> typeKey() {
        return new TypeKey<MutableTreeImpl<E>>() {};
    }

    @Override
    public int getNumberOfChildren() {
        synchronized (this) {
            return childList.size();
        }
    }

    @Override
    public Iterator<MutableTreeImpl<E>> childIterator() {
        synchronized (this) {
            return childList.iterator();
        }
    }
}
