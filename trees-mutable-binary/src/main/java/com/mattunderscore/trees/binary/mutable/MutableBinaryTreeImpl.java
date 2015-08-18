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

package com.mattunderscore.trees.binary.mutable;

import java.util.Iterator;

import net.jcip.annotations.NotThreadSafe;

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.TreeBuilderFactoryAware;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.spi.impl.AbstractNodeToRelatedTreeConverter;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Mutable binary tree implementation.
 * @author Matt Champion on 08/09/14.
 */
@NotThreadSafe
public final class MutableBinaryTreeImpl<E> implements Tree<E, MutableBinaryTreeNode<E>> {
    private MutableBinaryTreeNodeImpl<E> root;

    private MutableBinaryTreeImpl() {
        root = null;
    }

    private MutableBinaryTreeImpl(MutableBinaryTreeNodeImpl<E> root) {
        this.root = root;
    }

    @Override
    public MutableBinaryTreeNode<E> getRoot() {
        return root;
    }

    /**
     * Allow mutation of root.
     * @param newRoot The new root
     */
    /*package*/ void setRoot(MutableBinaryTreeNodeImpl<E> newRoot) {
        root = newRoot;
    }

    public static final class NodeConverter<E> extends AbstractNodeToRelatedTreeConverter<E, MutableBinaryTreeNode<E>, MutableBinaryTreeImpl<E>> implements TreeBuilderFactoryAware {
        private volatile TreeBuilderFactory treeBuilderFactory;

        public NodeConverter() {
            super(MutableBinaryTreeNodeImpl.class, MutableBinaryTreeImpl.class);
        }

        @Override
        protected TopDownTreeRootBuilder<E, MutableBinaryTreeNode<E>> getBuilder() {
            return treeBuilderFactory.topDownBuilder();
        }

        @Override
        public void setTreeBuilderFactory(TreeBuilderFactory factory) {
            treeBuilderFactory = factory;
        }
    }

    public static final class EmptyConstructor<E> implements EmptyTreeConstructor<E, MutableBinaryTreeNode<E>, MutableBinaryTreeImpl<E>> {
        @Override
        public MutableBinaryTreeImpl<E> build() {
            return new MutableBinaryTreeImpl<>();
        }

        @Override
        public Class<? extends Tree> forClass() {
            return MutableBinaryTreeImpl.class;
        }
    }

    public static final class Constructor<E> implements TreeConstructor<E, MutableBinaryTreeNode<E>, MutableBinaryTreeImpl<E>> {

        @Override
        public MutableBinaryTreeImpl<E> build(E e, MutableBinaryTreeImpl<E>[] subtrees) {
            if (subtrees.length > 2) {
                throw new IllegalStateException("A binary tree cannot have more than two children");
            }

            MutableBinaryTreeNodeImpl<E> left = null;
            MutableBinaryTreeNodeImpl<E> right = null;

            if (subtrees.length > 0) {
                left = (MutableBinaryTreeNodeImpl<E>)subtrees[0].getRoot();
            }
            if (subtrees.length > 1) {
                right = (MutableBinaryTreeNodeImpl<E>)subtrees[1].getRoot();
            }

            final MutableBinaryTreeNodeImpl<E> root = new MutableBinaryTreeNodeImpl<>(e, left, right);
            return new MutableBinaryTreeImpl<>(root);
        }

        @Override
        public Class<? extends Tree> forClass() {
            return MutableBinaryTreeImpl.class;
        }
    }

    public static final class Converter<E> implements TreeConverter<E, MutableBinaryTreeNode<E>, MutableBinaryTreeImpl<E>> {
        @Override
        public <S extends OpenNode<E, S>> MutableBinaryTreeImpl<E> build(Tree<E, S> sourceTree) {
            final S root = sourceTree.getRoot();
            return new MutableBinaryTreeImpl<>(duplicate(root));
        }

        @Override
        public Class<? extends Tree> forClass() {
            return MutableBinaryTreeImpl.class;
        }

        private <S extends OpenNode<E, S>> MutableBinaryTreeNodeImpl<E> duplicate(S sourceChild) {
            final Iterator<? extends S> children = sourceChild.childIterator();
            if (children.hasNext()) {
                final S left = children.next();
                S right = null;
                if (children.hasNext()) {
                    right = children.next();
                }
                if (children.hasNext()) {
                    throw new IllegalStateException("A binary tree can only have two children");
                }
                final MutableBinaryTreeNodeImpl<E> newLeft = duplicate(left);
                MutableBinaryTreeNodeImpl<E> newRight = null;
                if (right != null) {
                    newRight = duplicate(right);
                }
                return new MutableBinaryTreeNodeImpl<>(sourceChild.getElement(), newLeft, newRight);
            }
            else {
                return new MutableBinaryTreeNodeImpl<>(sourceChild.getElement());
            }
        }
    }

    /**
     * Construct a TypeKey for a specific element type.
     * @param <E> The element type
     * @return The type key
     */
    public static <E> TypeKey<MutableBinaryTreeImpl<E>> typeKey() {
        return new TypeKey<MutableBinaryTreeImpl<E>>() {};
    }
}
