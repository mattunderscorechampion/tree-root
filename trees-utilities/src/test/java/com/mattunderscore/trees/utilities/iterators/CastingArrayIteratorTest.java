package com.mattunderscore.trees.utilities.iterators;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class CastingArrayIteratorTest {

    @Test(expected = NoSuchElementException.class)
    public void stringCast() {
        final Object[] objects = new Object[] {"a", "b"};
        final Iterator<String> iterator = new CastingArrayIterator<>(objects);

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        final Iterator<String> iterator = new SingletonIterator<>("a");

        iterator.remove();
    }
}