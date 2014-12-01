package com.mattunderscore.trees.utilities.iterators;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;

public final class EmptyIteratorTest {

    @Test(expected = NoSuchElementException.class)
    public void stepThrough() {
        final Iterator<String> iterator = EmptyIterator.get();

        assertFalse(iterator.hasNext());
        iterator.next();
    }



    @Test(expected = IllegalStateException.class)
    public void remove() {
        final Iterator<String> iterator = EmptyIterator.get();

        iterator.remove();
    }
}