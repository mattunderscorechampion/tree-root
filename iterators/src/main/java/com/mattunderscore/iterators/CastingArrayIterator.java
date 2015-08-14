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

package com.mattunderscore.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.jcip.annotations.NotThreadSafe;

/**
 * An iterator over object arrays that casts the objects to another class. The next method may throw a
 * ClassCastException if the wrong type of array is provided. The iterator does not maintain a thread safe position
 * counter. Two thread both accessing the same iterator may both receive the same object.
 * @param <E> The element type
 * @author Matt Champion on 11/09/14.
 */
@NotThreadSafe
public final class CastingArrayIterator<E> implements Iterator<E> {
    private final Object[] array;
    private int pos;

    CastingArrayIterator(Object[] array) {
        this.array = array;
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < array.length;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E next() {
        if (pos < array.length) {
            return (E) array[pos++];
        }
        else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Create an iterator over an array known to be the correct type. Does not really cast. Does not copy the array.
     * @param array The array
     * @param <E> The element type
     * @return The iterator
     * @deprecated Prefer the use of {@link ArrayIterator} in this case
     */
    @Deprecated
    public static <E> CastingArrayIterator<E> create(E[] array) {
        return new CastingArrayIterator<>(array);
    }

    /**
     * Create an iterator over an array known to be the correct type. Does not really cast. Does not copy the array.
     * @param array The array
     * @param <E> The element type
     * @return The iterator
     */
    public static <E> CastingArrayIterator<E> unsafeCreate(Object[] array) {
        return new CastingArrayIterator<>(array);
    }
}
