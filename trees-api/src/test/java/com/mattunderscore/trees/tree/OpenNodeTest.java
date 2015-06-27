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

package com.mattunderscore.trees.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

/**
 * Unit tests for default methods of {@link OpenNode}.
 * @author Matt Champion on 27/06/2015
 */
public final class OpenNodeTest {
    @Test
    public void isLeaf() {
        final LeafTestNode node = new LeafTestNode();
        assertTrue(node.isLeaf());
    }

    @Test
    public void isNotLeaf() {
        final TestNode node = new TestNode();
        assertFalse(node.isLeaf());
    }

    @Test
    public void elementClass() {
        final TestNode node = new TestNode();
        assertEquals(String.class, node.getElementClass());
    }

    private static final class LeafTestNode implements OpenNode<String, LeafTestNode> {

        @Override
        public String getElement() {
            return "node";
        }

        @Override
        public int getNumberOfChildren() {
            return 0;
        }

        @Override
        public Iterator<? extends LeafTestNode> childIterator() {
            return null;
        }
    }

    private static final class TestNode implements OpenNode<String, TestNode> {

        @Override
        public String getElement() {
            return "node";
        }

        @Override
        public int getNumberOfChildren() {
            return 1;
        }

        @Override
        public Iterator<? extends TestNode> childIterator() {
            return null;
        }
    }
}
