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

package com.mattunderscore.trees.utilities.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstract iterator implementation.
 * Iterator that may prefetch the element to return next on hasNext.
 * @author matt on 25/06/14.
 */
public abstract class PrefetchingIterator<E> implements Iterator<E> {
    private E prefetched;

    @Override
    public final boolean hasNext() {
        if (prefetched != null) {
            return true;
        }
        else {
            try {
                prefetched = calculateNext();
                return true;
            }
            catch (NoSuchElementException e) {
                return false;
            }
        }
    }

    @Override
    public final E next() {
        if (prefetched != null) {
            final E next = prefetched;
            prefetched = null;
            return next;
        }
        else {
            return calculateNext();
        }
    }

    /**
     * @return The next element to return when asked
     * @throws NoSuchElementException If no more elements
     */
    protected abstract E calculateNext() throws NoSuchElementException;
}
