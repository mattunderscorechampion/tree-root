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

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.internal.TreeNodeImpl;
import com.mattunderscore.trees.utilities.FixedUncheckedSimpleCollection;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author matt on 09/06/14.
 */
public final class RegexMatcherTest {
    private static final NodeMatcher<String> aMatcher = new RegexMatcher("^A$");
    private static final NodeMatcher<String> aMatcherAlt = new RegexMatcher("A");

    @Test
    public void nodeMatches0() {
        final Node<String> node = new TreeNodeImpl("A", new FixedUncheckedSimpleCollection<>(new Object[0]));
        assertTrue(aMatcher.matches(node));
    }

    @Test
    public void nodeMatches1() {
        final Node<String> node = new TreeNodeImpl("A", new FixedUncheckedSimpleCollection<>(new Object[0]));
        assertTrue(aMatcherAlt.matches(node));
    }

    @Test
    public void nodeMatches2() {
        final Node<String> nodeA = new TreeNodeImpl("A", new FixedUncheckedSimpleCollection<>(new Object[0]));
        final Node<String> nodeB = new TreeNodeImpl("B", new FixedUncheckedSimpleCollection<>(new Object[0]));
    }

    @Test
    public void nodeNoMatch0() {
        final Node<String> node = new TreeNodeImpl("B", new FixedUncheckedSimpleCollection<>(new Object[0]));
        assertFalse(aMatcher.matches(node));
    }

    @Test
    public void nodeNoMatch1() {
        final Node<String> node = new TreeNodeImpl("B", new FixedUncheckedSimpleCollection<>(new Object[0]));
        assertFalse(aMatcherAlt.matches(node));
    }
}
