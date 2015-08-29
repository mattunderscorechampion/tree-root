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

package com.mattunderscore.simple.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import net.jcip.annotations.NotThreadSafe;

import com.mattunderscore.iterators.FilteringIterator;

/**
 * Wrap a {@link Collection} as a {@link SimpleCollection}.
 * @author Matt Champion on 29/08/2015
 */
@NotThreadSafe
public final class WrappingSimpleCollection<E> implements SimpleCollection<E> {
    private final Collection<E> delegateCollection;

    public WrappingSimpleCollection(Collection<E> delegateCollection) {
        this.delegateCollection = delegateCollection;
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
        return new FilteringIterator<E>(delegateCollection.iterator()) {
            @Override
            protected boolean accept(E element) {
                return element != null;
            }
        };
    }

    @Override
    public Iterator<E> structuralIterator() {
        return delegateCollection.iterator();
    }

    @Override
    public Stream<E> stream() {
        return delegateCollection.stream();
    }
}
