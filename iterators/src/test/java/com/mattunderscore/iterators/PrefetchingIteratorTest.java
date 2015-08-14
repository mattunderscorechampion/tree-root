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

package com.mattunderscore.iterators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Unit tests for PrefetchingIterator.
 * @author Matt Champion on 25/12/14
 */
public final class PrefetchingIteratorTest {

    @Test
    public void iterate() {
        final Iterator<String> iterator = new TestIterator(Arrays.asList(new String[]{"a", "b"}).iterator());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeUnsupported() {
        final Iterator<String> iterator = new TestIterator(Arrays.asList(new String[]{"a", "b"}).iterator());
        iterator.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void removeNoElement() {
        final List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        final Iterator<String> iterator = new RemovingTestIterator(list.iterator());
        iterator.remove();
    }

    @Test
    public void remove() {
        final List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        final Iterator<String> iterator = new RemovingTestIterator(list.iterator());
        assertEquals("a", iterator.next());
        iterator.remove();
        assertEquals(1, list.size());
        assertEquals("b", iterator.next());
    }

    private static class TestIterator extends PrefetchingIterator<String> {
        protected final Iterator<String> delegate;

        private TestIterator(Iterator<String> iterator) {
            this.delegate = iterator;
        }

        @Override
        protected final String calculateNext() throws NoSuchElementException {
            return delegate.next();
        }
    }

    private static class RemovingTestIterator extends TestIterator {

        private RemovingTestIterator(Iterator<String> iterator) {
            super(iterator);
        }

        @Override
        protected boolean isRemoveSupported() {
            return true;
        }

        @Override
        protected void remove(String current) {
            delegate.remove();
        }
    }
}
