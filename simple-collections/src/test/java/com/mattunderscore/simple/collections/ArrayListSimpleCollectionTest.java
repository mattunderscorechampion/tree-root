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

package com.mattunderscore.simple.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * Unit tests for {@link ArrayListSimpleCollection}.
 * @author Matt Champion on 19/09/14.
 */
public final class ArrayListSimpleCollectionTest {
    @Test
    public void createWithInitialValues() {
        final List<String> set = new ArrayList<>();
        set.add("a");
        set.add("b");
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>(set);
        assertFalse(collection.isEmpty());
        assertEquals(2, collection.size());
        final Iterator<String> iterator = collection.iterator();
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertFalse(iterator.hasNext());
        final Iterator<String> structuralIterator = collection.structuralIterator();
        assertEquals("a", structuralIterator.next());
        assertEquals("b", structuralIterator.next());
        assertFalse(structuralIterator.hasNext());
    }

    @Test
    public void createWithInitialCapacity() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>(64);
        collection.add("a");
        assertEquals("a", collection.get(0));
    }

    @Test
    public void add() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        assertTrue(collection.isEmpty());
        collection.add("a");
        assertFalse(collection.isEmpty());
        assertEquals(1, collection.size());
        assertEquals("a", collection.iterator().next());
        assertEquals("a", collection.structuralIterator().next());
    }

    @Test(expected = NullPointerException.class)
    public void addNull() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add(null);
    }

    @Test
    public void remove() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        assertTrue(collection.remove("a"));
        assertEquals(0, collection.size());
        assertFalse(collection.iterator().hasNext());
        assertFalse(collection.structuralIterator().hasNext());
    }

    @Test
    public void removeNotPresent() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        assertFalse(collection.remove("b"));
        assertEquals(1, collection.size());
        assertEquals("a", collection.iterator().next());
        assertEquals("a", collection.structuralIterator().next());
    }

    @Test
    public void removeIterator() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        final Iterator<String> iterator = collection.iterator();
        iterator.next();
        iterator.remove();

        assertEquals(0, collection.size());
    }

    @Test
    public void removeStructuralIterator() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        final Iterator<String> iterator = collection.structuralIterator();
        iterator.next();
        iterator.remove();

        assertEquals(0, collection.size());
    }

    @Test
    public void set0() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.set(0, "a");

        assertEquals(1, collection.size());
        assertEquals("a", collection.iterator().next());
        assertEquals("a", collection.structuralIterator().next());
    }

    @Test
    public void set1() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.set(1, "a");

        assertEquals(2, collection.size());
        assertEquals("a", collection.iterator().next());
        final Iterator<String> structuralIterator = collection.structuralIterator();
        assertEquals(null, structuralIterator.next());
        assertEquals("a", structuralIterator.next());
    }

    @Test
    public void set2() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.set(2, "a");

        assertEquals(3, collection.size());
        assertEquals("a", collection.iterator().next());
        final Iterator<String> structuralIterator = collection.structuralIterator();
        assertEquals(null, structuralIterator.next());
        assertEquals(null, structuralIterator.next());
        assertEquals("a", structuralIterator.next());
    }

    @Test
    public void set3() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("x");
        collection.set(0, "a");

        assertEquals(1, collection.size());
        assertEquals("a", collection.iterator().next());
        assertEquals("a", collection.structuralIterator().next());
    }

    @Test
    public void set4() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("x");
        collection.set(1, "a");

        assertEquals(2, collection.size());
        final Iterator<String> iterator = collection.iterator();
        final Iterator<String> structuralIterator = collection.structuralIterator();
        assertEquals("x", iterator.next());
        assertEquals("a", iterator.next());
        assertEquals("x", structuralIterator.next());
        assertEquals("a", structuralIterator.next());
    }

    @Test
    public void set5() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("x");
        collection.set(2, "a");

        assertEquals(3, collection.size());
        final Iterator<String> iterator = collection.iterator();
        final Iterator<String> structuralIterator = collection.structuralIterator();
        assertEquals("x", iterator.next());
        assertEquals("a", iterator.next());
        assertEquals("x", structuralIterator.next());
        assertEquals(null, structuralIterator.next());
        assertEquals("a", structuralIterator.next());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get0() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.get(1);
    }

    @Test
    public void get1() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        collection.add("b");
        assertEquals("a", collection.get(0));
        assertEquals("b", collection.get(1));
    }
}
