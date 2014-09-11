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

package com.mattunderscore.trees.utilities;

import com.mattunderscore.trees.utilities.FixedUncheckedList;
import org.junit.Test;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Matt Champion on 20/06/14.
 */
public final class FixedUncheckedListTest {

    @Test
    public void simpleTest() {
        final String[] array = {"a", "b", "c"};
        final FixedUncheckedList<String> strings = new FixedUncheckedList<String>(array);
        assertEquals(3, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("b", strings.get(1));
        assertEquals("c", strings.get(2));
    }

    @Test(expected =  ArrayStoreException.class)
    public void toArray0() {
        final String[] array = {"a", "b", "c"};
        final FixedUncheckedList<String> strings = new FixedUncheckedList<String>(array);
        strings.toArray(new Integer[0]);
    }

    @Test(expected =  NullPointerException.class)
    public void toArray1() {
        final String[] array = {"a", "b", "c"};
        final FixedUncheckedList<String> strings = new FixedUncheckedList<String>(array);
        strings.toArray(null);
    }

    @Test
    public void toArray2() {
        final String[] array = {"a", "b", "c"};
        final FixedUncheckedList<String> strings = new FixedUncheckedList<String>(array);
        final String[] newArray = strings.toArray(new String[0]);
        assertEquals("a", newArray[0]);
        assertEquals("b", newArray[1]);
        assertEquals("c", newArray[2]);
    }

    @Test
    public void toArray3() {
        final String[] array = {"a", "b", "c"};
        final FixedUncheckedList<String> strings = new FixedUncheckedList<String>(array);
        final String[] newArray = new String[5];
        assertSame(newArray, strings.toArray(newArray));
        assertEquals("a", newArray[0]);
        assertEquals("b", newArray[1]);
        assertEquals("c", newArray[2]);
        assertNull(newArray[3]);
        assertNull(newArray[4]);
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator0() {
        final String[] array = {"a", "b", "c"};
        final FixedUncheckedList<String> strings = new FixedUncheckedList<String>(array);
        final Iterator<String> iterator = strings.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("c", iterator.next());
        assertFalse(iterator.hasNext());

        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator1() {
        final String[] array = {"a", "b", "c"};
        final FixedUncheckedList<String> strings = new FixedUncheckedList<String>(array);
        final ListIterator<String> iterator = strings.listIterator();
        assertFalse(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.next());
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
        assertEquals("c", iterator.next());
        assertTrue(iterator.hasPrevious());
        assertFalse(iterator.hasNext());

        assertTrue(iterator.hasPrevious());
        assertFalse(iterator.hasNext());
        assertEquals("c", iterator.previous());
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.previous());
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.previous());
        assertFalse(iterator.hasPrevious());
        assertTrue(iterator.hasNext());

        iterator.previous();
    }
}
