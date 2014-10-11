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

package com.mattunderscore.trees.common.synchronised;

import com.mattunderscore.trees.mutable.MutableNode;

import java.util.Iterator;

/**
 * Synchronised iterator for mutable nodes.
* @author Matt Champion on 09/10/14.
*/
public abstract class SynchronisedIterator<E> implements Iterator<E> {
    private final Object lock;
    private final Iterator<E> delegateIterator;


    SynchronisedIterator(Object lock, Iterator<E> delegateIterator) {
        this.lock = lock;
        this.delegateIterator = delegateIterator;
    }

    @Override
    public boolean hasNext() {
        synchronized (lock) {
            return delegateIterator.hasNext();
        }
    }

    @Override
    public E next() {
        synchronized (lock) {
            return synchroniseElement(lock, delegateIterator.next());
        }
    }

    @Override
    public void remove() {
        synchronized (lock) {
            delegateIterator.remove();
        }
    }

    protected abstract E synchroniseElement(Object lock, E element);
}
