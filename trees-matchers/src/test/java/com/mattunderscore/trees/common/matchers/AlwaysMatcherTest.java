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

package com.mattunderscore.trees.common.matchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mattunderscore.trees.base.ImmutableNode;
import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.tree.Node;
import org.junit.Test;

/**
 * Unit tests for AlwaysMatcher.
 * @author Matt Champion on 20/12/14
 */
public final class AlwaysMatcherTest {
    @Test
    public void matches() {
        final Node<String> node = new ImmutableNode<String>("a", new Object[0]) {};
        final NodeMatcher<String> matcher = AlwaysMatcher.create();
        assertTrue(matcher.matches(node));
    }

    @Test
    public void testEquals0() {
        final NodeMatcher<String> matcher0 = new AlwaysMatcher<>();
        final NodeMatcher<String> matcher1 = new AlwaysMatcher<>();

        assertTrue(matcher0.equals(matcher1));
        assertTrue(matcher1.equals(matcher0));
        assertEquals(matcher0.hashCode(), matcher1.hashCode());
    }

    @Test
    public void testEquals1() {
        final NodeMatcher<String> matcher = AlwaysMatcher.create();

        assertTrue(matcher.equals(matcher));
        assertTrue(matcher.equals(matcher));
        assertEquals(matcher.hashCode(), matcher.hashCode());
    }

    @Test
    public void testNotEquals0() {
        final NodeMatcher<String> matcher0 = AlwaysMatcher.create();

        assertFalse(matcher0.equals(null));
    }

    @Test
    public void testNotEquals1() {
        final NodeMatcher<String> matcher0 = AlwaysMatcher.create();

        assertFalse(matcher0.equals(new Object()));
    }
}
