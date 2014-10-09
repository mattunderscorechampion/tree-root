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

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.mutable.MutableNode;

import java.util.Iterator;

/**
 * Synchronised mutable node collection.
* @author Matt Champion on 09/10/14.
*/
final class SynchronisedSimpleNodeCollection<E> implements SimpleCollection<MutableNode<E>> {
    private final Object lock;
    private final SimpleCollection<? extends MutableNode<E>> delegateCollection;

    SynchronisedSimpleNodeCollection(Object lock, SimpleCollection<? extends MutableNode<E>> delegateCollection) {
        this.lock = lock;
        this.delegateCollection = delegateCollection;
    }

    @Override
    public int size() {
        synchronized (lock) {
            return delegateCollection.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (lock) {
            return delegateCollection.isEmpty();
        }
    }

    @Override
    public Iterator<MutableNode<E>> iterator() {
        synchronized (lock) {
            return new SynchronisedIterator<>(lock, delegateCollection.iterator());
        }
    }

    @Override
    public Iterator<MutableNode<E>> structuralIterator() {
        synchronized (lock) {
            return new SynchronisedIterator<>(lock, delegateCollection.structuralIterator());
        }
    }
}
