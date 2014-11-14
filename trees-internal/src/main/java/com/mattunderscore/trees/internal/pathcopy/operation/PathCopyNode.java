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

package com.mattunderscore.trees.internal.pathcopy.operation;

import com.mattunderscore.trees.base.FixedNode;
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.utilities.collections.DuplicateOnWriteSimpleCollection;

/**
 * @author matt on 14/11/14.
 */
final class PathCopyNode<E> extends FixedNode<E> implements MutableNode<E> {
    private final DuplicateOnWriteSimpleCollection<PathCopyNode<E>> children;
    private final Holder<E> holder;

    PathCopyNode(Holder<E> holder, E element) {
        this(holder, element, DuplicateOnWriteSimpleCollection.<PathCopyNode<E>>create());
    }

    PathCopyNode(Holder<E> holder, E element, DuplicateOnWriteSimpleCollection<PathCopyNode<E>> children) {
        super(element);
        this.holder = holder;
        this.children = children;
    }

    @Override
    public DuplicateOnWriteSimpleCollection<PathCopyNode<E>> getChildren() {
        return children;
    }

    @Override
    public boolean removeChild(MutableNode<E> child) {
        final PathCopyNode<E> castChild = (PathCopyNode<E>)child;
        final DuplicateOnWriteSimpleCollection<PathCopyNode<E>> currentChildren = holder.get().getChildren();
        final DuplicateOnWriteSimpleCollection<PathCopyNode<E>> modifiedChildren = currentChildren.remove(castChild);
        if (modifiedChildren.size() != currentChildren.size()) {
            final PathCopyNode<E> newParent = new PathCopyNode<>(holder, element, modifiedChildren);
            holder.set(newParent);
            holder.propagate(this, newParent);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public MutableNode<E> addChild(E e) {
        final PathCopyNodeHolder<E> childHolder = new PathCopyNodeHolder<>(holder, null);
        final PathCopyNode<E> child = new PathCopyNode<E>(childHolder, e);
        childHolder.set(child);
        final PathCopyNode<E> currentParent = holder.get();
        final PathCopyNode<E> newParent = new PathCopyNode<>(holder, element, holder.get().getChildren().add(child));
        holder.set(newParent);
        holder.propagate(currentParent, newParent);
        return child;
    }
}
