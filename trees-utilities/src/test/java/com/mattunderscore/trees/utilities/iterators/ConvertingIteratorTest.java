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

package com.mattunderscore.trees.utilities.iterators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public final class ConvertingIteratorTest {
    @Test(expected = NoSuchElementException.class)
    public void testSingle() {
        final Iterator<String> delegate = new SingletonIterator<>("5");
        final Iterator<Integer> converter = new IntegerParsingConvertingIterator(delegate);

        assertTrue(converter.hasNext());
        assertEquals(Integer.valueOf(5), converter.next());
        assertFalse(converter.hasNext());
        converter.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        final Iterator<String> delegate = new SingletonIterator<>("5");
        final Iterator<Integer> converter = new IntegerParsingConvertingIterator(delegate);

        converter.remove();
    }

    @Test
    public void mockedRemove() {
        final Iterator<String> delegate = mock(Iterator.class);
        final Iterator<Integer> converter = new IntegerParsingConvertingIterator(delegate);

        converter.remove();
        verify(delegate).remove();
    }

    public static final class IntegerParsingConvertingIterator extends ConvertingIterator<Integer, String> {
        private IntegerParsingConvertingIterator(Iterator<String> delegate) {
            super(delegate);
        }

        @Override
        protected Integer convert(String s) {
            return Integer.parseInt(s);
        }
    }
}
