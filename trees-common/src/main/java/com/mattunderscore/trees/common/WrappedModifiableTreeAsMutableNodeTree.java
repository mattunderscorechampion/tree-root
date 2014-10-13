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

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.mutable.ModifiableTree;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableNodeTree;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.utilities.collections.ConvertingSimpleCollection;
import com.mattunderscore.trees.utilities.iterators.ConvertingIterator;

import java.util.Iterator;

/**
 * Wrapped mutable tree as a mutable node tree.
 * @author Matt Champion on 11/10/14.
 */
public final class WrappedModifiableTreeAsMutableNodeTree<E> implements MutableNodeTree<E, MutableNode<E>> {
    private final ModifiableTree<E> delegateTree;

    public WrappedModifiableTreeAsMutableNodeTree(ModifiableTree<E> delegateTree) {
        this.delegateTree = delegateTree;
    }

    @Override
    public MutableNode<E> setRoot(E root) {
        return new NodeToMutableNode(delegateTree.setRoot(root));
    }

    @Override
    public MutableNode<E> getRoot() {
        return new NodeToMutableNode(delegateTree.getRoot());
    }

    @Override
    public boolean isEmpty() {
        return delegateTree.isEmpty();
    }

    private final class NodeToMutableNode implements MutableNode<E> {
        private final Node<E> delegateNode;

        private NodeToMutableNode(Node<E> delegateNode) {
            this.delegateNode = delegateNode;
        }

        @Override
        public E getElement() {
            return delegateNode.getElement();
        }

        @Override
        public Class<E> getElementClass() {
            return delegateNode.getElementClass();
        }

        @Override
        public SimpleCollection<MutableNode<E>> getChildren() {
            return new Collection((SimpleCollection<Node<E>>)delegateNode.getChildren());
        }

        @Override
        public boolean isLeaf() {
            return delegateNode.isLeaf();
        }

        @Override
        public boolean removeChild(MutableNode<E> child) {
            final NodeToMutableNode wrappedNode = (NodeToMutableNode) child;
            return delegateTree.removeChild(delegateNode, wrappedNode.delegateNode);
        }

        @Override
        public MutableNode<E> addChild(E e) {
            return new NodeToMutableNode(delegateTree.addChild(delegateNode, e));
        }
    }

    private final class Collection extends ConvertingSimpleCollection<MutableNode<E>, Node<E>> {
        protected Collection(SimpleCollection<Node<E>> collection) {
            super(collection);
        }

        @Override
        protected Iterator<MutableNode<E>> convert(Iterator<Node<E>> delegateIterator) {
            return new ConvertingIteratorImpl(delegateIterator);
        }
    }

    private final class ConvertingIteratorImpl extends ConvertingIterator<MutableNode<E>, Node<E>> {

        protected ConvertingIteratorImpl(Iterator<Node<E>> delegate) {
            super(delegate);
        }

        @Override
        protected MutableNode<E> convert(Node<E> node) {
            return new NodeToMutableNode(node);
        }
    }
}
