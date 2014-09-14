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

package com.mattunderscore.trees.utilities.collections;

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.utilities.iterators.CastingArrayIterator;
import net.jcip.annotations.Immutable;

import java.util.*;

/**
 * @author Matt Champion on 11/09/14.
 */
@Immutable
public final class DuplicateOnWriteSimpleCollection<E> implements SimpleCollection<E> {
    private final Object[] elements;

    public DuplicateOnWriteSimpleCollection() {
        elements = new Object[0];
    }

    public DuplicateOnWriteSimpleCollection(Object[] collection) {
        elements = new Object[collection.length];
        for (int i = 0; i < collection.length; i++) {
            elements[i] = collection[i];
        }
    }

    public DuplicateOnWriteSimpleCollection(Collection<E> collection) {
        elements = new Object[collection.size()];
        final Iterator<E> iterator = collection.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            elements[i++] = iterator.next();
        }
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public boolean isEmpty() {
        return elements.length == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new CastingArrayIterator<>(elements);
    }

    @Override
    public Iterator<E> structuralIterator() {
        return new CastingArrayIterator<>(elements);
    }

    /**
     * Add a new element to a new collection
     * @param element the element to add
     * @return the modified collection
     */
    public DuplicateOnWriteSimpleCollection<E> add(E element) {
        Object[] newElements = Arrays.copyOf(elements, elements.length + 1);
        newElements[elements.length] = element;
        return new DuplicateOnWriteSimpleCollection<>(newElements);
    }

    /**
     * Remove an element from a new collection
     * @param element the element to remove
     * @return the modified collection
     */
    public DuplicateOnWriteSimpleCollection<E> remove(E element) {
        final List<Object> tmpElements = new ArrayList<>(elements.length);
        boolean removed = false;
        for (Object o : elements) {
            if (!removed && o.equals(element)) {
                removed = true;
            }
            else {
                tmpElements.add(o);
            }
        }

        if (removed) {
            return new DuplicateOnWriteSimpleCollection<>(tmpElements.toArray());
        }
        else {
            return this;
        }
    }

    /**
     * Replace an element in a new collection. If the old element is not present the collection is not changed.
     * @param newElement the element to add
     * @param oldElement the element to remove
     * @return the modified collection
     */
    public DuplicateOnWriteSimpleCollection<E> replace(E newElement, E oldElement) {
        final Object[] oldElements = elements;
        final List<Object> tmpElements = new ArrayList<>(oldElements.length);
        boolean removed = false;
        for (Object o : oldElements) {
            if (!removed && o.equals(oldElement)) {
                removed = true;
                tmpElements.add(newElement);
            }
            else {
                tmpElements.add(o);
            }
        }

        if (removed) {
            return new DuplicateOnWriteSimpleCollection<>(tmpElements.toArray());
        }
        else {
            return this;
        }
    }
}
