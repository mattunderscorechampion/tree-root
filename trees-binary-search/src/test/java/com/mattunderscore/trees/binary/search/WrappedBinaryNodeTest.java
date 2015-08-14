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

package com.mattunderscore.trees.binary.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.binary.BinaryTreeNode;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.iterators.ArrayIterator;

/**
 * Unit tests for {@link WrappedBinaryNode}.
 * @author Matt Champion on 05/07/2015
 */
public final class WrappedBinaryNodeTest {

    @Mock
    private MutableBinaryTreeNode<String> parent;
    @Mock
    private MutableBinaryTreeNode<String> left;
    @Mock
    private MutableBinaryTreeNode<String> right;

    private Iterator<? extends MutableBinaryTreeNode<String>> iterator;

    @Before
    public void setUp() {
        initMocks(this);
        when(parent.getElement()).thenReturn("root");
        when(left.getElement()).thenReturn("left");
        when(right.getElement()).thenReturn("right");
        when(parent.getElementClass()).thenAnswer(invocationOnMock -> String.class);
        when(left.getElementClass()).thenAnswer(invocationOnMock -> String.class);
        when(right.getElementClass()).thenAnswer(invocationOnMock -> String.class);
        when(parent.getNumberOfChildren()).thenReturn(2);
        when(left.getNumberOfChildren()).thenReturn(0);
        when(right.getNumberOfChildren()).thenReturn(0);
        when(parent.isLeaf()).thenReturn(false);
        when(left.isLeaf()).thenReturn(true);
        when(right.isLeaf()).thenReturn(true);
        when(parent.getChild(0)).thenReturn(left);
        when(parent.getChild(1)).thenReturn(right);

        when(parent.getLeft()).thenReturn(left);
        when(parent.getRight()).thenReturn(right);

        iterator = ArrayIterator.create(new MutableBinaryTreeNode[] {left, right});

        when(parent.childIterator()).thenAnswer(invocationOnMock -> iterator);
        when(parent.childStructuralIterator()).thenAnswer(invocationOnMock -> iterator);
    }

    @Test
    public void element() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        assertEquals("root", wrappedParent.getElement());
    }

    @Test
    public void elementClass() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        assertEquals(String.class, wrappedParent.getElementClass());
    }

    @Test
    public void numberOfChildren() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        assertEquals(2, wrappedParent.getNumberOfChildren());
    }

    @Test
    public void isLeaf() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        assertFalse(wrappedParent.isLeaf());
    }

    @Test
    public void left() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        final BinaryTreeNode<String> wrappedLeft = wrappedParent.getLeft();
        assertEquals("left", wrappedLeft.getElement());
    }

    @Test
    public void right() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        final BinaryTreeNode<String> wrappedRight = wrappedParent.getRight();
        assertEquals("right", wrappedRight.getElement());
    }

    @Test
    public void child() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        final BinaryTreeNode<String> wrappedLeft = wrappedParent.getChild(0);
        final BinaryTreeNode<String> wrappedRight = wrappedParent.getChild(1);
        assertEquals("left", wrappedLeft.getElement());
        assertEquals("right", wrappedRight.getElement());
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        final Iterator<? extends BinaryTreeNode<String>> wrappedIterator = wrappedParent.childIterator();
        assertTrue(wrappedIterator.hasNext());
        final BinaryTreeNode<String> wrappedLeft = wrappedIterator.next();
        assertTrue(wrappedIterator.hasNext());
        final BinaryTreeNode<String> wrappedRight = wrappedIterator.next();
        assertFalse(wrappedIterator.hasNext());
        assertEquals("left", wrappedLeft.getElement());
        assertEquals("right", wrappedRight.getElement());

        wrappedIterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void structuralIterator() {
        final WrappedBinaryNode<String> wrappedParent = new WrappedBinaryNode<>(parent);
        final Iterator<? extends BinaryTreeNode<String>> wrappedIterator = wrappedParent.childStructuralIterator();
        assertTrue(wrappedIterator.hasNext());
        final BinaryTreeNode<String> wrappedLeft = wrappedIterator.next();
        assertTrue(wrappedIterator.hasNext());
        final BinaryTreeNode<String> wrappedRight = wrappedIterator.next();
        assertFalse(wrappedIterator.hasNext());
        assertEquals("left", wrappedLeft.getElement());
        assertEquals("right", wrappedRight.getElement());

        wrappedIterator.next();
    }
}
