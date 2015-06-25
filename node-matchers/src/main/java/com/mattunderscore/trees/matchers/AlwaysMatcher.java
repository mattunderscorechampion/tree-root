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

import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.selection.NodeMatcher;

import net.jcip.annotations.Immutable;

/**
 * Matches any node.
 * @author Matt Champion on 25/06/14.
 */
@Immutable
public final class AlwaysMatcher<E> implements NodeMatcher<E> {
    private static final NodeMatcher<?> INSTANCE = new AlwaysMatcher<>();

    @Override
    public <N extends OpenNode<? extends E, ? extends N>> boolean matches(N node) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && (o == this || o.getClass().equals(getClass()));
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @SuppressWarnings("unchecked")
    public static <E> NodeMatcher<E> create() {
        return (NodeMatcher<E>)INSTANCE;
    }
}
