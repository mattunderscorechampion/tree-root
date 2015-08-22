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
import com.mattunderscore.trees.spi.Rotator;

/**
 * Implementation of {@link Rotator} for {@link MutableBinaryTreeNodeImpl} in the left direction. Rotation is performed
 * in place by modifying the existing tree.
 * @author Matt Champion on 17/08/2015
 */
public final class InPlaceLeftRotator<E> implements Rotator<E, MutableBinaryTreeNode<E>> {
    @Override
    public void rotate(RootReference<MutableBinaryTreeNode<E>> reference, MutableBinaryTreeNode<E> currentRoot) {
        final MutableBinaryTreeNodeImpl<E> root = (MutableBinaryTreeNodeImpl<E>)currentRoot;
        final MutableBinaryTreeNodeImpl<E> pivot = (MutableBinaryTreeNodeImpl<E>)root.getRight();

        if (pivot == null) {
            throw new IllegalStateException("No pivot node");
        }
        else {
            final MutableBinaryTreeNodeImpl<E> rs = (MutableBinaryTreeNodeImpl<E>)pivot.getLeft();

            if (rs == null) {
                pivot.setInternalLeft(root);
                reference.replaceRoot(root, pivot);
            }
            else {
                root.setInternalRight(rs);
                pivot.setInternalLeft(root);
                reference.replaceRoot(root, pivot);
            }
        }
    }

    @Override
    public Direction forDirection() {
        return Direction.LEFT;
    }

    @Override
    public Class<?> forClass() {
        return MutableBinaryTreeNodeImpl.class;
    }
}
