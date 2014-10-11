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

package com.mattunderscore.trees.common.synchronised;

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableNodeTree;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.tree.Node;

/**
 * @author Matt Champion on 09/10/14.
 */
public final class SynchronisedMutableTree2AsMutableNodeTree<E> implements MutableNodeTree<E, MutableNode<E>> {
    private final MutableTree<E> delegateTree;

    public SynchronisedMutableTree2AsMutableNodeTree(MutableTree<E> delegateTree) {
        this.delegateTree = delegateTree;
    }

    @Override
    public synchronized MutableNode<E> setRoot(E root) {
        return null;
    }

    @Override
    public synchronized MutableNode<E> getRoot() {
        return new NodeToMutableNode<>(this, delegateTree.getRoot());
    }

    @Override
    public synchronized boolean isEmpty() {
        return delegateTree.isEmpty();
    }

    private static final class NodeToMutableNode<E> implements MutableNode<E> {
        private final SynchronisedMutableTree2AsMutableNodeTree<E> tree;
        private final Node<E> delegateNode;

        private NodeToMutableNode(SynchronisedMutableTree2AsMutableNodeTree<E> tree, Node<E> delegateNode) {
            this.tree = tree;
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
        public SimpleCollection<? extends MutableNode<E>> getChildren() {
            return null;
        }

        @Override
        public boolean isLeaf() {
            return delegateNode.isLeaf();
        }

        @Override
        public boolean removeChild(MutableNode<E> child) {
            final NodeToMutableNode<E> wrappedNode = (NodeToMutableNode<E>)child;
            return tree.delegateTree.removeChild(delegateNode, wrappedNode.delegateNode);
        }

        @Override
        public MutableNode<E> addChild(E e) {
            return new NodeToMutableNode<>(tree, tree.delegateTree.addChild(delegateNode, e));
        }
    }
}