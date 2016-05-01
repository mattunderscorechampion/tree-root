/* Copyright © 2016 Matthew Champion
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

package com.mattunderscore.tree.root.querying;

import com.mattunderscore.trees.binary.BinaryTreeNode;
import com.mattunderscore.trees.tree.Tree;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link BackPath}.
 * @author Matt Champion on 30/04/16
 */
public final class BackPathTest {
    @Mock
    private BinaryTreeNode<String> parent;

    @Mock
    private BinaryTreeNode<String> node;

    @Test
    public void testGetNullParent() throws Exception {
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(null, node);

        assertNull(backPath.getParent());
    }

    @Test
    public void testGetParent() throws Exception {
        BackPath<String, BinaryTreeNode<String>> parentBackPath = new BackPath<>(null, parent);
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(parentBackPath, node);

        assertEquals(parentBackPath, backPath.getParent());
    }

    @Test
    public void testGetNode() throws Exception {
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(null, node);

        assertEquals(node, backPath.getNode());
    }

    @Test
    public void testToTrivialPath() throws Exception {
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(null, node);

        final List<BinaryTreeNode<String>> path = backPath.toPath();
        assertEquals(1, path.size());
        assertEquals(node, path.get(0));
    }

    @Test
    public void testToSimplePath() throws Exception {
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(new BackPath<>(null, parent), node);

        final List<BinaryTreeNode<String>> path = backPath.toPath();
        assertEquals(2, path.size());
        assertEquals(parent, path.get(0));
        assertEquals(node, path.get(1));
    }
}
