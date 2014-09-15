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

package com.mattunderscore.trees.internal.pathcopy;

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.utilities.collections.DuplicateOnWriteSimpleCollection;
import com.mattunderscore.trees.utilities.iterators.ConvertingIterator;

import java.util.Iterator;

/**
 * Nodes of trees based on path copy.
 * @author Matt Champion on 13/09/14.
*/
final class PathCopyTreeNode<E> implements MutableNode<E> {
    private final PathCopyTree<E> tree;
    private final PathCopyTreeNode<E> parent;
    private final E element;
    private DuplicateOnWriteSimpleCollection<ChildWrapper<E>> elementList;

    public PathCopyTreeNode(PathCopyTree<E> tree, E element) {
        this.tree = tree;
        this.element = element;
        parent = null;
        elementList = new DuplicateOnWriteSimpleCollection<>();
    }

    public PathCopyTreeNode(PathCopyTreeNode<E> parent, E element) {
        tree = null;
        this.element = element;
        this.parent = parent;
        elementList = new DuplicateOnWriteSimpleCollection<>();
    }

    @Override
    public E getElement() {
        return element;
    }

    @Override
    public Class<E> getElementClass() {
        return (Class<E>)element.getClass();
    }

    @Override
    public SimpleCollection<? extends MutableNode<E>> getChildren() {
        return new CollectionProxy<>(elementList);
    }

    @Override
    public boolean isLeaf() {
        return elementList.isEmpty();
    }

    @Override
    public boolean removeChild(MutableNode<E> child) {
        final PathCopyResult<E> result = copyPath(this);
        final PathCopyTreeNode<E> newNode = result.newNode;
        final DuplicateOnWriteSimpleCollection<ChildWrapper<E>> newCollection =
                elementList.remove(new ChildWrapper<>((PathCopyTreeNode<E>) child));

        if (newCollection.size() != elementList.size()) {
            newNode.elementList = newCollection;
            final PathCopyTree<E> tree = result.newRoot.tree;
            tree.checkAndSetRootNode(result.newRoot, result.oldRoot);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public PathCopyTreeNode<E> addChild(E e) {
        final PathCopyResult<E> result = copyPath(this);
        final PathCopyTreeNode<E> newNode = result.newNode;
        final PathCopyTreeNode<E> newChild = new PathCopyTreeNode<E>(newNode, e);
        newNode.elementList = elementList.add(new ChildWrapper<>(newChild));
        final PathCopyTree<E> tree = result.newRoot.tree;
        tree.checkAndSetRootNode(result.newRoot, result.oldRoot);
        return newChild;
    }

    private PathCopyResult<E> copyPath(PathCopyTreeNode<E> oldNode) {
        if (oldNode.parent != null) {
            final PathCopyResult<E> result = copyPath(oldNode.parent);
            final PathCopyTreeNode<E> newParent = result.newNode;
            final PathCopyTreeNode<E> newNode = new PathCopyTreeNode(newParent, oldNode.element);
            final DuplicateOnWriteSimpleCollection<ChildWrapper<E>> oldChildren = oldNode.parent.elementList;
            newParent.elementList =
                oldChildren.replace(new ChildWrapper<>(newNode), new ChildWrapper<>(oldNode));
            return new PathCopyResult<>(result.newRoot, result.oldRoot, newNode);
        }
        final PathCopyTreeNode<E> newNode = new PathCopyTreeNode(tree, oldNode.element);
        return new PathCopyResult<>(newNode, oldNode, newNode);
    }

    /**
     * Wrapper for nodes that checks equality based on the element of the node.
     * @param <E>
     */
    private static final class ChildWrapper<E> {
        private final PathCopyTreeNode<E> child;

        private ChildWrapper(PathCopyTreeNode<E> child) {
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
    private static final class CollectionProxy<E> implements SimpleCollection<PathCopyTreeNode<E>> {
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
        public Iterator<PathCopyTreeNode<E>> iterator() {
            return new ConvertingIterator<PathCopyTreeNode<E>, ChildWrapper<E>>(delegate.iterator()) {
                @Override
                protected PathCopyTreeNode<E> convert(ChildWrapper<E> wrapped) {
                    return wrapped.child;
                }
            };
        }

        @Override
        public Iterator<PathCopyTreeNode<E>> structuralIterator() {
            return new ConvertingIterator<PathCopyTreeNode<E>, ChildWrapper<E>>(delegate.structuralIterator()) {
                @Override
                protected PathCopyTreeNode<E> convert(ChildWrapper<E> wrapped) {
                    return wrapped.child;
                }
            };
        }
    }
}
