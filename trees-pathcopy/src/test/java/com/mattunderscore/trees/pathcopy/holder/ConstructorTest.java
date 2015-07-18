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

package com.mattunderscore.trees.pathcopy.holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import com.mattunderscore.trees.mutable.MutableNode;

/**
 * Test for {@link Constructor}.
 * @author Matt Champion on 07/07/2015.
 */
public final class ConstructorTest {

    @Test
    public void key() {
        final Constructor<String> constructor = new Constructor<>();
        assertEquals(PathCopyTree.class, constructor.forClass());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void build0() {
        final Constructor<String> constructor = new Constructor<>();
        final PathCopyTree<String> tree = constructor.build("root", new PathCopyTree[] {});
        assertEquals("root", tree.getRoot().getElement());
        assertTrue(tree.getRoot().isLeaf());
        assertFalse(tree.isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void build1() {
        final Constructor<String> constructor = new Constructor<>();
        final PathCopyTree<String> tree = constructor.build("root", new PathCopyTree[] {
            constructor.build("left", new PathCopyTree[] {})});
        assertEquals("root", tree.getRoot().getElement());
        assertFalse(tree.getRoot().isLeaf());
        assertFalse(tree.isEmpty());
        final Iterator<MutableNode<String>> iterator = tree.getRoot().childIterator();
        assertEquals("left", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void build2() {
        final Constructor<String> constructor = new Constructor<>();
        final PathCopyTree<String> tree = constructor.build("root", new PathCopyTree[] {
            constructor.build("left", new PathCopyTree[] {
                constructor.build("left-left", new PathCopyTree[] {})
            })});
        assertEquals("root", tree.getRoot().getElement());
        assertFalse(tree.getRoot().isLeaf());
        assertFalse(tree.isEmpty());
        final Iterator<MutableNode<String>> iterator = tree.getRoot().childIterator();
        final MutableNode<String> child = iterator.next();
        assertEquals("left", child.getElement());
        assertFalse(child.isLeaf());
        assertFalse(iterator.hasNext());
        final Iterator<? extends MutableNode<String>> deeperIterator = child.childIterator();
        final MutableNode<String> leaf = deeperIterator.next();
        assertEquals("left-left", leaf.getElement());
        assertTrue(leaf.isLeaf());
        assertFalse(deeperIterator.hasNext());
    }
}
