/* Copyright © 2015 Matthew Champion
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

package com.mattunderscore.trees.walkers;

import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.ClosedMutableSettableStructuredNode;

/**
 * Utilities for matching arguments of mocked walkers and tree walkers.
 * @author Matt Champion on 09/05/2015
 */
public final class MatcherUtilities {
    private MatcherUtilities() {
    }

    /**
     * Returns a matcher for the element of a linked tree of strings.
     * @param element The element to match
     * @return The matcher
     */
    public static LinkedTree<String> linkedTreeElementMatcher(String element) {
        return Matchers.argThat(new ElementMatcher(element));
    }

    public static final class ElementMatcher extends ArgumentMatcher<LinkedTree<String>> {
        private final String element;

        public ElementMatcher(String element) {
            this.element = element;
        }

        @Override
        public boolean matches(Object o) {
            final ClosedMutableSettableStructuredNode<String> node = (ClosedMutableSettableStructuredNode<String>)o;
            return element.equals(node.getElement());
        }
    }
}
