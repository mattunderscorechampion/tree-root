/* Copyright © 2014 Matthew Champion
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of mattunderscore.com nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL MATTHEW CHAMPION BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.mattunderscore.trees.strings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ServiceLoader;
import java.util.function.Predicate;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.immutable.TreeNodeImpl;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * @author Matt Champion on 09/06/14.
 */
public final class RegexMatcherTest {
    private static final BottomUpTreeBuilder<String, Node<String>> builder = ServiceLoader.load(Trees.class)
            .iterator().next()
            .treeBuilders().bottomUpBuilder();
    private static final Predicate<OpenNode<? extends String, ?>> aMatcher = new RegexMatcher("^A$");
    private static final Predicate<OpenNode<? extends String, ?>> aMatcherAlt = new RegexMatcher("A");

    @Test
    public void nodeMatches0() {
        final Tree<String, Node<String>> tree = builder.create("A").build(TreeNodeImpl.<String>typeKey());
        final Node<String> node = tree.getRoot();
        assertTrue(aMatcher.test(node));
    }

    @Test
    public void nodeMatches1() {
        final Node<String> node = builder.create("A").build(TreeNodeImpl.<String>typeKey()).getRoot();
        assertTrue(aMatcherAlt.test(node));
    }

    @Test
    public void nodeNoMatch0() {
        final Node<String> node = builder.create("B").build(TreeNodeImpl.<String>typeKey()).getRoot();
        assertFalse(aMatcher.test(node));
    }

    @Test
    public void nodeNoMatch1() {
        final Node<String> node = builder.create("B").build(TreeNodeImpl.<String>typeKey()).getRoot();
        assertFalse(aMatcherAlt.test(node));
    }

    @Test
    public void testEquals() {
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new RegexMatcher("A");
        final Predicate<OpenNode<? extends String, ?>> matcher1 = new RegexMatcher("A");

        assertTrue(matcher0.equals(matcher1));
        assertTrue(matcher1.equals(matcher0));
        assertEquals(matcher0.hashCode(), matcher1.hashCode());
    }

    @Test
    public void testNotEquals0() {
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new RegexMatcher("A");
        final Predicate<OpenNode<? extends String, ?>> matcher1 = new RegexMatcher("B");

        assertFalse(matcher0.equals(matcher1));
    }

    @Test
    public void testNotEquals1() {
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new RegexMatcher("A");

        assertFalse(matcher0.equals(null));
    }

    @Test
    public void testNotEquals2() {
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new RegexMatcher("A");

        assertFalse(matcher0.equals(new Object()));
    }
}
