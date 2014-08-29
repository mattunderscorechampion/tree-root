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

package com.mattunderscore.trees.common;

import com.mattunderscore.trees.*;
import com.mattunderscore.trees.spi.*;
import net.jcip.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author matt on 07/08/14.
 */
@NotThreadSafe
public final class LinkedTree<E> implements MutableTree<E, LinkedTree<E>>, MutableNode<E> {
    private final E element;
    private final List<LinkedTree<E>> children;

    LinkedTree(E root) {
        this.element = root;
        children = new ArrayList<>();
    }

    private LinkedTree(E root, LinkedTree[] subtrees) {
        this.element = root;
        children = new ArrayList<>();
        for (int i = 0; i < subtrees.length; i++) {
            children.add(subtrees[i]);
        }
    }

    @Override
    public MutableNode<E> getRoot() {
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
    public E getElement() {
        return element;
    }

    @Override
    public Class<E> getElementClass() {
        return (Class<E>)element.getClass();
    }

    @Override
    public Collection<? extends MutableNode<E>> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public MutableNode<E> addChild(E e) {
        if (e == null) {
            throw new NullPointerException("You cannot add a child to an empty tree");
        }
        final LinkedTree<E> child = new LinkedTree<>(e);
        children.add(child);
        return child;
    }

    @Override
    public boolean removeChild(MutableNode<E> child) {
        if (child == null) {
            return false;
        }
        return children.remove(child);
    }

    @Override
    public boolean isLeaf() {
        return children.size() == 0;
    }

    public final static class NodeConverter<E> implements NodeToTreeConverter<E, MutableNode<E>, LinkedTree<E>> {
        @Override
        public LinkedTree<E> treeFromRootNode(MutableNode<E> node) {
            return (LinkedTree)node;
        }

        @Override
        public Class<?> forClass() {
            return LinkedTree.class;
        }
    }

    public final static class Constructor<E> implements TreeConstructor<E, LinkedTree<E>> {

        @Override
        public LinkedTree<E> build(E e, LinkedTree<E>... subtrees) {
            return new LinkedTree<E>(e, subtrees);
        }

        @Override
        public Class<?> forClass() {
            return LinkedTree.class;
        }
    }

    public final static class EmptyConstructor<E> implements EmptyTreeConstructor<E, LinkedTree<E>> {

        @Override
        public LinkedTree<E> build() {
            return new LinkedTree<E>(null);
        }

        @Override
        public Class<?> forClass() {
            return LinkedTree.class;
        }
    }

    public final static class Converter<E> implements TreeConverter<E, LinkedTree<E>> {
        @Override
        public LinkedTree<E> build(Tree<E, ? extends Node<E>> sourceTree) {
            final Node<E> root = sourceTree.getRoot();
            final LinkedTree<E> newTree = new LinkedTree<>(root.getElement());
            for (final Node<E> child : root.getChildren()) {
                duplicate(newTree, child);
            }
            return newTree;
        }

        @Override
        public Class<?> forClass() {
            return LinkedTree.class;
        }

        private void duplicate(LinkedTree<E> newParent, Node<E> sourceChild) {
            final LinkedTree<E> newChild = (LinkedTree<E>) newParent.addChild(sourceChild.getElement());
            for (final Node<E> child : sourceChild.getChildren()) {
                duplicate(newChild, child);
            }
        }
    }
}
