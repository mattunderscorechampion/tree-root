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

package com.mattunderscore.simple.collections;

import com.mattunderscore.iterators.PrefetchingIterator;

import net.jcip.annotations.NotThreadSafe;

import java.util.*;

/**
 * A {@link SimpleCollection} backed by {@link java.util.ArrayList}. Not
 * thread-safe. Mutable. Does not provide an iteration order or permit nulls.
 * @author Matt Champion on 09/09/14.
 */
@NotThreadSafe
public final class ArrayListSimpleCollection<E> implements SimpleCollection<E> {
    private final ArrayList<E> list;

    /**
     * Create an empty collection.
     */
    public ArrayListSimpleCollection() {
        list = new ArrayList<>();
    }

    /**
     * Create a collection from a collection. The elements are copied from the collection and no references to the
     * collection are kept.
     * @param initial The initial collection
     */
    public ArrayListSimpleCollection(Collection<E> initial) {
        list = new ArrayList<>(initial);
    }

    /**
     * Create an empty collection with an initial capacity.
     * @param initialCapacity The initial capacity
     */
    public ArrayListSimpleCollection(int initialCapacity) {
        list = new ArrayList<>(initialCapacity);
    }

    /**
     * Add element to the end of the list.
     * @param element The element to add
     */
    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("Nulls are not permitted in this collection");
        }
        else {
            list.add(element);
        }
    }

    /**
     * Set an element as a specific position.
     * @param index The position
     * @return Any replaced value
     * @throws IndexOutOfBoundsException If index is greater than the current size
     */
    public E get(int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    /**
     * Set an element as a specific position. If index is beyond the current size, any intermediate values will be set
     * to null.
     * @param index The position
     * @param element The element
     * @return The element
     */
    public E set(int index, E element) {
        if (index < list.size()) {
            return list.set(index, element);
        }
        else {
            list.ensureCapacity(index);
            for (int i = list.size(); i < index; i++) {
                list.add(null);
            }
            list.add(element);
            return null;
        }
    }

    public boolean remove(E element) {
        return list.remove(element);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }

    @Override
    public Iterator<E> structuralIterator() {
        return list.iterator();
    }

    @Override
    public Iterator<E> iterator() {
        final Iterator<E> iterator = list.iterator();
        return new PrefetchingIterator<E>() {
            @Override
            protected E calculateNext() throws NoSuchElementException {
                while(true) {
                    final E next = iterator.next();
                    if (next != null) {
                        return next;
                    }
                }
            }

            @Override
            protected boolean isRemoveSupported() {
                return true;
            }

            @Override
            protected void remove(E current) {
                iterator.remove();
            }
        };
    }

}
