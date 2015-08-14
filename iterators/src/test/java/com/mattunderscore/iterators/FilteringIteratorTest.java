package com.mattunderscore.iterators;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Unit tests for {@link FilteringIterator}.
 *
 * @author Matt Champion on 14/08/2015
 */
public final class FilteringIteratorTest {

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void iteratorOnlyReturnsAcceptedElements() {
        final List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("FILTERED");
        list.add("c");

        final Iterator<String> iterator = new TestIterator(list.iterator());
        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("c", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void filtersAll() {
        final List<String> list = new ArrayList<>();
        list.add("FILTERED");
        list.add("FILTERED");
        list.add("FILTERED");
        list.add("FILTERED");

        final Iterator<String> iterator = new TestIterator(list.iterator());
        assertFalse(iterator.hasNext());
        iterator.next();
    }

    public static final class TestIterator extends FilteringIterator<String> {
        public TestIterator(Iterator<String> delegate) {
            super(delegate);
        }

        @Override
        protected boolean accept(String element) {
            return !"FILTERED".equals(element);
        }
    }
}
