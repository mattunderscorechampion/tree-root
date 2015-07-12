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

package com.mattunderscore.trees.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.mattunderscore.trees.mutable.OpenSettableNode;
import com.mattunderscore.trees.mutable.SettableNode;

/**
 * Unit tests for AbstractSettableNode.
 * @author Matt Champion on 20/12/14
 */
public final class AbstractSettableNodeTest {
    private OpenSettableNode<String, SettableNode<String>> node;

    @Before
    public void setUp() {
        node = new AbstractSettableNode<String, SettableNode<String>>("a") {
            @Override
            public int getNumberOfChildren() {
                return 0;
            }

            @Override
            public Iterator<? extends SettableNode<String>> childIterator() {
                return Collections.emptyIterator();
            }
        };
    }

    @Test
    public void getElement() {
        assertEquals("a", node.getElement());
    }

    @Test
    public void getElementClass() {
        assertEquals(String.class, node.getElementClass());
    }

    @Test
    public void isLeaf() {
        assertTrue(node.isLeaf());
    }

    @Test
    public void setElement() {
        assertEquals("a", node.setElement("b"));
        assertEquals("b", node.getElement());
    }
}
