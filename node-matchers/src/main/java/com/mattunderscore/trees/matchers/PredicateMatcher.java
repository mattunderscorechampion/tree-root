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

package com.mattunderscore.trees.matchers;

import java.util.function.Predicate;

import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.tree.OpenNode;

/**
 * NodeMatcher that wraps a predicate.
 * @author Matt Champion on 29/06/2015
 */
public final class PredicateMatcher<E> implements NodeMatcher<E> {
    private final Predicate<OpenNode<? extends E, ?>> predicate;

    public PredicateMatcher(Predicate<OpenNode<? extends E, ?>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean matches(OpenNode<? extends E, ?> node) {
        return predicate.test(node);
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
            final PredicateMatcher matcher = (PredicateMatcher)o;
            return matcher.predicate.equals(predicate);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return predicate.hashCode();
    }
}
