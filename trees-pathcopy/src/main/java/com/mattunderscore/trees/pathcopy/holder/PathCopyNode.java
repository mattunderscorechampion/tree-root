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

import java.util.Iterator;

import com.mattunderscore.trees.base.ImmutableNode;
import com.mattunderscore.trees.base.MutableChildIterator;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.simple.collections.DuplicateOnWriteSimpleCollection;

/**
 * Path copy node that uses holders to propagate changes up.
 * @author Matt Champion on 14/11/14.
 */
public final class PathCopyNode<E> extends ImmutableNode<E, MutableNode<E>> implements MutableNode<E> {
    private final Holder<E> holder;

    PathCopyNode(Holder<E> holder, E element) {
        this(holder, element, DuplicateOnWriteSimpleCollection.<MutableNode<E>>create());
    }

    PathCopyNode(Holder<E> holder, E element, DuplicateOnWriteSimpleCollection<MutableNode<E>> children) {
        super(element, children);
        this.holder = holder;
    }

    public DuplicateOnWriteSimpleCollection<MutableNode<E>> getChildren() {
        return (DuplicateOnWriteSimpleCollection<MutableNode<E>>) children;
    }

    @Override
    public boolean removeChild(MutableNode<E> child) {
        final PathCopyNode<E> castChild = (PathCopyNode<E>)child;
        PathCopyNode<E> newParent = null;
        boolean modified = false;
        holder.lock();
        try {
            final DuplicateOnWriteSimpleCollection<MutableNode<E>> currentChildren = holder.get().getChildren();
            final DuplicateOnWriteSimpleCollection<MutableNode<E>> modifiedChildren = currentChildren.remove(castChild);
            if (modifiedChildren.size() != currentChildren.size()) {
                newParent = new PathCopyNode<>(holder, element, modifiedChildren);
                holder.set(newParent);
                modified = true;
            } else {
                modified = false;
            }
        }
        finally {
            holder.unlock();
        }
        if (modified) {
            holder.propagate(this, newParent);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public MutableNode<E> addChild(E e) {
        final PathCopyNodeHolder<E> childHolder = new PathCopyNodeHolder<>(holder);
        final PathCopyNode<E> child = new PathCopyNode<>(childHolder, e);
        final PathCopyNode<E> currentParent;
        final PathCopyNode<E> newParent;
        childHolder.set(child);
        holder.lock();
        try {
            currentParent = holder.get();
            newParent = new PathCopyNode<>(holder, element, holder.get().getChildren().add(child));
            holder.set(newParent);
        }
        finally {
            holder.unlock();
        }

        holder.propagate(currentParent, newParent);
        return child;
    }

    @Override
    public Iterator<MutableNode<E>> childIterator() {
        return new MutableChildIterator<>(this, children.iterator());
    }
}
