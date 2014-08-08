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

import com.mattunderscore.trees.MutableNode;
import com.mattunderscore.trees.MutableTree;
import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeToNodeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author matt on 07/08/14.
 */
public final class LinkedTree<E> implements MutableTree<E> {
    private final MutableNode<E> root;

    public LinkedTree(E root) {
        this.root = new LinkedTreeNode(root);
    }

    @Override
    public MutableNode<E> getRoot() {
        return root;
    }

    private final class LinkedTreeNode implements MutableNode<E> {
        private final E element;
        private final List<LinkedTreeNode> children;

        public LinkedTreeNode(E element) {
            this.element = element;
            children = new ArrayList<>();
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
        public Collection<Node<E>> getChildren() {
            final List<Node<E>> newChildren = new ArrayList<>();
            for (Node<E> child : children) {
                newChildren.add(child);
            }
            return newChildren;
        }

        @Override
        public MutableNode<E> addChild(E e) {
            final LinkedTreeNode node = new LinkedTreeNode(e);
            children.add(node);
            return node;
        }

        public LinkedTree<E> getTree() {
            return LinkedTree.this;
        }
    }

    final static class Converter implements TreeToNodeConverter {

        @Override
        public Tree treeFromRootNode(Node node) {
            final LinkedTree.LinkedTreeNode linkedNode = (LinkedTree.LinkedTreeNode)node;
            return linkedNode.getTree();
        }

        @Override
        public Class<? extends Node> forClass() {
            return LinkedTree.LinkedTreeNode.class;
        }
    }

    final static class Constructor<E, T extends Tree<E>> implements TreeConstructor<E, T> {

        @Override
        public T build(Tree<E> tree) {
            return (T)tree;
        }

        @Override
        public Class<?> forClass() {
            return LinkedTree.class;
        }
    }
}
