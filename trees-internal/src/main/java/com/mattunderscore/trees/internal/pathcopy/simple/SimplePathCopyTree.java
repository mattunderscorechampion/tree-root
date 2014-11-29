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
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import net.jcip.annotations.GuardedBy;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Tree based on path copy mutation. Mutation on old
 * @author Matt Champion on 11/09/14.
 */
public final class SimplePathCopyTree<E> implements MutableTree<E, MutableNode<E>> {
    @GuardedBy("this")
    SimplePathCopyTreeNode<E> root;

    public SimplePathCopyTree() {
        root = null;
    }

    @Override
    public MutableNode<E> setRoot(E root) {
        final SimplePathCopyTreeNode<E> newRoot = new SimplePathCopyTreeNode<>(this, root);
        synchronized (this) {
            this.root = newRoot;
        }
        return newRoot;
    }

    @Override
    public SimplePathCopyTreeNode<E> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    public static final class EmptyConstructor<E> implements EmptyTreeConstructor<E, SimplePathCopyTree<E>> {

        @Override
        public SimplePathCopyTree<E> build() {
            return new SimplePathCopyTree<>();
        }

        @Override
        public Class<? extends Tree> forClass() {
            return SimplePathCopyTree.class;
        }
    }

    public static final class NodeConverter<E> implements NodeToTreeConverter<E, MutableNode<E>, SimplePathCopyTree<E>, Node<E>> {

        @Override
        public SimplePathCopyTree<E> treeFromRootNode(Node<E> node) {
            final SimplePathCopyTree<E> newTree = new SimplePathCopyTree<>();
            copyChildren(newTree.setRoot(node.getElement()), node.getChildren());
            return newTree;
        }

        private void copyChildren(MutableNode<E> newParent, SimpleCollection<? extends Node<E>> children) {
            for (final Node<E> child : children) {
                final MutableNode<E> newChild = newParent.addChild(child.getElement());
                copyChildren(newChild, child.getChildren());
            }
        }

        @Override
        public Class<? extends Node> forClass() {
            return SimplePathCopyTreeNode.class;
        }
    }

    public static final class Converter<E> implements TreeConverter<E, SimplePathCopyTree<E>> {
        private final NodeConverter<E> converter = new NodeConverter();

        @Override
        public SimplePathCopyTree<E> build(Tree<E, ? extends Node<E>> sourceTree) {
            final Node<E> root = sourceTree.getRoot();
            return converter.treeFromRootNode(root);
        }

        @Override
        public Class<? extends Tree> forClass() {
            return SimplePathCopyTree.class;
        }
    }

    public static final class Constructor<E> implements TreeConstructor<E, SimplePathCopyTree<E>> {

        @Override
        public SimplePathCopyTree<E> build(E e, SimplePathCopyTree<E>[] subtrees) {
            final SimplePathCopyTree<E> tree = new SimplePathCopyTree<>();
            tree.setRoot(e);
            for (SimplePathCopyTree<E> subtree : subtrees) {
                final Node<E> subRoot = subtree.getRoot();
                final MutableNode<E> newSubRoot = tree.getRoot().addChild(subRoot.getElement());
                copyChildren(newSubRoot, subRoot.getChildren());
            }
            return tree;
        }

        @Override
        public Class<? extends Tree> forClass() {
            return SimplePathCopyTree.class;
        }

        private void copyChildren(MutableNode<E> newParent, SimpleCollection<? extends Node<E>> children) {
            for (final Node<E> child : children) {
                final MutableNode<E> newChild = newParent.addChild(child.getElement());
                copyChildren(newChild, child.getChildren());
            }
        }
    }
}
