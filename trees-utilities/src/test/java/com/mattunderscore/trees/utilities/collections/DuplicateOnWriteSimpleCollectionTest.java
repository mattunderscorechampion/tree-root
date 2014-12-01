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

package com.mattunderscore.trees.utilities.collections;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link com.mattunderscore.trees.utilities.collections.DuplicateOnWriteSimpleCollection}.
 * @author Matt Champion on 19/09/14.
 */
public final class DuplicateOnWriteSimpleCollectionTest {
    @Test
    public void add() {
        final DuplicateOnWriteSimpleCollection<String> collection = DuplicateOnWriteSimpleCollection.create();
        assertTrue(collection.isEmpty());

        final DuplicateOnWriteSimpleCollection<String> modifiedCollection = collection.add("a");

        assertEquals(0, collection.size());
        assertFalse(collection.iterator().hasNext());
        assertFalse(collection.structuralIterator().hasNext());

        assertFalse(modifiedCollection.isEmpty());
        assertEquals(1, modifiedCollection.size());
        assertEquals("a", modifiedCollection.iterator().next());
        assertEquals("a", modifiedCollection.structuralIterator().next());
    }

    @Test
    public void remove() {
        final DuplicateOnWriteSimpleCollection<String> collection = DuplicateOnWriteSimpleCollection.create();
        final DuplicateOnWriteSimpleCollection<String> modifiedCollection0 = collection.add("a");
        final DuplicateOnWriteSimpleCollection<String> modifiedCollection1 = modifiedCollection0.remove("a");
        assertEquals(1, modifiedCollection0.size());
        assertEquals("a", modifiedCollection0.iterator().next());
        assertEquals("a", modifiedCollection0.structuralIterator().next());
        assertEquals(0, modifiedCollection1.size());
        assertFalse(modifiedCollection1.iterator().hasNext());
        assertFalse(modifiedCollection1.structuralIterator().hasNext());
    }

    @Test
    public void removeNotPresent() {
        final DuplicateOnWriteSimpleCollection<String> collection = DuplicateOnWriteSimpleCollection.create();
        final DuplicateOnWriteSimpleCollection<String> modifiedCollection0 = collection.add("a");
        final DuplicateOnWriteSimpleCollection<String> modifiedCollection1 = modifiedCollection0.remove("b");
        assertEquals(1, modifiedCollection1.size());
        assertEquals("a", modifiedCollection1.iterator().next());
        assertEquals("a", modifiedCollection1.structuralIterator().next());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeIterator() {
        final DuplicateOnWriteSimpleCollection<String> collection = DuplicateOnWriteSimpleCollection.create();
        final DuplicateOnWriteSimpleCollection<String> modifiedCollection = collection.add("a");
        final Iterator<String> iterator = modifiedCollection.iterator();
        iterator.next();
        iterator.remove();
    }

    @Test
    public void replace() {
        final DuplicateOnWriteSimpleCollection<String> collection = DuplicateOnWriteSimpleCollection.create(new String[] {"Who", "is", "a", "good", "boy?"});
        final DuplicateOnWriteSimpleCollection<String> modifiedCollection = collection.replace("bad", "good");
        for (final String element : modifiedCollection) {
            assertNotEquals("good", element);
        }
    }
}
