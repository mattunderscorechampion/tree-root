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
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation of {@link com.mattunderscore.trees.spi.TreeConstructor} for
 * {@link MutableBinaryTreeImpl}.
 * @author Matt Champion on 18/08/2015
 */
public final class Constructor<E> implements TreeConstructor<E, MutableBinaryTreeNode<E>, MutableBinaryTreeImpl<E>> {

    @Override
    public MutableBinaryTreeImpl<E> build(E e, MutableBinaryTreeImpl<E>[] subtrees) {
        if (subtrees.length > 2) {
            throw new IllegalStateException("A binary tree cannot have more than two children");
        }

        MutableBinaryTreeNodeImpl<E> left = null;
        MutableBinaryTreeNodeImpl<E> right = null;

        if (subtrees.length > 0) {
            left = (MutableBinaryTreeNodeImpl<E>) subtrees[0].getRoot();
        }
        if (subtrees.length > 1) {
            right = (MutableBinaryTreeNodeImpl<E>) subtrees[1].getRoot();
        }

        final MutableBinaryTreeNodeImpl<E> root = new MutableBinaryTreeNodeImpl<>(e, left, right);
        return new MutableBinaryTreeImpl<>(root);
    }

    @Override
    public Class<? extends Tree> forClass() {
        return MutableBinaryTreeImpl.class;
    }
}
