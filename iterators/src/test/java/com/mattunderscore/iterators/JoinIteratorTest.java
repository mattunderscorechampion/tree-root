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

package com.mattunderscore.iterators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Unit tests for {@link JoinIterator}.
 *
 * @author Matt Champion on 06/08/2015
 */
public final class JoinIteratorTest {

    @Test(expected = NoSuchElementException.class)
    public void noIterators() {
        final Iterator<String> iterator = JoinIterator.<String>builder().build();

        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void oneEmptyIterator() {
        final Iterator<String> iterator = JoinIterator.<String>builder()
            .join(Collections.emptyIterator())
            .build();

        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void twoEmptyIterators() {
        final Iterator<String> iterator = JoinIterator.<String>builder()
            .join(Collections.emptyIterator())
            .join(Collections.emptyIterator())
            .build();

        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test
    public void oneIteratorContainingOneElement() {
        final Iterator<String> iterator = JoinIterator.<String>builder()
            .join(Collections.singletonList("a").iterator())
            .build();

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void twoIteratorsContainingOneElement() {
        final Iterator<String> iterator = JoinIterator.<String>builder()
            .join(Collections.singletonList("a").iterator())
            .join(Collections.singletonList("b").iterator())
            .build();

        final List<String> list = new ArrayList<>();
        assertTrue(iterator.hasNext());
        iterator.forEachRemaining(list::add);
        assertFalse(iterator.hasNext());

        assertEquals(2, list.size());
        assertTrue(list.contains("a"));
        assertTrue(list.contains("b"));
    }

    @Test
    public void oneIteratorContainingTwoElements() {
        final Iterator<String> iterator = JoinIterator.<String>builder()
            .join(new ArrayIterator<>(new String[]{"a", "b"}))
            .join(Collections.emptyIterator())
            .build();

        final List<String> list = new ArrayList<>();
        assertTrue(iterator.hasNext());
        iterator.forEachRemaining(list::add);
        assertFalse(iterator.hasNext());

        assertEquals(2, list.size());
        assertTrue(list.contains("a"));
        assertTrue(list.contains("b"));
    }

    @Test
    public void twoIteratorsContainingTwoElements() {
        final Iterator<String> iterator = JoinIterator.<String>builder()
            .join(new ArrayIterator<>(new String[]{"a", "b"}))
            .join(new ArrayIterator<>(new String[]{"c", "d"}))
            .build();

        final List<String> list = new ArrayList<>();
        assertTrue(iterator.hasNext());
        iterator.forEachRemaining(list::add);
        assertFalse(iterator.hasNext());

        assertEquals(4, list.size());
        assertTrue(list.contains("a"));
        assertTrue(list.contains("b"));
        assertTrue(list.contains("c"));
        assertTrue(list.contains("d"));
    }
}
