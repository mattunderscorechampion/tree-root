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

import static org.junit.Assert.*;

import com.mattunderscore.trees.*;
import com.mattunderscore.trees.internal.DefaultMatcher;
import com.mattunderscore.trees.internal.NodeSelectorFactory;
import com.mattunderscore.trees.internal.TreeFactoryImpl;
import org.junit.Test;

import java.util.Iterator;

/**
 * @author matt on 25/06/14.
 */
public class NodeSelectorTest {
    @Test
    public void test() {
        final TreeFactory factory = new TreeFactoryImpl();
        final Tree<String> tree = factory.create("A",
            factory.create("B"),
            factory.create("C"));

        final NodeSelectorFactory selectorFactory = new NodeSelectorFactory();
        final NodeMatcher<String> matcher0 = new DefaultMatcher("A");
        final NodeSelector<String> selector0 = selectorFactory.newSelector(matcher0);
        final Iterator<Node<String>> nodeIterator0 = selector0.select(tree);
        assertTrue(nodeIterator0.hasNext());
        assertEquals("A", nodeIterator0.next().getElement());

        final NodeMatcher<String> matcher1 = new DefaultMatcher("B");
        final NodeSelector<String> selector1 = selectorFactory.newSelector(matcher1, selector0);
        final Iterator<Node<String>> nodeIterator1 = selector1.select(tree);
        assertTrue(nodeIterator1.hasNext());
        assertEquals("B", nodeIterator1.next().getElement());

        final NodeMatcher<String> matcher2 = new DefaultMatcher("C");
        final NodeSelector<String> selector2 = selectorFactory.newSelector(matcher2, selector1);
        final Iterator<Node<String>> nodeIterator2 = selector2.select(tree);
        assertFalse(nodeIterator2.hasNext());

        final NodeSelector<String> selector3 = selectorFactory.newSelector(matcher2, selector0);
        final Iterator<Node<String>> nodeIterator3 = selector3.select(tree);
        assertTrue(nodeIterator3.hasNext());
        assertEquals("C", nodeIterator3.next().getElement());
    }
}
