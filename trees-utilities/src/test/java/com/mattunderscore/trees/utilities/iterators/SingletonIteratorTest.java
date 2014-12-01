package com.mattunderscore.trees.utilities.iterators;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class SingletonIteratorTest {

    @Test(expected = NoSuchElementException.class)
    public void stepThrough() {
        final Iterator<String> iterator = new SingletonIterator<>("a");

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        final Iterator<String> iterator = new SingletonIterator<>("a");

        iterator.remove();
    }
}