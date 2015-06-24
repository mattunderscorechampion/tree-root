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

package com.mattunderscore.trees.matchers;

import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.selection.NodeMatcher;

import net.jcip.annotations.Immutable;

/**
 * Matches a node if one or both of the two matchers passed in match the node.
 * @author Matt Champion on 26/06/14.
 */
@Immutable
public final class DisjunctionMatcher<E> implements NodeMatcher<E> {
    private final NodeMatcher<E> matcher0;
    private final NodeMatcher<E> matcher1;

    public DisjunctionMatcher(NodeMatcher<E> matcher0, NodeMatcher<E> matcher1) {
        this.matcher0 = matcher0;
        this.matcher1 = matcher1;
    }

    @Override
    public <N extends Node<? extends E, ? extends N>> boolean matches(N node) {
        return matcher0.matches(node) || matcher1.matches(node);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        else if (o == this) {
            return true;
        }
        else if (o.getClass().equals(getClass())) {
            @SuppressWarnings("unchecked")
            final DisjunctionMatcher<E> matcher = (DisjunctionMatcher<E>)o;
            // Order of matchers does not matter
            return (matcher.matcher0.equals(matcher0) && matcher.matcher1.equals(matcher1)) ||
                (matcher.matcher0.equals(matcher1) && matcher.matcher1.equals(matcher0));
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return matcher0.hashCode() + matcher1.hashCode();
    }

    /**
     * Collapses two equal matchers into a single, return a an AlwaysMatcher if either matcher is an AlwaysMatcher or
     * return a matcher for the disjunction of the two.
     * @param matcher0 A matcher
     * @param matcher1 A matcher
     * @param <E> The element type of the nodes it matches
     * @return A matcher that evaluates to the disjunction of the two matchers passed in
     */
    public static <E> NodeMatcher<E> create(NodeMatcher<E> matcher0, NodeMatcher<E> matcher1) {
        final Class<? extends NodeMatcher> matcher0Class = matcher0.getClass();
        final Class<? extends NodeMatcher> matcher1Class = matcher1.getClass();
        if (matcher0Class.equals(AlwaysMatcher.class) || matcher1Class.equals(AlwaysMatcher.class)) {
            return new AlwaysMatcher<>();
        }
        else if (matcher0.getClass().equals(NeverMatcher.class)) {
            return matcher1;
        }
        else if (matcher1.getClass().equals(NeverMatcher.class)) {
            return matcher0;
        }
        else if (matcher0.equals(matcher1)) {
            return matcher0;
        }
        else {
            return new DisjunctionMatcher<>(matcher0, matcher1);
        }
    }
}
