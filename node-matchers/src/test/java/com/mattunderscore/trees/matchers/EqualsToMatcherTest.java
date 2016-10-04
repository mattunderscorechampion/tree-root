package com.mattunderscore.trees.matchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Test;

import com.mattunderscore.trees.base.ImmutableNode;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.OpenNode;

/**
 * Unit tests for {@link EqualsToMatcher}.
 *
 * @author Matt Champion on 14/09/2015
 */
public final class EqualsToMatcherTest {
    @Test
    public void greaterThan() {
        final OpenNode<Integer, Node<Integer>> node = new ImmutableNode<Integer, Node<Integer>>(5, new Object[0]) {};
        final Predicate<OpenNode<? extends Integer, ?>> matcher = EqualsToMatcher.create(3);
        assertFalse(matcher.test(node));
    }

    @Test
    public void lessThan() {
        final OpenNode<Integer, Node<Integer>> node = new ImmutableNode<Integer, Node<Integer>>(5, new Object[0]) {};
        final Predicate<OpenNode<? extends Integer, ?>> matcher = EqualsToMatcher.create(8);
        assertFalse(matcher.test(node));
    }

    @Test
    public void equalTo() {
        final OpenNode<Integer, Node<Integer>> node = new ImmutableNode<Integer, Node<Integer>>(5, new Object[0]) {};
        final Predicate<OpenNode<? extends Integer, ?>> matcher = EqualsToMatcher.create(5);
        assertTrue(matcher.test(node));
    }

    @Test
    public void testEquals() {
        final Predicate<OpenNode<? extends Integer, ?>> matcher0 = EqualsToMatcher.create(3);
        final Predicate<OpenNode<? extends Integer, ?>> matcher1 = EqualsToMatcher.create(3);

        assertTrue(matcher0.equals(matcher1));
        assertTrue(matcher1.equals(matcher0));
        assertEquals(matcher0.hashCode(), matcher1.hashCode());
    }

    @Test
    public void testEqualsSelf() {
        final Predicate<OpenNode<? extends Integer, ?>> matcher0 = EqualsToMatcher.create(3);

        assertTrue(matcher0.equals(matcher0));
    }

    @Test
    public void testNotEquals0() {
        final Predicate<OpenNode<? extends Integer, ?>> matcher0 = EqualsToMatcher.create(3);
        final Predicate<OpenNode<? extends Integer, ?>> matcher1 = EqualsToMatcher.create(5);

        assertFalse(matcher0.equals(matcher1));
    }

    @Test
    public void testNotEquals1() {
        final Predicate<OpenNode<? extends Integer, ?>> matcher0 = EqualsToMatcher.create(3);

        assertFalse(matcher0.equals(null));
    }

    @Test
    public void testNotEquals2() {
        final Predicate<OpenNode<? extends Integer, ?>> matcher0 = EqualsToMatcher.create(3);

        assertFalse(matcher0.equals(new Object()));
    }
}
