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

package com.mattunderscore.trees.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mattunderscore.trees.construction.TypeKey;

/**
 * @author Matt Champion on 04/05/15
 */
public final class MutableTreeImplTest {
    @Test
    public void emptyTree() {
        final MutableTree<String, MutableSettableNode<String>> emptyTree = new MutableTreeImpl<>(null);

        assertTrue(emptyTree.isEmpty());
        assertNull(emptyTree.getRoot());
    }

    @Test
    public void setRoot() {
        final MutableTree<String, MutableSettableNode<String>> tree = new MutableTreeImpl<>(null);

        tree.setRoot("a");
        assertFalse(tree.isEmpty());
    }

    @Test
    public void removeNothing() {
        final MutableTree<String, MutableSettableNode<String>> tree = new MutableTreeImpl<>(null);
        tree.setRoot("a");

        assertFalse(tree.getRoot().removeChild(null));
    }

    @Test
    public void removeFromWrongTree() {
        final MutableTree<String, MutableSettableNode<String>> tree0 = new MutableTreeImpl<>(null);
        tree0.setRoot("a").addChild("b");
        final MutableTree<String, MutableSettableNode<String>> tree1 = new MutableTreeImpl<>(null);
        tree1.setRoot("c");

        assertFalse(tree0.getRoot().removeChild(tree1.getRoot()));
    }

    @Test
    public void typeKey() {
        final TypeKey<MutableTreeImpl<String>> key = MutableTreeImpl.typeKey();
        assertEquals(MutableTreeImpl.class, key.getTreeType());
    }
}
