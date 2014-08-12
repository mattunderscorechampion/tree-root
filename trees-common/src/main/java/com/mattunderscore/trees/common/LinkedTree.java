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
import com.mattunderscore.trees.spi.INodeToTreeConverter;
import com.mattunderscore.trees.spi.ITreeConstructor;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeToNodeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author matt on 07/08/14.
 */
public final class LinkedTree<E> implements IMutableTree<E, LinkedTree<E>>, IMutableNode<E> {
    private final E element;
    private final List<LinkedTree<E>> children;

    public LinkedTree(E root) {
        this.element = root;
        children = new ArrayList<>();
    }

    @Override
    public IMutableNode<E> getRoot() {
        return this;
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
    public Collection<? extends IMutableNode<E>> getChildren() {
        return children;
    }

    @Override
    public IMutableNode<E> addChild(E e) {
        final LinkedTree<E> child = new LinkedTree<>(e);
        children.add(child);
        return child;
    }

    public final static class Converter<E> implements INodeToTreeConverter<E, IMutableNode<E>, LinkedTree<E>> {
        @Override
        public LinkedTree<E> treeFromRootNode(IMutableNode<E> node) {
            return (LinkedTree)node;
        }

        @Override
        public Class<?> forClass() {
            return LinkedTree.class;
        }
    }

    public final static class Constructor<E> implements ITreeConstructor<E, LinkedTree<E>> {
        @Override
        public LinkedTree<E> build(Class<LinkedTree<E>> klass) {
            return null;
        }

        @Override
        public Class<?> forClass() {
            return LinkedTree.class;
        }
    }
}
