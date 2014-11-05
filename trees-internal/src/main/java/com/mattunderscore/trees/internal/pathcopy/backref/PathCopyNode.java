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

import com.mattunderscore.trees.base.FixedNode;
import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.ids.HasId;
import com.mattunderscore.trees.ids.Id;
import com.mattunderscore.trees.ids.IdGenerator;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.utilities.collections.DuplicateOnWriteSimpleCollection;

/**
 * Path copy tree node that uses a separate data structure for back references.
 * @author Matt Champion on 02/11/14.
 */
public final class PathCopyNode<E> extends FixedNode<E> implements MutableNode<E>, HasId {
    private final IdGenerator generator;
    private final DuplicateOnWriteSimpleCollection<PathCopyNode<E>> children;
    private final Id id;
    final PathCopyNodeHolder<E> holder;

    private PathCopyNode(IdGenerator generator, Id id, PathCopyNodeHolder<E> holder, E element) {
        this(generator, id, holder, element, DuplicateOnWriteSimpleCollection.<PathCopyNode<E>>create());
    }

    private PathCopyNode(IdGenerator generator, Id id, PathCopyNodeHolder<E> holder, E element, DuplicateOnWriteSimpleCollection<PathCopyNode<E>> children) {
        super(element);
        this.generator = generator;
        this.id = id;
        this.holder = holder;
        this.children = children;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public SimpleCollection<? extends MutableNode<E>> getChildren() {
        return children;
    }

    @Override
    public boolean removeChild(MutableNode<E> child) {
        if (child instanceof PathCopyNode) {
            final PathCopyNode<E> childNode = (PathCopyNode<E>) child;
            childNode.holder.lock();
            try {
                holder.lock();
                try {
                    final DuplicateOnWriteSimpleCollection<PathCopyNode<E>> newChildren = children.remove(childNode);
                    if (newChildren.size() == children.size()) {
                        return false;
                    }
                    else {
                        final PathCopyNode<E> newNode = new PathCopyNode<E>(generator, id, holder, element, newChildren);
                        copyPath(generator, newNode);
                        return true;
                    }
                }
                finally {
                    holder.unlock();
                }
            }
            finally {
                childNode.holder.unlock();
            }
        }
        else {
            return false;
        }
    }

    @Override
    public MutableNode<E> addChild(E e) {
        holder.lock();
        try {
            final PathCopyNode<E> child = createNew(new PathCopyNodeHolder<E>(holder), generator, e);
            attachChild(generator, this, child);
            return child;
        }
        finally {
            holder.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PathCopyNode<E> that = (PathCopyNode<E>) o;

        if (!children.equals(that.children)) {
            return false;
        }
        else if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = children.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    static <E> PathCopyNode<E> createRoot(IdGenerator idGenerator, E element) {
        return createNew(null, idGenerator, element);
    }

    static <E> PathCopyNode<E> createNew(PathCopyNodeHolder<E> parentHolder, IdGenerator idGenerator, E element) {
        final PathCopyNodeHolder<E> holder = new PathCopyNodeHolder<>(parentHolder);
        final PathCopyNode<E> node = new PathCopyNode<>(idGenerator, idGenerator.next(), holder, element);
        holder.lock();
        try {
            holder.set(node);
        }
        finally {
            holder.unlock();
        }
        return node;
    }

    static <E> void copyPath(IdGenerator generator, PathCopyNode<E> newNode) {
        newNode.holder.set(newNode);
        if (newNode.holder.parent != null) {
            newNode.holder.parent.lock();
            try {
                final PathCopyNode<E> oldParent = newNode.holder.parent.get();
                if (oldParent != null) {
                    final DuplicateOnWriteSimpleCollection<PathCopyNode<E>> newChildren = oldParent.children.replace(newNode, newNode);
                    final PathCopyNode<E> newParent = new PathCopyNode<E>(generator, oldParent.id, oldParent.holder, oldParent.element, newChildren);
                    copyPath(generator, newParent);
                }
                else {
                    // TODO: Set root
                }
            }
            finally {
                newNode.holder.parent.unlock();
            }
        }
        else {
            // TODO: Set root
        }
    }

    static <E> PathCopyNode<E> attachChild(IdGenerator generator, PathCopyNode<E> parent, PathCopyNode<E> child) {
        parent.holder.lock();
        try {
            final PathCopyNode<E> currentParent = parent.holder.get();
            final DuplicateOnWriteSimpleCollection<PathCopyNode<E>> newChildren = currentParent.children.add(child);
            final PathCopyNode<E> newParent = new PathCopyNode<E>(generator, parent.id, parent.holder, parent.element, newChildren);
            copyPath(generator, newParent);

            return child;
        }
        finally {
            parent.holder.unlock();
        }
    }
}
