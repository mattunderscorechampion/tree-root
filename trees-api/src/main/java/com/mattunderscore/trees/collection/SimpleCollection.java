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

package com.mattunderscore.trees.collection;

import java.util.Iterator;

/**
 * A simplified collection that implements {@link java.lang.Iterable}. It exposes two iterators, one which returns only
 * elements (returned by {@link #iterator()}) and another which may return null ({@link #structuralIterator()}).
 * Implementations of this collection may either permit or reject nulls. Nulls represent an empty place where a element
 * might be. It should only permit nulls if there is an iteration order.
 * @author Matt Champion on 09/09/14.
 */
public interface SimpleCollection<N> extends Iterable<N> {

    /**
     * Returns an estimate of the number of elements in the collection. It may count nulls in the collection.
     * @return The number of children
     */
    int size();

    /**
     * @return {@code true} if there are no children
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the elements in this collection. Only some implementations may guarantee an iteration
     * order. It is guaranteed not to return null.
     * @return an Iterator over the elements in this collection
     */
    @Override
    Iterator<N> iterator();

    /**
     * An iterator over the structure of the collection. If there is an empty place in the collection where an element
     * might be but is not then the iterator will not return null. It will only return null if there is an iteration
     * order.
     * @return an Iterator over the elements in this collection
     */
    Iterator<N> structuralIterator();
}
