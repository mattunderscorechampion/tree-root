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

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.utilities.FixedUncheckedList;

import net.jcip.annotations.Immutable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author matt on 10/06/14.
 */
@Immutable
public final class TreeNodeImpl<E> implements Tree<E, Node<E>>, Node<E> {
    private final E element;
    private final List<Node<E>> children;

    public TreeNodeImpl(E element, List<Node<E>> children) {
        this.element = element;
        this.children = children;
    }

    @Override
    public E getElement() {
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<E> getElementClass() {
        return (Class<E>)element.getClass();
    }

    @Override
    public Collection<Node<E>> getChildren() {
        return children;
    }

    @Override
    public Node<E> getRoot() {
        if (isEmpty()) {
            return null;
        }
        else {
            return this;
        }
    }

    @Override
    public boolean isEmpty() {
        return element == null;
    }

    @Override
    public boolean isLeaf() {
        return children.size() == 0;
    }

    public final static class Constructor<E> implements TreeConstructor<E, TreeNodeImpl<E>> {

        @Override
        public TreeNodeImpl<E> build(E e, TreeNodeImpl<E>... subtrees) {
            return new TreeNodeImpl(e, new FixedUncheckedList<>(subtrees));
        }

        @Override
        public Class<?> forClass() {
            return Tree.class;
        }
    }

    public final static class EmptyConstructor<E> implements EmptyTreeConstructor<E, TreeNodeImpl<E>> {

        @Override
        public TreeNodeImpl<E> build() {
            return new TreeNodeImpl(null, Collections.<Node<E>>emptyList());
        }

        @Override
        public Class<?> forClass() {
            return Tree.class;
        }
    }

    public static final class NodeConverter<E> implements NodeToTreeConverter<E, Node<E>, Tree<E, Node<E>>> {

        @Override
        public Tree<E, Node<E>> treeFromRootNode(Node<E> node) {
            return (TreeNodeImpl<E>)node;
        }

        @Override
        public Class<?> forClass() {
            return TreeNodeImpl.class;
        }
    }

    public static final class Converter<E> implements TreeConverter<E, TreeNodeImpl<E>> {

        @Override
        public TreeNodeImpl<E> build(Tree<E, ? extends Node<E>> sourceTree) {
            final Node<E> root = sourceTree.getRoot();
            return new TreeNodeImpl(root.getElement(), duplicateChildren(root.getChildren()));
        }

        @Override
        public Class<?> forClass() {
            return Tree.class;
        }

        private List<Node<E>> duplicateChildren(Collection<? extends Node<E>> children) {
            @SuppressWarnings("unchecked")
            final Node<E>[] newChildren = new Node[children.size()];
            int i = 0;
            for (final Node<E> sourceChild : children) {
                final List<Node<E>> newGrandChildren = duplicateChildren(sourceChild.getChildren());
                newChildren[i] = new TreeNodeImpl<>(sourceChild.getElement(), newGrandChildren);
                i++;
            }
            return new FixedUncheckedList<>(newChildren);
        }
    }
}
