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

package com.mattunderscore.trees.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.binary.MutableBinaryTree;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.binary.mutable.MutableBinaryTreeImpl;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.transformation.RotationDirection;
import com.mattunderscore.trees.transformation.TreeTransformer;

/**
 * Tests for {@link TreeTransformer}.
 * @author Matt Champion on 22/08/2015
 */
public final class TreeTransformerImplTest {
    @Test
    public void leftRotate() {
        final TreeTransformer transformer = Trees.get().transformations();
        final BottomUpTreeBuilder<String, MutableBinaryTreeNode<String>> builder = Trees
            .get()
            .treeBuilders()
            .bottomUpBuilder();
        final MutableBinaryTree<String, MutableBinaryTreeNode<String>> tree = builder.create(
            "p",
            builder.create("a"),
            builder.create(
                "q",
                builder.create("b"),
                builder.create("c")))
            .build(MutableBinaryTreeImpl.typeKey());

        transformer.rotateRootInPlace(tree, RotationDirection.LEFT);

        assertEquals("q", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("b", tree.getRoot().getLeft().getRight().getElement());
    }
}
