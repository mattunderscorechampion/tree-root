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
import com.mattunderscore.trees.tree.Node;

/**
 * Synchronised mutable node.
* @author Matt Champion on 09/10/14.
*/
final class SynchronisedMutableNode<E> implements MutableNode<E> {
    private final Object lock;
    private final MutableNode<E> delegateNode;

    public SynchronisedMutableNode(Object lock, MutableNode<E> delegateNode) {
        this.lock = lock;
        this.delegateNode = delegateNode;
    }

    @Override
    public E getElement() {
        synchronized (lock) {
            return delegateNode.getElement();
        }
    }

    @Override
    public Class<E> getElementClass() {
        synchronized (lock) {
            return delegateNode.getElementClass();
        }
    }

    @Override
    public SimpleCollection<MutableNode<E>> getChildren() {
        synchronized (lock) {
            return new Collection<E>(lock, (SimpleCollection<MutableNode<E>>)delegateNode.getChildren());
        }
    }

    @Override
    public boolean isLeaf() {
        synchronized (lock) {
            return delegateNode.isLeaf();
        }
    }

    @Override
    public boolean removeChild(MutableNode<E> child) {
        synchronized (lock) {
            final SynchronisedMutableNode<E> wrappedChild = (SynchronisedMutableNode<E>) child;
            return delegateNode.removeChild(wrappedChild.delegateNode);
        }
    }

    @Override
    public MutableNode<E> addChild(E e) {
        synchronized (lock) {
            return new SynchronisedMutableNode<>(lock, delegateNode.addChild(e));
        }
    }

    private static final class Collection<E> extends SynchronisedSimpleNodeCollection<E, MutableNode<E>> {

        Collection(Object lock, SimpleCollection<MutableNode<E>> delegateCollection) {
            super(lock, delegateCollection);
        }

        @Override
        protected MutableNode<E> synchroniseElement(Object lock, MutableNode<E> element) {
            return new SynchronisedMutableNode<>(lock, element);
        }
    }
}
