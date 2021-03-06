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

import static com.mattunderscore.trees.walkers.MatcherUtilities.linkedTreeElementMatcher;
import static com.mattunderscore.trees.walkers.MatcherUtilities.linkedTreeTypeMatcher;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mattunderscore.trees.linked.tree.Constructor;
import com.mattunderscore.trees.linked.tree.EmptyConstructor;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.traversal.Walker;

/**
 * Unit tests for {@link BreadthFirstTraversalDriver}.
 */
public final class BreadthFirstTraversalDriverTest {
    private static TraversalDriver walker;
    private static LinkedTree<String> tree;
    private static LinkedTree<String> emptyTree;

    @Mock
    private Walker<MutableSettableStructuredNode<String>> nodeWalker;
    @Mock
    private Walker<String> elementWalker;
    private InOrder elementOrder;
    private InOrder nodeOrder;

    @BeforeClass
    public static void setUpClass() {
        final Constructor<String> constructor = new Constructor<>();
        tree = constructor.build(
            "f",
            constructor.build(
                "b",
                constructor.build(
                    "a"),
                constructor.build(
                    "d",
                    constructor.build(
                        "c"),
                    constructor.build(
                        "e")
                )
            ),
            constructor.build(
                "i",
                constructor.build(
                    "h",
                    constructor.build(
                        "g"))));

        emptyTree = new EmptyConstructor<String>().build();

        walker = new BreadthFirstTraversalDriver();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        nodeOrder = inOrder(nodeWalker);
        elementOrder = inOrder(elementWalker);
    }

    @Test
    public void empty() {
        walker.traverseTree(emptyTree, nodeWalker);
        nodeOrder.verify(nodeWalker).onEmpty();
        nodeOrder.verify(nodeWalker).onCompleted();
        nodeOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(nodeWalker);
    }

    @Test
    public void emptyElements() {
        walker.traverseTree(emptyTree, new NodeToElementWalker<>(elementWalker));
        elementOrder.verify(elementWalker).onEmpty();
        elementOrder.verify(elementWalker).onCompleted();
        elementOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void elements() {
        when(elementWalker.onNext(isA(String.class))).thenReturn(true);
        walker.traverseTree(tree, new NodeToElementWalker<>(elementWalker));
        elementOrder.verify(elementWalker).onNext("f");
        elementOrder.verify(elementWalker).onNext("b");
        elementOrder.verify(elementWalker).onNext("i");
        elementOrder.verify(elementWalker).onNext("a");
        elementOrder.verify(elementWalker).onNext("d");
        elementOrder.verify(elementWalker).onNext("h");
        elementOrder.verify(elementWalker).onNext("c");
        elementOrder.verify(elementWalker).onNext("e");
        elementOrder.verify(elementWalker).onNext("g");
        elementOrder.verify(elementWalker).onCompleted();
        elementOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void firstElement() {
        when(elementWalker.onNext(isA(String.class))).thenReturn(false);
        walker.traverseTree(tree, new NodeToElementWalker<>(elementWalker));
        verify(elementWalker).onNext("f");
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void firstTwoElements() {
        when(elementWalker.onNext(isA(String.class))).thenReturn(true, false);
        walker.traverseTree(tree, new NodeToElementWalker<>(elementWalker));
        elementOrder.verify(elementWalker).onNext("f");
        elementOrder.verify(elementWalker).onNext("b");
        elementOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void nodes() {
        when(nodeWalker.onNext(linkedTreeTypeMatcher())).thenReturn(true);
        walker.traverseTree(tree, nodeWalker);
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("f"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("b"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("i"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("a"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("d"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("h"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("c"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("e"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("g"));
        nodeOrder.verify(nodeWalker).onCompleted();
        nodeOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(nodeWalker);
    }
}
