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

package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.IMutableTree;
import com.mattunderscore.trees.INode;
import com.mattunderscore.trees.ITree;
import com.mattunderscore.trees.spi.IEmptyTreeConstructor;
import com.mattunderscore.trees.spi.INodeToTreeConverter;
import com.mattunderscore.trees.spi.ITreeConstructor;
import com.mattunderscore.trees.spi.ITreeConverter;
import com.mattunderscore.trees.utilities.FixedUncheckedList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author matt on 10/06/14.
 */
public final class TreeNodeImpl<E> implements ITree<E, INode<E>>, INode<E> {
    private final E element;
    private final List<INode<E>> children;

    public TreeNodeImpl(E element, List<INode<E>> children) {
        this.element = element;
        this.children = children;
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
    public Collection<INode<E>> getChildren() {
        return children;
    }

    @Override
    public INode<E> getRoot() {
        return this;
    }

    public final static class Constructor<E> implements ITreeConstructor<E, TreeNodeImpl<E>> {

        @Override
        public TreeNodeImpl<E> build(E e, TreeNodeImpl<E>... subtrees) {
            return new TreeNodeImpl(e, new FixedUncheckedList<>(subtrees));
        }

        @Override
        public Class<?> forClass() {
            return ITree.class;
        }
    }

    public final static class EmptyConstructor<E> implements IEmptyTreeConstructor<E, TreeNodeImpl<E>> {

        @Override
        public TreeNodeImpl<E> build() {
            return new TreeNodeImpl(null, Collections.<INode<E>>emptyList());
        }

        @Override
        public Class<?> forClass() {
            return ITree.class;
        }
    }

    public static final class NodeConverter<E> implements INodeToTreeConverter<E, INode<E>, ITree<E, INode<E>>> {

        @Override
        public ITree<E, INode<E>> treeFromRootNode(INode<E> node) {
            return (TreeNodeImpl<E>)node;
        }

        @Override
        public Class<?> forClass() {
            return TreeNodeImpl.class;
        }
    }

    public static final class Converter<E> implements ITreeConverter<E, TreeNodeImpl<E>> {

        @Override
        public TreeNodeImpl<E> build(ITree<E, ? extends INode<E>> sourceTree) {
            final INode<E> root = sourceTree.getRoot();
            return new TreeNodeImpl(root.getElement(), duplicateChildren(root.getChildren()));
        }

        @Override
        public Class<?> forClass() {
            return ITree.class;
        }

        private List<INode<E>> duplicateChildren(Collection<? extends INode<E>> children) {
            final INode[] newChildren = new INode[children.size()];
            int i = 0;
            for (final INode<E> sourceChild : children) {
                final List<INode<E>> newGrandChildren = duplicateChildren(sourceChild.getChildren());
                newChildren[i] = new TreeNodeImpl<>(sourceChild.getElement(), newGrandChildren);
                i++;
            }
            return new FixedUncheckedList<>(newChildren);
        }
    }
}
