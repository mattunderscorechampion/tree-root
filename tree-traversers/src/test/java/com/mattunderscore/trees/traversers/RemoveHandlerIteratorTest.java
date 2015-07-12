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

package com.mattunderscore.trees.traversers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.spi.IteratorRemoveHandler;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link RemoveHandlerIterator}.
 * @author Matt Champion on 07/07/2015.
 */
public final class RemoveHandlerIteratorTest {
    @Mock
    private Tree<String, Node<String>> tree;
    @Mock
    private Node<String> node;
    @Mock
    private IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void supported0() {
        when(handler.isSupported()).thenReturn(true);
        final TestIterator iterator = new TestIterator(tree, handler);

        assertTrue(iterator.isRemoveSupported());
    }

    @Test
    public void supported1() {
        when(handler.isSupported()).thenReturn(false);
        final TestIterator iterator = new TestIterator(tree, handler);

        assertFalse(iterator.isRemoveSupported());
    }

    @Test
    public void remove() {
        when(handler.isSupported()).thenReturn(true);
        final TestIterator iterator = new TestIterator(tree, handler);

        iterator.remove(node);
        verify(handler).remove(tree, node);
    }

    private static final class TestIterator extends RemoveHandlerIterator<String, Node<String>, Tree<String, Node<String>>> {

        public TestIterator(Tree<String, Node<String>> tree, IteratorRemoveHandler<String, Node<String>, Tree<String, Node<String>>> handler) {
            super(tree, handler);
        }

        @Override
        protected Node<String> calculateNext() throws NoSuchElementException {
            return null;
        }
    }
}
