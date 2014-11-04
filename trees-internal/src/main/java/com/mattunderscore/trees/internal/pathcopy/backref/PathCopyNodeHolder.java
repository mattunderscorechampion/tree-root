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

package com.mattunderscore.trees.internal.pathcopy.backref;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Path copy tree node back reference data structure.
 * @author Matt Champion on 02/11/14.
 */
public final class PathCopyNodeHolder<E> {
    final PathCopyNodeHolder<E> parent;
    private final ReentrantLock lock = new ReentrantLock();
    private PathCopyNode<E> currentNode;

    public PathCopyNodeHolder(PathCopyNodeHolder<E> parent) {
        this.parent = parent;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public PathCopyNode<E> get() {
        if (lock.isHeldByCurrentThread()) {
            return currentNode;
        }
        else {
            throw new IllegalStateException("Lock must be held");
        }
    }

    public void set(PathCopyNode<E> node) {
        if (lock.isHeldByCurrentThread()) {
            currentNode = node;
        }
        else {
            throw new IllegalStateException("Lock must be held");
        }
    }
}
