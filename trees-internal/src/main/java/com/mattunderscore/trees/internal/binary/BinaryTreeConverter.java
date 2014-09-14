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

package com.mattunderscore.trees.internal.binary;

import com.mattunderscore.trees.binary.BinaryTree;
import com.mattunderscore.trees.binary.BinaryTreeNode;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.spi.TreeConverter;

import java.util.Iterator;

/**
 * @author Matt Champion on 06/09/14.
 */
public class BinaryTreeConverter<E> implements TreeConverter<E, BinaryTree<E, BinaryTreeNode<E>>> {
    @Override
    public BinaryTree<E, BinaryTreeNode<E>> build(Tree<E, ? extends Node<E>> sourceTree) {
        final Node<E> root = sourceTree.getRoot();
        return new BinaryTreeWrapper<E, BinaryTreeNode<E>>(duplicate(root));
    }

    @Override
    public Class<?> forClass() {
        return BinaryTree.class;
    }

    private BinaryTreeNodeImpl<E> duplicate(Node<E> sourceChild) {
        final Iterator<? extends Node<E>> children = sourceChild.getChildren().iterator();
        if (children.hasNext()) {
            final Node<E> left = children.next();
            Node<E> right = null;
            if (children.hasNext()) {
                right = children.next();
            }
            if (children.hasNext()) {
                throw new IllegalStateException("A binary tree can only have two children");
            }
            final BinaryTreeNodeImpl<E> newLeft = duplicate(left);
            BinaryTreeNodeImpl<E> newRight = null;
            if (right != null) {
                newRight = duplicate(right);
            }
            return new BinaryTreeNodeImpl<>(sourceChild.getElement(), newLeft, newRight);
        }
        else {
            return new BinaryTreeNodeImpl<>(sourceChild.getElement());
        }
    }
}
