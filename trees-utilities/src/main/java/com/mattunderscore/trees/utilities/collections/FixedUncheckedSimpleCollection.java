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
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;

import java.util.*;

/**
 * Array backed, not typed checked, {@link com.mattunderscore.trees.collection.SimpleCollection} implementation for an immutable
 * collection from a trusted source. It provides an iteration order and permits nulls.
 * <p>This is immutable assuming the ownership of the backing array is exclusive. Hence the need to be created by a
 * trusted source.</p>
 * @author Matt Champion on 20/06/14.
 */
public final class FixedUncheckedSimpleCollection<E> implements SimpleCollection<E> {
    private final Object[] array;

    public FixedUncheckedSimpleCollection(Object[] array) {
        this.array = array;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public Iterator<E> structuralIterator() {
        return new FUSCStructuralIterator();
    }

    @Override
    public Iterator<E> iterator() {
        return new FUSCIterator();
    }

    private final class FUSCIterator extends PrefetchingIterator<E> {
        private int pos;

        public FUSCIterator() {
            pos = 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected E calculateNext() throws NoSuchElementException {
            while (pos < array.length) {
                final E next = (E) array[pos++];
                if (next != null) {
                    return next;
                }
            }
            throw new NoSuchElementException();
        }
    }

    private final class FUSCStructuralIterator implements Iterator<E> {
        private int pos;

        public FUSCStructuralIterator() {
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
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
