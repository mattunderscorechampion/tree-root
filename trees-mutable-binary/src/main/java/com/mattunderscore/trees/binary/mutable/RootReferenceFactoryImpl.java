/* Copyright © 2015 Matthew Champion
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

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.spi.Rotator.RootReference;
import com.mattunderscore.trees.spi.RootReferenceFactory;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation of {@link RootReferenceFactory} for {@link MutableBinaryTreeNodeImpl}.
 * @author Matt Champion on 20/08/2015
 */
public final class RootReferenceFactoryImpl<E> implements RootReferenceFactory<E, MutableBinaryTreeNode<E>> {
    @Override
    public RootReference<MutableBinaryTreeNode<E>> wrapNode(MutableBinaryTreeNode<E> node) {
        return (oldRoot, newRoot) -> {
            if (node.getLeft() == oldRoot) {
                final MutableBinaryTreeNodeImpl<E> concreteNode = (MutableBinaryTreeNodeImpl<E>)node;
                concreteNode.setInternalLeft((MutableBinaryTreeNodeImpl<E>)newRoot);
            }
            else if (node.getRight() == oldRoot) {
                final MutableBinaryTreeNodeImpl<E> concreteNode = (MutableBinaryTreeNodeImpl<E>)node;
                concreteNode.setInternalRight((MutableBinaryTreeNodeImpl<E>) newRoot);
            }
            else {
                throw new IllegalStateException("The root node is not child of parent");
            }
        };
    }

    @Override
    public RootReference<MutableBinaryTreeNode<E>> wrapTree(Tree<E, MutableBinaryTreeNode<E>> tree) {
        return (oldRoot, newRoot) -> {
            final MutableBinaryTreeImpl<E> concreteTree = (MutableBinaryTreeImpl<E>)tree;
            concreteTree.setRootInternal((MutableBinaryTreeNodeImpl<E>)newRoot);
        };
    }

    @Override
    public Class<?> forClass() {
        return MutableBinaryTreeNodeImpl.class;
    }
}
