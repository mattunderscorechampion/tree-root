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
import net.jcip.annotations.NotThreadSafe;

import java.util.*;

/**
 * A {@link com.mattunderscore.trees.collection.SimpleCollection} backed by {@link java.util.ArrayList}. Not
 * thread-safe. Mutable. Does not provide an iteration order or permit nulls.
 * @author Matt Champion on 09/09/14.
 */
@NotThreadSafe
public final class ArrayListSimpleCollection<E> implements SimpleCollection<E> {
    private final List<E> list;

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

    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("Nulls are not permitted in this collection");
        }
        else {
            list.add(element);
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
        return list.iterator();
    }

}
