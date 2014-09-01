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

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.selection.NodeMatcher;
import net.jcip.annotations.Immutable;

/**
 * Matches the negation of the matcher passed to it.
 * @author matt on 25/06/14.
 */
@Immutable
public final class NegatingMatcher<E> implements NodeMatcher<E> {
    private final NodeMatcher<E> matcher;

    public NegatingMatcher(NodeMatcher<E> matcher) {
        if (matcher == null) {
            throw new NullPointerException();
        }
        this.matcher = matcher;
    }

    @Override
    public <T extends Node<E>> boolean matches(T node) {
        return !matcher.matches(node);
    }

    @Override
    public boolean equals(Object o) {
        if (o ==  null) {
            return false;
        }
        else if (o == this) {
            return true;
        }
        else if (o.getClass().equals(getClass())) {
            final NegatingMatcher match = (NegatingMatcher)o;
            return match.matcher.equals(matcher);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return matcher.hashCode();
    }
}
