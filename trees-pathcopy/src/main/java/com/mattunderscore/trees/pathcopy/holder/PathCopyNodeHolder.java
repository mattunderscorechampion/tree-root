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

package com.mattunderscore.trees.pathcopy.holder;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.simple.collections.DuplicateOnWriteSimpleCollection;

/**
 * Node holder for child nodes.
 * @author Matt Champion on 14/11/14.
 */
final class PathCopyNodeHolder<E> implements Holder<E> {
    private final Holder<E> parent;
    private final AtomicReference<PathCopyNode<E>> currentNodeRef;
    private final Lock lock = new ReentrantLock();

    public PathCopyNodeHolder(Holder<E> parent) {
        this.parent = parent;
        currentNodeRef = new AtomicReference<>();
    }

    public PathCopyNode<E> get() {
        return currentNodeRef.get();
    }

    public void set(PathCopyNode<E> node) {
        currentNodeRef.set(node);
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unlock() {
        lock.unlock();
    }

    public void propagate(PathCopyNode<E> currentNode, PathCopyNode<E> newNode) {
        final PathCopyNode<E> currentParent;
        final PathCopyNode<E> newParent;
        parent.lock();
        try {
            currentParent = parent.get();
            final DuplicateOnWriteSimpleCollection<MutableNode<E>> newChildren =
                    currentParent.getChildren().replace(newNode, currentNode);
            newParent = new PathCopyNode<>(parent, currentParent.getElement(), newChildren);
            parent.set(newParent);
        }
        finally {
            parent.unlock();
        }

        parent.propagate(currentParent, newParent);
    }
}
