package com.mattunderscore.trees.matchers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Test;

import com.mattunderscore.trees.base.ImmutableNode;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.OpenNode;

/**
 * Unit tests for {@link GreaterThanMatcher}.
 *
 * @author Matt Champion on 14/09/2015
 */
public final class GreaterThanMatcherTest {
    @Test
    public void greaterThan() {
        final OpenNode<Integer, Node<Integer>> node = new ImmutableNode<Integer, Node<Integer>>(5, new Object[0]) {};
        final Predicate<OpenNode<? extends Integer, ?>> matcher = GreaterThanMatcher.create(3);
        assertTrue(matcher.test(node));
    }

    @Test
    public void lessThan() {
        final OpenNode<Integer, Node<Integer>> node = new ImmutableNode<Integer, Node<Integer>>(5, new Object[0]) {};
        final Predicate<OpenNode<? extends Integer, ?>> matcher = GreaterThanMatcher.create(8);
        assertFalse(matcher.test(node));
    }

    @Test
    public void equalTo() {
        final OpenNode<Integer, Node<Integer>> node = new ImmutableNode<Integer, Node<Integer>>(5, new Object[0]) {};
        final Predicate<OpenNode<? extends Integer, ?>> matcher = GreaterThanMatcher.create(5);
        assertFalse(matcher.test(node));
    }
}
