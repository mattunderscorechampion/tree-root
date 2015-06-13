package com.mattunderscore.trees.utilities.iterators;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Unit tests for {@link ArrayIterator}.
 */
public final class ArrayIteratorTest {
    @Test(expected = NoSuchElementException.class)
    public void stringCast() {
        final String[] objects = new String[] {"a", "b"};
        final Iterator<String> iterator = ArrayIterator.create(objects);

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        final Iterator<String> iterator = new ArrayIterator<>(new String[] {"a"});

        iterator.remove();
    }
}