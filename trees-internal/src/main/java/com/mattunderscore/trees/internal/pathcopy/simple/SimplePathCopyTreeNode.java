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

package com.mattunderscore.trees.internal.pathcopy.simple;

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.base.FixedNode;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.utilities.collections.DuplicateOnWriteSimpleCollection;
import com.mattunderscore.trees.utilities.iterators.ConvertingIterator;

import java.util.Iterator;

/**
 * Nodes of trees based on path copy. Unable to support removal through iterator because it reverts changes.
 * @author Matt Champion on 13/09/14.
*/
final class SimplePathCopyTreeNode<E> extends FixedNode<E> implements MutableNode<E> {
    private final SimplePathCopyTree<E> tree;
    private final SimplePathCopyTreeNode<E> parent;
    private DuplicateOnWriteSimpleCollection<ChildWrapper<E>> elementList;

    public SimplePathCopyTreeNode(SimplePathCopyTree<E> tree, E element) {
        super(element);
        this.tree = tree;
        parent = null;
        elementList = DuplicateOnWriteSimpleCollection.create();
    }

    public SimplePathCopyTreeNode(SimplePathCopyTreeNode<E> parent, E element) {
        super(element);
        tree = parent.tree;
        this.parent = parent;
        elementList = DuplicateOnWriteSimpleCollection.create();
    }

    @Override
    public SimpleCollection<? extends MutableNode<E>> getChildren() {
        return new CollectionProxy<>(elementList);
    }

    @Override
    public boolean removeChild(MutableNode<E> child) {
        synchronized (tree) {
            final SimplePathCopyResult<E> result = copyPath(this);
            final SimplePathCopyTreeNode<E> newNode = result.newNode;
            final DuplicateOnWriteSimpleCollection<ChildWrapper<E>> newCollection =
                    elementList.remove(new ChildWrapper<>((SimplePathCopyTreeNode<E>) child));

            if (newCollection.size() != elementList.size()) {
                newNode.elementList = newCollection;
                tree.root = result.newRoot;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public SimplePathCopyTreeNode<E> addChild(E e) {
        synchronized (tree) {
            final SimplePathCopyResult<E> result = copyPath(this);
            final SimplePathCopyTreeNode<E> newNode = result.newNode;
            final SimplePathCopyTreeNode<E> newChild = new SimplePathCopyTreeNode<E>(newNode, e);
            newNode.elementList = elementList.add(new ChildWrapper<>(newChild));
            tree.root = result.newRoot;
            return newChild;
        }
    }

    private SimplePathCopyResult<E> copyPath(SimplePathCopyTreeNode<E> oldNode) {
        if (oldNode.parent != null) {
            final SimplePathCopyResult<E> result = copyPath(oldNode.parent);
            final SimplePathCopyTreeNode<E> newParent = result.newNode;
            final SimplePathCopyTreeNode<E> newNode = new SimplePathCopyTreeNode(newParent, oldNode.element);
            final DuplicateOnWriteSimpleCollection<ChildWrapper<E>> oldChildren = oldNode.parent.elementList;
            newParent.elementList =
                oldChildren.replace(new ChildWrapper<>(newNode), new ChildWrapper<>(oldNode));
            return new SimplePathCopyResult<>(result.newRoot, newNode);
        }
        final SimplePathCopyTreeNode<E> newNode = new SimplePathCopyTreeNode(tree, oldNode.element);
        return new SimplePathCopyResult<>(newNode, newNode);
    }

    /**
     * Wrapper for nodes that checks equality based on the element of the node.
     * @param <E>
     */
    private static final class ChildWrapper<E> {
        private final SimplePathCopyTreeNode<E> child;

        private ChildWrapper(SimplePathCopyTreeNode<E> child) {
            this.child = child;
        }

        @Override
        public int hashCode() {
            return child.element.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            else if (obj == this) {
                return true;
            }
            else if (obj.getClass().equals(getClass())) {
                final ChildWrapper<?> unknownWrapper = (ChildWrapper<?>)obj;
                return child.getElement().equals(unknownWrapper.child.getElement());
            }
            else {
                return false;
            }
        }
    }

    /**
     * Proxy for the collection that unwraps the elements.
     * @param <E>
     */
    private static final class CollectionProxy<E> implements SimpleCollection<SimplePathCopyTreeNode<E>> {
        private final SimpleCollection<ChildWrapper<E>> delegate;

        private CollectionProxy(SimpleCollection<ChildWrapper<E>> delegate) {
            this.delegate = delegate;
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public Iterator<SimplePathCopyTreeNode<E>> iterator() {
            return new ConvertingIterator<SimplePathCopyTreeNode<E>, ChildWrapper<E>>(delegate.iterator()) {
                @Override
                protected SimplePathCopyTreeNode<E> convert(ChildWrapper<E> wrapped) {
                    return wrapped.child;
                }
            };
        }

        @Override
        public Iterator<SimplePathCopyTreeNode<E>> structuralIterator() {
            return new ConvertingIterator<SimplePathCopyTreeNode<E>, ChildWrapper<E>>(delegate.structuralIterator()) {
                @Override
                protected SimplePathCopyTreeNode<E> convert(ChildWrapper<E> wrapped) {
                    return wrapped.child;
                }
            };
        }
    }
}
