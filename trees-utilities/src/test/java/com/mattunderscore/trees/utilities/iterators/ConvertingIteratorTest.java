package com.mattunderscore.trees.utilities.iterators;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

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

    public static final class IntegerParsingConvertingIterator extends ConvertingIterator<Integer, String> {
        protected IntegerParsingConvertingIterator(Iterator<String> delegate) {
            super(delegate);
        }

        @Override
        protected Integer convert(String s) {
            return Integer.parseInt(s);
        }
    }
}