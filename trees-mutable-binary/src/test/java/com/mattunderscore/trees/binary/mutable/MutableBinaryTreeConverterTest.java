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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.linked.tree.Constructor;
import com.mattunderscore.trees.linked.tree.LinkedTree;

/**
 * Test for {@link NodeConverter}.
 *
 * @author Matt Champion on 04/05/15
 */
public final class MutableBinaryTreeConverterTest {
    @Test
    public void convertFromSelf() {
        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(
            new MutableBinaryTreeNodeImpl<>(
                "a",
                new MutableBinaryTreeNodeImpl<>("b",
                    new MutableBinaryTreeNodeImpl<>("c"),
                    null),
                new MutableBinaryTreeNodeImpl<>("d")));

        final Converter<String> converter = new Converter<>();

        final MutableBinaryTreeImpl<String> convertedTree =
            converter.build(tree);

        final MutableBinaryTreeNode<String> convertedRoot = convertedTree.getRoot();
        assertEquals(2, convertedRoot.getNumberOfChildren());
        final Iterator<? extends MutableBinaryTreeNode<String>> iterator0 = convertedRoot.childIterator();
        assertTrue(iterator0.hasNext());
        final MutableBinaryTreeNode<String> child0 = iterator0.next();
        assertEquals("b", child0.getElement());
        final MutableBinaryTreeNode<String> child1 = iterator0.next();
        assertEquals("d", child1.getElement());

        final Iterator<? extends MutableBinaryTreeNode<String>> iterator1 = child0.childIterator();
        assertTrue(iterator1.hasNext());
        final MutableBinaryTreeNode<String> child2 = iterator1.next();
        assertEquals("c", child2.getElement());
        assertFalse(iterator1.hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void cannotConvertTreeWithThreeChildren() {
        final Constructor<String> linkedTreeConstructor = new Constructor<>();
        final LinkedTree<String> tree = linkedTreeConstructor.build("a",
            linkedTreeConstructor.build("b"),
            linkedTreeConstructor.build("c"),
            linkedTreeConstructor.build("d"));

        final Converter<String> converter = new Converter<>();

        converter.build(tree);
    }

    @Test
    public void forClass() {
        final Converter<String> converter = new Converter<>();
        assertEquals(MutableBinaryTreeImpl.class, converter.forClass());
    }
}
