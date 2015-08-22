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

package com.mattunderscore.trees.spi;

import com.mattunderscore.trees.transformation.RotationDirection;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Rotator SPI component. Rotates a tree once in any direction.
 * @author Matt Champion on 17/08/2015
 */
public interface Rotator<E, N extends OpenNode<E, N>> extends SPIComponent {
    /**
     * Apply the rotation.
     * @param reference The reference to the root
     * @param root The root of the rotation
     */
    void rotate(RootReference<N> reference, N root);

    /**
     * @return The direction of rotation performed by the rotator
     */
    RotationDirection forDirection();

    /**
     * Reference to the root to allow replacement.
     */
    interface RootReference<O> {
        /**
         * Replace the root with the pivot.
         * @param root The root of the rotation
         * @param pivot The pivot of the rotation
         */
       void replaceRoot(O root, O pivot);
    }

    /**
     * Factory for {@link RootReference} objects.
     * @param <E> The element type
     * @param <O> The node type
     */
    interface RootReferenceFactory<E, O extends OpenNode<E, O>> extends SPIComponent {
        /**
         * Wrap a node as a root reference.
         * @param node The node to wrap
         * @return The reference
         */
        RootReference<O> wrapNode(O node);

        /**
         * Wrap a tree as a root reference.
         * @param tree The tree to wrap
         * @return The reference
         */
        RootReference<O> wrapTree(Tree<E, O> tree);
    }
}
