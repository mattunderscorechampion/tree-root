package com.mattunderscore.trees.base;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.iterators.SingletonIterator;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.tree.Node;

/**
 * Unit tests for {@link MutableChildIterator}.
 *
 * @author Matt Champion on 29/08/2015
 */
public final class MutableChildIteratorTest {
    @Mock
    private MutableNode<String> parent;
    @Mock
    private MutableNode<String> child;

    private Iterator<? extends MutableNode<String>> iterator;

    @Before
    public void setUp() {
        initMocks(this);

        iterator = new SingletonIterator<>(child);
    }


    @Test
    public void hasNext() {
        final MutableChildIterator<String, MutableNode<String>> mutableIterator = new MutableChildIterator<>(parent, iterator);
        assertTrue(mutableIterator.hasNext());
        mutableIterator.next();
        assertFalse(mutableIterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void next() {
        final MutableChildIterator<String, MutableNode<String>> mutableIterator = new MutableChildIterator<>(parent, iterator);
        final MutableNode<String> node = mutableIterator.next();
        assertSame(child, node);
        mutableIterator.next();
    }

    @Test
    public void remove() {
        final MutableChildIterator<String, MutableNode<String>> mutableIterator = new MutableChildIterator<>(parent, iterator);
        mutableIterator.next();

        mutableIterator.remove();

        verify(parent).removeChild(child);
    }
}
