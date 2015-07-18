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

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.impl.SPISupportImpl;
import com.mattunderscore.trees.impl.TreesImpl;

/**
 * Unit tests for {@link MutableTreeImplNodeConverter}.
 * @author Matt Champion on 04/07/2015.
 */
public final class MutableTreeImplNodeConverterTest {

    @Test
    public void key() {
        final MutableTreeImplNodeConverter<String> converter = new MutableTreeImplNodeConverter<>();
        converter.setSupport(new SPISupportImpl());
        assertEquals(MutableTreeImpl.class, converter.forClass());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void convert() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String, MutableSettableNode<String>> builder = trees.treeBuilders().bottomUpBuilder();
        final MutableTree<String, MutableSettableNode<String>> tree = builder.create("root",
            builder.create("left"),
            builder.create("right"))
            .build(MutableTreeImpl.typeKey());

        final MutableTreeImplNodeConverter<String> converter = new MutableTreeImplNodeConverter<>();
        converter.setSupport(new SPISupportImpl());

        final MutableTree<String, MutableSettableNode<String>> newTree = converter.treeFromRootNode(tree.getRoot());
        assertFalse(newTree.isEmpty());
        assertEquals("root", newTree.getRoot().getElement());
        assertFalse(newTree.getRoot().isLeaf());
    }
}
