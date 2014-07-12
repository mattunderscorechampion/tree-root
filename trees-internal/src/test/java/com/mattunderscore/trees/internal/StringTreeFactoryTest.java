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

package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.*;
import com.mattunderscore.trees.internal.TreeFactoryImpl;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author matt on 10/06/14.
 */
public final class StringTreeFactoryTest {

    @Test
    public void build0() {
        final TreeFactory factory = new TreeFactoryImpl();
        final Tree<String> tree = factory.create("a",
            factory.create("b",
                factory.create("c")),
            factory.create("d")
        );

        final Node<String> root = tree.getRoot();
        assertEquals(String.class, root.getElementClass());
        assertEquals("a", root.getElement());
        final Collection<Node<String>> children0 = root.getChildren();
        final Iterator<Node<String>> iterator0 = children0.iterator();
        assertEquals(2, children0.size());
        assertEquals("a", root.getElement());
        assertTrue(iterator0.hasNext());
        final Node<String> bNode = iterator0.next();
        assertTrue(iterator0.hasNext());
        final Node<String> dNode = iterator0.next();
        assertFalse(iterator0.hasNext());
        assertEquals("b", bNode.getElement());
        assertEquals("d", dNode.getElement());
        final Collection<Node<String>> children1 = bNode.getChildren();
        final Iterator<Node<String>> iterator1 = children1.iterator();
        assertTrue(iterator1.hasNext());
        final Node<String> cNode = iterator1.next();
        assertFalse(iterator1.hasNext());
        assertEquals("c", cNode.getElement());
    }
}