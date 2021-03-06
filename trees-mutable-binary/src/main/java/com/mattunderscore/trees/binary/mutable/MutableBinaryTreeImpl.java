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

import com.mattunderscore.trees.binary.MutableBinaryTree;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.construction.TypeKey;
import net.jcip.annotations.NotThreadSafe;

/**
 * Mutable binary tree implementation.
 * @author Matt Champion on 08/09/14.
 */
@NotThreadSafe
public final class MutableBinaryTreeImpl<E> implements MutableBinaryTree<E, MutableBinaryTreeNode<E>> {
    private MutableBinaryTreeNodeImpl<E> root;

    /*package*/ MutableBinaryTreeImpl() {
        root = null;
    }

    /*package*/ MutableBinaryTreeImpl(MutableBinaryTreeNodeImpl<E> root) {
        this.root = root;
    }

    @Override
    public MutableBinaryTreeNode<E> getRoot() {
        return root;
    }

    @Override
    public MutableBinaryTreeNode<E> setRoot(E root) {
        final MutableBinaryTreeNodeImpl<E> newRoot = new MutableBinaryTreeNodeImpl<>(root);
        setRootInternal(newRoot);
        return newRoot;
    }

    /**
     * Allow mutation of root.
     * @param newRoot The new root
     */
    /*package*/ void setRootInternal(MutableBinaryTreeNodeImpl<E> newRoot) {
        root = newRoot;
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
