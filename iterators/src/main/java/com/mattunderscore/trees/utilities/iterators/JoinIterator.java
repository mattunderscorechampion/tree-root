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

package com.mattunderscore.trees.utilities.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.jcip.annotations.NotThreadSafe;

/**
 * An iterator that combines other iterators into a single one.
 * @author Matt Champion on 06/08/2015
 */
@NotThreadSafe
public final class JoinIterator<E> implements Iterator<E> {
    private final Iterator<E>[] iterators;
    private volatile int currentIndex = 0;

    private JoinIterator(Iterator<E>[] iterators) {
        this.iterators = iterators;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < iterators.length - 1 || (currentIndex != iterators.length && iterators[currentIndex].hasNext());
    }

    @Override
    public E next() {
        while (true) {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else if (iterators[currentIndex].hasNext()) {
                return iterators[currentIndex].next();
            }
            currentIndex++;
        }
    }

    /**
     * @param <E> the type of elements returned by the iterator
     * @return A new builder
     */
    public static <E> Builder<E> builder() {
        return new Builder<E>().estimatedSize(5);
    }

    /**
     * Builder for the iterator.
     * @param <E> the type of elements returned by the iterator
     */
    @NotThreadSafe
    public static final class Builder<E> {
        private final ArrayList<Iterator<? extends E>> iterators;

        private Builder() {
            iterators = new ArrayList<>();
        }

        /**
         * Set an estimated size for the number of iterators to join
         * @param estimatedSize the number of iterators
         * @return the builder
         */
        public Builder<E> estimatedSize(int estimatedSize) {
            iterators.ensureCapacity(estimatedSize);
            return this;
        }

        /**
         * Add another iterator.
         * @param iterator the new iterator
         * @return The builder
         */
        public Builder<E> join(Iterator<? extends E> iterator) {
            // Ignore empty iterators
            if (iterator.hasNext()) {
                iterators.add(iterator);
            }
            return this;
        }

        /**
         * @return The joined iterator
         */
        @SuppressWarnings("unchecked")
        public Iterator<E> build() {
            final Iterator<E>[] iteratorArray = (Iterator<E>[])new Iterator[iterators.size()];
            iterators.toArray(iteratorArray);
            return new JoinIterator<>(iteratorArray);
        }
    }
}
