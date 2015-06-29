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

package com.mattunderscore.trees.impl;

import java.util.function.Predicate;

import com.mattunderscore.trees.matchers.PredicateMatcher;
import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.selectors.NextNodeSelector;
import com.mattunderscore.trees.selectors.RootMatcherSelector;
import com.mattunderscore.trees.selectors.SelectorNodeSelector;
import com.mattunderscore.trees.tree.OpenNode;

/**
 * Factory for node selectors.
 * @author Matt Champion on 25/06/14.
 */
final class NodeSelectorFactoryImpl implements NodeSelectorFactory {

    public NodeSelectorFactoryImpl() {
    }

    @Override
    public <E> NodeSelector<E> newSelector(final NodeMatcher<E> matcher) {
        return new RootMatcherSelector<>(matcher);
    }

    @Override
    public <E> NodeSelector<E> newSelector(Predicate<OpenNode<? extends E, ?>> predicate) {
        return new RootMatcherSelector<>(new PredicateMatcher<>(predicate));
    }

    @Override
    public <E> NodeSelector<E> newSelector(final NodeSelector<E> selector, final NodeMatcher<E> matcher) {
        return new NextNodeSelector<>(selector, matcher);
    }

    @Override
    public <E> NodeSelector<E> newSelector(NodeSelector<E> selector, Predicate<OpenNode<? extends E, ?>> predicate) {
        return new NextNodeSelector<>(selector, new PredicateMatcher<>(predicate));
    }

    @Override
    public <E> NodeSelector<E> newSelector(final NodeSelector<E> baseSelector, final NodeSelector<E> extensionSelector) {
        return new SelectorNodeSelector<>(baseSelector, extensionSelector);
    }

}
