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

package com.mattunderscore.trees.utilities;

import com.mattunderscore.trees.Children;
import com.mattunderscore.trees.OptionalEnumeration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author matt on 09/09/14.
 */
public final class ArrayListChildren<E> implements Children<E> {
    private final List<E> list;

    public ArrayListChildren() {
        list = new ArrayList<>();
    }

    public ArrayListChildren(Collection<E> initial) {
        list = new ArrayList<>(initial);
    }

    public void add(E element) {
        list.add(element);
    }

    public boolean remove(Object element) {
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
    public E get(int i) {
        return list.get(i);
    }

    @Override
    public OptionalEnumeration<E> optionalEnumeration() {
        return new OEnum();
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    private final class OEnum implements OptionalEnumeration<E> {
        final Iterator<E> iterator = list.iterator();

        @Override
        public boolean hasMoreElements() {
            return iterator.hasNext();
        }

        @Override
        public E nextElement() {
            return iterator.next();
        }
    }
}
