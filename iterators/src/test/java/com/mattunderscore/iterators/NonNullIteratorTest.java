package com.mattunderscore.iterators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Unit tests for {@link NonNullIterator}.
 *
 * @author Matt Champion on 04/10/2016
 */
public final class NonNullIteratorTest {

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void noNulls() {
        final String[] objects = new String[] {"a", null, "b"};
        final Iterator<String> iterator = new NonNullIterator<>(ArrayIterator.create(objects));

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        final Iterator<String> iterator = new NonNullIterator<>(ArrayIterator.create(new String[] {"a"}));

        iterator.remove();
    }
}
