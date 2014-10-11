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

import java.util.Iterator;

/**
 * Collection that converts elements of one collection to another.
 * @author Matt Champion on 11/10/14.
 */
public abstract class ConvertingSimpleCollection<E, D> implements SimpleCollection<E> {
    private final SimpleCollection<D> delegateCollection;

    protected ConvertingSimpleCollection(SimpleCollection<D> collection) {
        delegateCollection = collection;
    }

    @Override
    public int size() {
        return delegateCollection.size();
    }

    @Override
    public boolean isEmpty() {
        return delegateCollection.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return createConvertingIterator(delegateCollection.iterator());
    }

    @Override
    public Iterator<E> structuralIterator() {
        return createConvertingIterator(delegateCollection.structuralIterator());
    }

    /**
     * Create a converting iterator.
     * @param delegateIterator The delegate iterator.
     * @return The converting iterator.
     */
    protected abstract Iterator<E> createConvertingIterator(Iterator<D> delegateIterator);
}
