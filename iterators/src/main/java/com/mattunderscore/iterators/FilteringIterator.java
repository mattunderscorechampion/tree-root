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

package com.mattunderscore.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstract filtering iterator. Filters returned elements by a predicate.
 * @author Matt Champion on 14/08/2015
 */
public abstract class FilteringIterator<E> extends PrefetchingIterator<E> {
    private final Iterator<E> delegate;

    /**
     * Constructor.
     * @param delegate An iterator over the unfiltered elements.
     */
    protected FilteringIterator(Iterator<E> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected E calculateNext() throws NoSuchElementException {
        while(true) {
            final E next = delegate.next();
            if (accept(next)) {
                return next;
            }
        }
    }

    /**
     * Method to evaluate the predicate.
     * @param element The element to evaluate
     * @return {@code true} if the element should be returned by the iterator
     */
    protected abstract boolean accept(E element);
}
