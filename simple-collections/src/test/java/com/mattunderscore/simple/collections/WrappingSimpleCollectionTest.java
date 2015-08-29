package com.mattunderscore.simple.collections;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Unit tests for {@link WrappingSimpleCollection}.
 *
 * @author Matt Champion on 29/08/2015
 */
public final class WrappingSimpleCollectionTest {
    private SimpleCollection<String> simpleCollection;

    @Before
    public void setUp() {
        final List<String> collection = new ArrayList<>();

        collection.add("a");
        collection.add(null);
        collection.add("b");

        simpleCollection = new WrappingSimpleCollection<>(collection);
    }


    @Test
    public void size() {
        assertEquals(3, simpleCollection.size());
    }

    @Test
    public void isEmpty() {
        assertFalse(simpleCollection.isEmpty());
    }

    @Test
    public void iterator() {
        final Iterator<String> iterator = simpleCollection.iterator();

        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void structuralIterator() {
        final Iterator<String> iterator = simpleCollection.structuralIterator();

        assertEquals("a", iterator.next());
        assertEquals(null, iterator.next());
        assertEquals("b", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void stream() {
        final Stream<String> stream = simpleCollection.stream();

        final List<String> streamed = stream.collect(Collectors.toList());

        assertEquals(3, streamed.size());
        assertEquals("a", streamed.get(0));
        assertEquals(null, streamed.get(1));
        assertEquals("b", streamed.get(2));
    }
}
